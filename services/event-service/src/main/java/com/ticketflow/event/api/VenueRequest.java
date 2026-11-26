package com.ticketflow.event.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record VenueRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 80) String city,
        @NotBlank @Size(max = 200) String address,
        @NotNull @Positive Integer capacity
) {
}