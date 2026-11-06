package com.swsoftware.marketplace.registration.infrastructure.persistence;

import com.swsoftware.marketplace.registration.domain.Customer;
import com.swsoftware.marketplace.registration.domain.CustomerId;

public final class CustomerMapper {

    private CustomerMapper(){
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada.");
    }

    public static Customer toDomain(CustomerEntity entity){
        if (entity == null) {
            return null;
        }

        return new Customer(
            new CustomerId(entity.getId()),
            entity.getName(),
            entity.getEmail()
        );
    }

    public static CustomerEntity toEntity(Customer customer) {
        if (customer == null){
            return null;
        }

        return new CustomerEntity(
            customer.getId().value(),
            customer.getName(),
            customer.getEmail(),
            null
        );
    }
}
