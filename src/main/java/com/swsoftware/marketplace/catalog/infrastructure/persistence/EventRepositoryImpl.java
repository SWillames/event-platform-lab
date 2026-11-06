package com.swsoftware.marketplace.catalog.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.swsoftware.marketplace.catalog.domain.Event;
import com.swsoftware.marketplace.catalog.domain.EventId;
import com.swsoftware.marketplace.catalog.domain.EventRepository;

@Component
public class EventRepositoryImpl implements EventRepository{

    private final EventEntityRespository entityRespository;
    private final EventMapper mapper;


    public EventRepositoryImpl(EventEntityRespository entityRespository, EventMapper mapper) {
        this.entityRespository = entityRespository;
        this.mapper = mapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = mapper.toEntity(event);
        EventEntity saEventEntity = entityRespository.save(entity);
        return mapper.toDomain(saEventEntity);
    }

    @Override
    public Optional<Event> findById(EventId id) {
        return entityRespository.findById(id.value()).map(mapper::toDomain);
    }

}
