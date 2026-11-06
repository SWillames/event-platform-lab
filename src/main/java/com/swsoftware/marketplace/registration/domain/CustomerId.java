package com.swsoftware.marketplace.registration.domain;
import java.util.UUID;

public record CustomerId(UUID value) {

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }
}
