package com.swsoftware.marketplace.catalog.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Event {

    private final EventId id;
    private String name;
    private String description;
    private BigDecimal price;
    private int totalCapacity;
    private EventStatus status;

    public Event(EventId id, String name, String description, BigDecimal price, int totalCapacity) {
        this.id = Objects.requireNonNull(id, "O id do evento é obrigatório");
        setName(name);
        setDescription(description);
        setPrice(price);
        setTotalCapacity(totalCapacity);
        this.status = EventStatus.DRAFT;
    }

    public Event(EventId id, String name, String description, BigDecimal price, int totalCapacity, EventStatus status) {
        this.id = Objects.requireNonNull(id, "O id do evento é obrigatório");
        setName(name);
        setDescription(description);
        setPrice(price);
        setTotalCapacity(totalCapacity);
        this.status = Objects.requireNonNull(status, "O status do evento não pode ser nulo.");
    }

    public void publish() {
        if (this.totalCapacity <= 0) {
            throw new IllegalStateException("Um evento não pode ser publicado sem capacidade de lugares.");
        }

        this.status = EventStatus.PUBLISHED;
    }

    public void cancel() {
        this.status = EventStatus.CANCELED;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do evento não pode ser vazio.");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrice(BigDecimal price){
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço do evento deve ser maior ou igual a zero.");
        }
        this.price = price;
    }

    public void setTotalCapacity(int totalCapacity) {
        if (totalCapacity < 0) {
            throw new IllegalArgumentException("A capacidade total não pode ser negativa");
        }
        this.totalCapacity = totalCapacity;
    }

    public EventId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public EventStatus getStatus() {
        return status;
    }

    

}
