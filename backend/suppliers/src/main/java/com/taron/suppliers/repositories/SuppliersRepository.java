package com.taron.suppliers.repositories;

import com.taron.suppliers.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
