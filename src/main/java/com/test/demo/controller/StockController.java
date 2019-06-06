package com.test.demo.controller;

import com.test.demo.entity.Stock;
import com.test.demo.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    private Logger LOG = LoggerFactory.getLogger(StockController.class);

    private StockService service;

    @Autowired
    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping("stock/{id}")
    public Stock getStock(@PathVariable("id") int id) {
        return service.findStock(id);
    }

    @PostMapping("stock/{id}")
    public Stock processStock(@PathVariable("id") int id,
                              @RequestParam("action") String action,
                              @RequestParam("value") int value,
                              @RequestParam(required = false, value = "delay", defaultValue = "0") int delay) {
        LOG.info("Stock: process " + id);
        Stock stock;

        switch(action) {
            case "add":
                stock = service.addStock(id, value, delay);
                break;
            case "decrease":
                stock = service.decreaseStock(id, value, delay);
                break;
            default:
                stock = null;
        }

        return stock;
    }
}