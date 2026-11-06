package com.swsoftware.marketplace.registration.domain;

import lombok.Getter;

@Getter 
public class Customer {

    private final CustomerId id;
    private final String name;
    private final String email;

    public Customer(CustomerId id, String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email do cliente é obrigatório.");
        }

        this.id = id != null ? id : CustomerId.generate();
        this.name = name;
        this.email = email;
    }

    public Customer(String name, String email) {
        this(CustomerId.generate(), name, email);
    }
}
