package com.ticketflow.event.mapper;

import com.ticketflow.event.api.CreateEventRequest;
import com.ticketflow.event.api.EventResponse;
import com.ticketflow.event.api.TicketTierRequest;
import com.ticketflow.event.api.VenueRequest;
import com.ticketflow.event.domain.Event;
import com.ticketflow.event.domain.EventStatus;
import com.ticketflow.event.domain.TicketTier;
import com.ticketflow.event.domain.Venue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class EventMapper {

    public Event toEntity(CreateEventRequest request) {
        Objects.requireNonNull(request, "request must not be null");

        Event event = new Event();
        event.setTitle(request.title().trim());
        event.setDescription(request.description() != null ? request.description().trim() : null);
        event.setCategory(request.category().trim());
        event.setStatus(EventStatus.DRAFT);
        event.setStartsAt(request.startsAt());
        event.setVenue(toVenue(request.venue()));
        event.setTicketTiers(toTicketTiers(request.ticketTiers()));
        return event;
    }

    public EventResponse toResponse(Event event) {
        Objects.requireNonNull(event, "event must not be null");

        List<TicketTier> tiersCopy = event.getTicketTiers() != null
                ? List.copyOf(event.getTicketTiers())
                : List.of();

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCategory(),
                event.getStatus(),
                event.getStartsAt(),
                event.getVenue(),
                tiersCopy,
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }

    private Venue toVenue(VenueRequest venueRequest) {
        return new Venue(
                venueRequest.name().trim(),
                venueRequest.city().trim(),
                venueRequest.address().trim(),
                venueRequest.capacity()
        );
    }

    private List<TicketTier> toTicketTiers(List<TicketTierRequest> tierRequests) {
        List<TicketTier> tiers = new ArrayList<>(tierRequests.size());
        for (TicketTierRequest tierRequest : tierRequests) {
            tiers.add(new TicketTier(
                    UUID.randomUUID().toString(),
                    tierRequest.name().trim(),
                    tierRequest.price(),
                    tierRequest.currency().trim().toUpperCase(),
                    tierRequest.totalSeats(),
                    tierRequest.totalSeats()
            ));
        }
        return tiers;
    }
}