package com.taron.stocks;

import com.taron.stocks.models.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    private final com.taron.stocks.StocksService service;

    public StocksController(StocksService service) {
        this.service = service;
    }

    @GetMapping("/getStocksBySupplier/{id}")
    public List<Stock> getStocksBySupplier(@PathVariable int id) {
        return service.getStocksBySupplierId(id);
    }


    @DeleteMapping("/deleteBySupplierAndProduct/{idSupplier}/{idProduct}")
    public void deleteBySupplierAndProduct(@PathVariable int idSupplier, @PathVariable int idProduct) {
        List<Stock> list = getStocksBySupplier(idSupplier);

        Stock stock = list.stream()
                .filter(stock1 -> stock1.getIdProduct() == idProduct)
                .findFirst()
                .orElse(null);

        if (stock != null) {
            this.service.deleteOne(stock.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
        }
    }


    @PatchMapping("/updateStock/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable int id, @RequestBody Stock newStockData) {
        Optional<Stock> optionalStock = this.service.getOne(id);
        if (optionalStock.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Stock stock = optionalStock.get();

        // Mise à jour uniquement des champs modifiables
        stock.setQuantity(newStockData.getQuantity());
        // Si besoin, ajoute ici d'autres champs à modifier comme ownerType, idOwner, etc.

        Stock updated = service.createOne(stock); // createOne fait office de save
        return ResponseEntity.ok(updated);
    }


    @PostMapping("")
    public ResponseEntity<?> createOne(@RequestBody Stock stock) {
        if (stock.getIdProduct() == null ||
                stock.getIdOwner() == null ||
                stock.getOwnerType() == null ||
                stock.getQuantity() == null || stock.getQuantity() < 0) {
            return ResponseEntity.badRequest().body("Tous les champs requis doivent être valides.");
        }

        Stock s = this.service.createOne(stock);
        return ResponseEntity.ok(s);
    }


    @DeleteMapping("/{id}")
    void deleteOne(@PathVariable Integer id){
        this.service.deleteOne(id);
    }

}
