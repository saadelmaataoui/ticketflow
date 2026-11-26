package com.ticketflow.event.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "events")
@CompoundIndex(
        name = "idx_status_city_starts",
        def = "{'status': 1, 'venue.city': 1, 'startsAt': 1}"
)
public class Event {
    @Id
    private String id;

    private String title;
    private String description;
    private String category;
    private EventStatus status;
    private Instant startsAt;
    private Venue venue;
    private List<TicketTier> ticketTiers = new ArrayList<>();
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @Version
    private Long version;

    // Default constructor
    public Event() {}

    // getter and setter methods
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public List<TicketTier> getTicketTiers() {
        return ticketTiers;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
    //null safety for ticketTiers
    public void setTicketTiers(List<TicketTier> ticketTiers) {
        this.ticketTiers = ticketTiers != null ? new ArrayList<>(ticketTiers) : new ArrayList<>();
    }
}
