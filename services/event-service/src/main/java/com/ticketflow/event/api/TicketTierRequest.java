package com.ticketflow.event.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TicketTierRequest(
        @NotBlank @Size(max = 100) String name,
        @NotNull @PositiveOrZero BigDecimal price,
        @NotBlank @Pattern(regexp = "^[A-Za-z]{3}$", message = "currency must be a 3-letter code") String currency,
        @NotNull @Positive Integer totalSeats
) {
}