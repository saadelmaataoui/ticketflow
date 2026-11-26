package com.ticketflow.event.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

public record CreateEventRequest(
        @NotBlank @Size(max = 160) String title,
        @Size(max = 2000) String description,
        @NotBlank @Size(max = 80) String category,
        @NotNull @Future Instant startsAt,
        @NotNull @Valid VenueRequest venue,
        @NotNull @NotEmpty @Valid List<TicketTierRequest> ticketTiers
) {
}