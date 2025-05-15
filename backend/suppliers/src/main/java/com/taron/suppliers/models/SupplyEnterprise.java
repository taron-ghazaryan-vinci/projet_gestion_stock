package com.taron.suppliers.models;

import com.taron.suppliers.models.SupplyEnterpriseKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(SupplyEnterpriseKey.class)
@Table(name = "supply_enterprises", schema = "projet_gestion_stock")
public class SupplyEnterprise {
    @Id
    private Integer idSupplier;

    @Id
    private Integer idEnterprise;
}
