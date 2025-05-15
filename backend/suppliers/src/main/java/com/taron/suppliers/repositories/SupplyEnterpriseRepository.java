package com.taron.suppliers.repositories;

import com.taron.suppliers.models.SupplyEnterprise;
import com.taron.suppliers.models.SupplyEnterpriseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyEnterpriseRepository extends JpaRepository<SupplyEnterprise, SupplyEnterpriseKey> {
    List<SupplyEnterprise> findByIdEnterprise(Integer idEnterprise);
}

