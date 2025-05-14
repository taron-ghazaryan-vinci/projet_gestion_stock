package com.taron.products;

import com.taron.products.models.Product;
import com.taron.products.repositories.ProductsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService service;


    public ProductsController(ProductsService service) {
        this.service = service;
    }


    @GetMapping("/getAll")
    public List<Product> getAll(){
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id){
        return this.service.getById(id);
    }

    @PatchMapping("/updateProduct/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product newProduct){
        Product product = getById(id);
        product.setType(newProduct.getType());
        product.setName(newProduct.getName());
        return this.service.createOne(product);
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Product product){
        if(product.getName().isBlank() || product.getName() == null || product.getType() == null || product.getType().isBlank()){
            return ResponseEntity.badRequest().body("Tous les champs doivent être remplis correctement (longueurs respectées et non vides).");
        }
        Product newProduct = this.service.createOne(product);
        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id){
        this.service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
