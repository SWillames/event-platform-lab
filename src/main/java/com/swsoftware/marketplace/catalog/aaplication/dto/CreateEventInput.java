package com.swsoftware.marketplace.catalog.aaplication.dto;

import java.math.BigDecimal;

public record CreateEventInput(
    String name,
    String description,
    BigDecimal price,
    Integer totalCapacity
) {}
