package com.ticketflow.event.api;

import com.ticketflow.event.domain.EventStatus;
import com.ticketflow.event.domain.TicketTier;
import com.ticketflow.event.domain.Venue;

import java.time.Instant;
import java.util.List;

public record EventResponse(
        String id,
        String title,
        String description,
        String category,
        EventStatus status,
        Instant startsAt,
        Venue venue,
        List<TicketTier> ticketTiers,
        Instant createdAt,
        Instant updatedAt
) {
}