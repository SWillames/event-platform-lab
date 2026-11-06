package com.swsoftware.marketplace.catalog.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swsoftware.marketplace.catalog.aaplication.CreateEventUseCase;
import com.swsoftware.marketplace.catalog.aaplication.dto.CreateEventInput;
import com.swsoftware.marketplace.catalog.aaplication.dto.EventOutput;

@RestController 
@RequestMapping ("/api/events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;

    public EventController(CreateEventUseCase createEventUseCase) {
        this.createEventUseCase = createEventUseCase;
    }

    @PostMapping
    public ResponseEntity<EventOutput> create(@RequestBody CreateEventInput input) {
        EventOutput output = createEventUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
