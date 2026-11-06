package com.swsoftware.marketplace.catalog.aaplication.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.swsoftware.marketplace.catalog.domain.Event;
import com.swsoftware.marketplace.catalog.domain.EventStatus;

public record EventOutput(
    UUID id,
    String name,
    String description,
    BigDecimal price,
    int totalCapacity,
    EventStatus status
) {
    /**
     * Método fabril estático para converter um Event de Domínio em EventOutput.
     */
    public static EventOutput fromDomain(Event event) {
        return new EventOutput(
            event.getId().value(),
            event.getName(),
            event.getDescription(),
            event.getPrice(),
            event.getTotalCapacity(),
            event.getStatus()
        );
    }
}
