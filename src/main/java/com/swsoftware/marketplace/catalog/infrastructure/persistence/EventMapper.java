package com.swsoftware.marketplace.catalog.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.swsoftware.marketplace.catalog.domain.Event;
import com.swsoftware.marketplace.catalog.domain.EventId;

@Component
public class EventMapper {

    public EventEntity toEntity(Event domain){
        if (domain == null) return null;

        return new EventEntity(
            domain.getId().value(),
            domain.getName(),
            domain.getDescription(),
            domain.getPrice(),
            domain.getTotalCapacity(),
            domain.getStatus()
        );
    }

    public Event toDomain(EventEntity entity) {
        if (entity == null) return null;

        return new Event(
            new EventId(entity.getId()), 
            entity.getName(),
            entity.getDescription(), 
            entity.getPrice(),
            entity.getTotalCapacity(), 
            entity.getStatus()
        );
    }
}
