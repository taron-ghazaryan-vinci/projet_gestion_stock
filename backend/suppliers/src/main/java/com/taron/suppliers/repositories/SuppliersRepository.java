package com.taron.suppliers.repositories;

import com.taron.suppliers.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {
    // Cette méthode est automatiquement générée par Spring Data JPA
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
