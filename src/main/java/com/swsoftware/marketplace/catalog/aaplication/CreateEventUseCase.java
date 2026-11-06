package com.swsoftware.marketplace.catalog.aaplication;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.swsoftware.marketplace.catalog.aaplication.dto.CreateEventInput;
import com.swsoftware.marketplace.catalog.aaplication.dto.EventOutput;
import com.swsoftware.marketplace.catalog.domain.Event;
import com.swsoftware.marketplace.catalog.domain.EventId;
import com.swsoftware.marketplace.catalog.domain.EventRepository;

@Service
public class CreateEventUseCase {

    private final EventRepository eventRepository;

    public CreateEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventOutput execute(CreateEventInput input) {
        Objects.requireNonNull(input, "Os dados de entrada não podem ser nulos.");

        EventId newId = EventId.generate();

        Event newEvent = new Event(
            newId,
            input.name(),
            input.description(),
            input.price(),
            input.totalCapacity() != null ? input.totalCapacity() : 0
        );

        Event savedEvent = eventRepository.save(newEvent);

        return EventOutput.fromDomain(savedEvent);
    }
}
