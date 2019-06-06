package com.test.demo.controller;

import com.test.demo.entity.Product;
import com.test.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private ProductService service;
    private Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("product/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        LOG.info("Product: GET request id " + id);
        return service.find(id);
    }

    @PostMapping("product")
    public Product addProduct(@RequestBody Product product) {
        LOG.info("Product: POST request received");
        Product result = service.create(product);

        LOG.info("Product: Inserted with id " + result.getProductId());
        return result;
    }

    @PatchMapping("product/{id}")
    public Product patchProduct(@PathVariable("id") int id,
                                 @RequestBody Product product) {
        LOG.info("Product: PATCH request received id " + id);
        return service.patchProduct(id, product, 0);
    }

    @PatchMapping("product/{id}/delay/{delay}")
    public Product patchProductDelay(@PathVariable("id") int id,
                                 @RequestBody Product product,
                                 @PathVariable("delay") int delay) {
        LOG.info("Product: PATCH delay request received id " + id);
        return service.patchProduct(id, product, delay);
    }
}
