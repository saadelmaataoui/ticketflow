package com.ticketflow.event.application;

import com.ticketflow.event.api.CreateEventRequest;
import com.ticketflow.event.api.EventResponse;
import com.ticketflow.event.domain.Event;
import com.ticketflow.event.domain.EventStatus;
import com.ticketflow.event.domain.TicketTier;
import com.ticketflow.event.mapper.EventMapper;
import com.ticketflow.event.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventResponse createEvent(CreateEventRequest request) {
        Event event = eventMapper.toEntity(request);
        validateEvent(event);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toResponse(savedEvent);
    }

    public EventResponse getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        return eventMapper.toResponse(event);
    }

    public Page<EventResponse> listEvents(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(eventMapper::toResponse);
    }

    public EventResponse publishEvent(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        if (event.getStatus() != null && event.getStatus().equals(EventStatus.PUBLISHED)) {
            throw new IllegalStateException("Event is already published");
        }
        if (event.getStatus() != null && event.getStatus().equals(EventStatus.CANCELLED)) {
            throw new IllegalStateException("Cannot publish a cancelled event");
        }
        if (event.getTicketTiers() == null || event.getTicketTiers().isEmpty()) {
            throw new IllegalStateException("Cannot publish an event without ticket tiers");
        }
        if (event.getStartsAt() == null || event.getStartsAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Cannot publish an event that has already started");
        }
        event.setStatus(EventStatus.PUBLISHED);
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toResponse(updatedEvent);
    }

    public EventResponse cancelEvent(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        if (event.getStatus() != null && event.getStatus().equals(EventStatus.CANCELLED)) {
            throw new IllegalStateException("Event is already cancelled");
        }
        if (event.getStartsAt() == null || event.getStartsAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Cannot cancel an event that has already started");
        }
        event.setStatus(EventStatus.CANCELLED);
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toResponse(updatedEvent);
    }

    public EventResponse updateEvent(String id, CreateEventRequest request) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        if (existingEvent.getStatus() != null && existingEvent.getStatus().equals(EventStatus.CANCELLED)) {
            throw new IllegalStateException("Cannot update a cancelled event");
        }
        if (existingEvent.getStatus() != null && existingEvent.getStatus().equals(EventStatus.PUBLISHED)) {
            throw new IllegalStateException("Cannot update a published event");
        }
        Event updatedEvent = eventMapper.toEntity(request);
        validateEvent(updatedEvent);
        existingEvent.setCategory(updatedEvent.getCategory());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setStartsAt(updatedEvent.getStartsAt());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setTicketTiers(updatedEvent.getTicketTiers());
        Event savedEvent = eventRepository.save(existingEvent);
        return eventMapper.toResponse(savedEvent);
    }

    private void validateEvent(Event event) {
        if (event.getVenue() == null) {
            throw new IllegalArgumentException("Venue information is missing");
        }
        if (event.getTicketTiers() == null || event.getTicketTiers().isEmpty()) {
            throw new IllegalArgumentException("Ticket tiers are missing");
        }
        int totalSeats = 0;
        for (TicketTier ticketTier : event.getTicketTiers()) {
            totalSeats += ticketTier.totalSeats();
        }
        if (totalSeats > event.getVenue().capacity()) {
            throw new IllegalArgumentException("Total seats in ticket tiers exceed venue capacity");
        }
    }
}
