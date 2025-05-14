package com.taron.products;

import com.taron.products.models.Product;
import com.taron.products.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    private final ProductsRepository repository;

    public ProductsService(ProductsRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll(){
        return this.repository.findAll();
    }

    public Product getById(int id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
    }

    public Product createOne(Product product){
        return this.repository.save(product);
    }

    public void deleteOne(int id){
        this.repository.deleteById(id);
    }
}
