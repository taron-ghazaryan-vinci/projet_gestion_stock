package com.taron.suppliers;

import com.taron.suppliers.models.Supplier;
import com.taron.suppliers.models.SupplyEnterprise;
import com.taron.suppliers.repositories.OrdersProxy;
import com.taron.suppliers.repositories.SuppliersRepository;
import com.taron.suppliers.repositories.SupplyEnterpriseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersService {

    private final OrdersProxy ordersProxy;
    private final SuppliersRepository repository;

    private final SupplyEnterpriseRepository supplyEnterpriseRepository;
    public SuppliersService(SuppliersRepository repository, OrdersProxy ordersProxy, SupplyEnterpriseRepository supplyEnterpriseRepository) {
        this.repository = repository;
        this.ordersProxy = ordersProxy;
        this.supplyEnterpriseRepository = supplyEnterpriseRepository;
    }

    public List<Supplier> getAll() {
        return repository.findAll();
    }

    public Supplier getById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public Supplier createOne(Supplier supplier) {
        return repository.save(supplier);
    }

    public boolean existsByEmail(String email) {
        return repository.
                existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return repository.existsByPhoneNumber(phoneNumber);
    }

    public void deleteOne(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        if (ordersProxy.existsBySupplierId(id)) {
            throw new RuntimeException("Impossible de supprimer : ce fournisseur est lié à une ou plusieurs commandes.");
        }

        repository.deleteById(id);
    }

    public List<Supplier> getAllSupliersByEnterprise(int idEnterprise){
        List<SupplyEnterprise> supplyEnterprise = this.supplyEnterpriseRepository.findByIdEnterprise(idEnterprise);

        List<Integer> supplierList = supplyEnterprise.stream()
                .map(SupplyEnterprise::getIdSupplier)
                .toList();

        System.out.println("IDs fournisseurs : " + supplierList);

        List<Supplier> results = this.repository.findAllById(supplierList);
        System.out.println("Fournisseurs retournés : " + results.size());

        return results;
    }
}
