package com.swsoftware.marketplace.registration.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.swsoftware.marketplace.registration.domain.Customer;
import com.swsoftware.marketplace.registration.domain.CustomerId;
import com.swsoftware.marketplace.registration.domain.CustomerRepository;

@Component
public class CustomerRepositoryImpl implements CustomerRepository{

    private final CustomerEntityRepository customerEntityRepository;

    public CustomerRepositoryImpl(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = CustomerMapper.toEntity(customer);
        CustomerEntity savedEntity = customerEntityRepository.save(entity);
        return CustomerMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        if (id == null || id.value() == null) {
            return Optional.empty();
        }

        return customerEntityRepository.findById(id.value()).map(CustomerMapper::toDomain);
    }

}
