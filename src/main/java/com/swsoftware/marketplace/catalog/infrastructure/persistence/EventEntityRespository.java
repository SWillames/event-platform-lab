package com.swsoftware.marketplace.catalog.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventEntityRespository extends JpaRepository<EventEntity, UUID>{
}
