package com.test.demo.service;

import com.test.demo.entity.Stock;
import com.test.demo.repository.StockRepository;
import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository repository;

    private Logger LOG = LoggerFactory.getLogger(StockService.class);

    @Autowired
    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public Stock findStock(int id) {
        return repository.findById(id).orElse(null);
    }

    @Retryable(value = {StaleStateException.class}, maxAttempts = 5)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Stock addStock(int id, int value, int delay) {
        Stock stock = repository.findById(id).orElse(null);

        if (stock == null) {
            return null;
        }

        LOG.info("Stock: " + stock.getStockId() + " Quantity: " + stock.getStockQuantity() +" increasing in " + value);
        stock.setStockQuantity(stock.getStockQuantity() + value);
        LOG.info("Stock: " + stock.getStockId() + " New Quantity " + stock.getStockQuantity());

        if (delay > 0) {
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Stock result = repository.save(stock);
        LOG.info("Stock: " + result.getStockId() + " saved");

        return result;
    }

    @Retryable(value = {StaleStateException.class}, maxAttempts = 5)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Stock decreaseStock(int id, int value, int delay) {
        Stock stock = repository.findById(id).orElse(null);

        if (stock == null) {
            return null;
        }

        LOG.info("Stock: " + stock.getStockId() + " Quantity: " + stock.getStockQuantity() +" decreasing in " + value);
        stock.setStockQuantity(stock.getStockQuantity() - value);
        LOG.info("Stock: " + stock.getStockId() + " New Quantity " + stock.getStockQuantity());

        if (delay > 0) {
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Stock result = repository.save(stock);
        LOG.info("Stock: " + result.getStockId() + " saved");

        return result;
    }
}
