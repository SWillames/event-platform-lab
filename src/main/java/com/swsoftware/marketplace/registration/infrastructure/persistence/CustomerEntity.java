package com.swsoftware.marketplace.registration.infrastructure.persistence;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "customer")
@Getter 
@Setter
@NoArgsConstructor
public class CustomerEntity {

    @Id 
    private UUID id;

    private String name;
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "addres_id", referencedColumnName = "id")
    private AddressEntity address;

    public CustomerEntity(UUID id, String name, String email, AddressEntity address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
