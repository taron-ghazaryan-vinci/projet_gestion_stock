package com.taron.stocks;

import com.taron.stocks.models.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    private final com.taron.stocks.StocksService service;

    public StocksController(StocksService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stock> getAll() {
        return service.getAll();
    }

    @GetMapping("/enterprise/{id}")
    public List<Stock> getByEnterprise(@PathVariable int id) {
        return service.getByEnterprise(id);
    }

    @GetMapping("/{id}")
    public Stock getOne(@PathVariable int id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody Stock stock) {
        if (stock.getProductId() <= 0 || stock.getEnterpriseId() <= 0 || stock.getAddress().isBlank()) {
            return ResponseEntity.badRequest().body("Champs manquants ou invalides");
        }
        return ResponseEntity.ok(service.createOne(stock));
    }

    @PatchMapping("/updateStock/{id}")
    public Stock updateStock(@PathVariable int id, @RequestBody Stock newStock) {
        Stock stock = service.getOne(id);
        stock.setAddress(newStock.getAddress());
        stock.setEnterpriseId(newStock.getEnterpriseId());
        stock.setProductId(newStock.getProductId());
        return service.updateOne(stock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
