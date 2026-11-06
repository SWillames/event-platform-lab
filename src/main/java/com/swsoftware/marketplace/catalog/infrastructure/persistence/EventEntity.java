package com.swsoftware.marketplace.catalog.infrastructure.persistence;

import java.math.BigDecimal;
import java.util.UUID;

import com.swsoftware.marketplace.catalog.domain.EventStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
public class EventEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_capacity", nullable = false)
    private int totalCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    public EventEntity() {}

    public EventEntity(UUID id, String name, String description, BigDecimal price, int totalCapacity,
            EventStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.totalCapacity = totalCapacity;
        this.status = status;
    }
}
