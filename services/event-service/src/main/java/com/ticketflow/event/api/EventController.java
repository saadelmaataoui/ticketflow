package com.ticketflow.event.api;

import com.ticketflow.event.application.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid CreateEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponse>> listEvents(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.listEvents(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable String id, @RequestBody @Valid CreateEventRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(id, request));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<EventResponse> publishEvent(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.publishEvent(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.cancelEvent(id));
    }
}
