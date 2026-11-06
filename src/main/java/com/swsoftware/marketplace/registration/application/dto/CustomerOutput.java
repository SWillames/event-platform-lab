package com.swsoftware.marketplace.registration.application.dto;

import java.util.UUID;

import com.swsoftware.marketplace.registration.domain.Customer;

public record CustomerOutput(
    UUID id,
    String name,
    String email
) {
    public static CustomerOutput fromDomain(Customer customer){
        return new CustomerOutput(
            customer.getId().value(), 
            customer.getName(), 
            customer.getEmail());
    }
}
