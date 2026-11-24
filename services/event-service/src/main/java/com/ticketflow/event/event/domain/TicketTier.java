package com.ticketflow.event.event.domain;

import java.math.BigDecimal;

public record TicketTier(String tierId , String name, BigDecimal price, String currency, int totalSeats, int availableSeats) {
}
