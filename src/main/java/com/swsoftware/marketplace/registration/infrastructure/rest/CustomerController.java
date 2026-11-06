package com.swsoftware.marketplace.registration.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swsoftware.marketplace.registration.application.CreateCustomerUserCase;
import com.swsoftware.marketplace.registration.application.dto.CreateCustomerInput;
import com.swsoftware.marketplace.registration.application.dto.CustomerOutput;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUserCase createCustomerUserCase;

    public CustomerController(CreateCustomerUserCase createCustomerUserCase) {
        this.createCustomerUserCase = createCustomerUserCase;
    }

    @PostMapping
    public ResponseEntity<CustomerOutput> create(@RequestBody CreateCustomerInput input) {

        CustomerOutput output = createCustomerUserCase.execute(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

}
