package com.taron.suppliers;

import com.taron.suppliers.models.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SuppliersController {

    private final SuppliersService service;

    public SuppliersController(SuppliersService service) {
        this.service = service;
    }

    @GetMapping
    public List<Supplier> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Supplier supplier) {
        if (service.existsByEmail(supplier.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé.");
        }
        if (service.existsByPhoneNumber(supplier.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Numéro de téléphone déjà utilisé.");
        }
        Supplier newSupplier = service.createOne(supplier);
        return ResponseEntity.ok(newSupplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
