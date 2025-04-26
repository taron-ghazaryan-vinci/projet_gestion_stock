package com.taron.enterprises;


import com.taron.enterprises.models.Enterprise;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterprises")
public class EnterprisesController {

    private final EnterprisesService service;

    public EnterprisesController(EnterprisesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Enterprise enterprise) {
        if (enterprise.getPhoneNumber() == null || enterprise.getPhoneNumber().isBlank() || enterprise.getPhoneNumber().length() > 20 ||
                enterprise.getEmail() == null || enterprise.getEmail().isBlank() || enterprise.getEmail().length() > 20 ||
                enterprise.getAddress() == null || enterprise.getAddress().isBlank() || enterprise.getAddress().length() > 50 ||
                enterprise.getName() == null || enterprise.getName().isBlank() || enterprise.getName().length() > 50) {

            return ResponseEntity.badRequest().body("Tous les champs doivent être remplis correctement (longueurs respectées et non vides).");
        }

        // Vérification unicité email
        if (service.existsByEmail(enterprise.getEmail())) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé.");
        }

        // Vérification unicité numéro de téléphone
        if (service.existsByPhoneNumber(enterprise.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Ce numéro de téléphone est déjà utilisé.");
        }

        Enterprise created = this.service.createOne(enterprise);
        return ResponseEntity.ok(created);
    }


    @GetMapping("/{id}")
    public Enterprise getById(@PathVariable int id) {
        return this.service.getById(id);
    }

    @GetMapping("/getAll")
    public List<Enterprise> getAll() {
        return this.service.getAll();
    }

    @PatchMapping("/updateEnterprise/{id}")
    public ResponseEntity<?> updateEnterprise(@PathVariable int id, @RequestBody Enterprise newEnterprise) {
        if (service.existsByEmail(newEnterprise.getEmail())) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé.");
        }

        // Vérification unicité numéro de téléphone
        if (service.existsByPhoneNumber(newEnterprise.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Ce numéro de téléphone est déjà utilisé.");
        }
        Enterprise enterprise = getById(id);
        enterprise.setAddress(newEnterprise.getAddress());
        enterprise.setName(newEnterprise.getName());
        enterprise.setEmail(newEnterprise.getEmail());
        enterprise.setPhoneNumber(newEnterprise.getPhoneNumber());


        Enterprise patched = this.service.createOne(enterprise);

        return ResponseEntity.ok(patched);

    }


}
