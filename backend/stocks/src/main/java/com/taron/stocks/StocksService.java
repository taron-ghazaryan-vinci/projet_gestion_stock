package com.taron.stocks;

import com.taron.stocks.models.Stock;
import com.taron.stocks.repositories.StocksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksService {

    private final StocksRepository repository;

    public StocksService(StocksRepository repository) {
        this.repository = repository;
    }

    public List<Stock> getAll() {
        return repository.findAll();
    }

    public List<Stock> getByEnterprise(int idEnterprise) {
        return repository.findAllByEnterpriseId(idEnterprise);
    }

    public Stock getOne(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Stock introuvable"));
    }

    public Stock createOne(Stock stock) {
        return repository.save(stock);
    }

    public void deleteOne(int id) {
        repository.deleteById(id);
    }

    public Stock updateOne(Stock updated) {
        return repository.save(updated);
    }
}
