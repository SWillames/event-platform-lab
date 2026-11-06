package com.swsoftware.marketplace.catalog.domain;

import java.util.Objects;
import java.util.UUID;

public record EventId(UUID value) {

    public EventId {
        Objects.requireNonNull(value, "O identificador do evento não pode ser nulo");
    }

    public static EventId generate() {
        return new EventId(UUID.randomUUID());
    }

    public static EventId fromStrinG(String id){
        return new EventId(UUID.fromString(id));
    }
}
