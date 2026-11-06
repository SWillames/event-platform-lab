package com.swsoftware.marketplace.catalog.domain;

import java.util.Optional;

public interface EventRepository {

    Event save(Event event);
    Optional<Event> findById(EventId id);

}
