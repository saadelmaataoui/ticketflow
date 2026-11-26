package com.ticketflow.event.repository;

import com.ticketflow.event.domain.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
