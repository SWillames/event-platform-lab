package com.swsoftware.marketplace.registration.application;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.swsoftware.marketplace.registration.application.dto.CreateCustomerInput;
import com.swsoftware.marketplace.registration.application.dto.CustomerOutput;
import com.swsoftware.marketplace.registration.domain.Customer;
import com.swsoftware.marketplace.registration.domain.CustomerId;
import com.swsoftware.marketplace.registration.domain.CustomerRepository;

@Service
public class CreateCustomerUserCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerUserCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerOutput execute(CreateCustomerInput input) {
        Objects.requireNonNull(input, "Os dados de entrada não podem ser nulos");

        CustomerId newId = CustomerId.generate();

        Customer newCustomer = new Customer(
            newId,
            input.name(),
            input.email()
        );

        Customer savedCustomer = customerRepository.save(newCustomer);

        return CustomerOutput.fromDomain(savedCustomer);
    }
}
