package com.test.demo.service;

import com.test.demo.entity.Product;
import com.test.demo.repository.ProductRepository;
import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product find(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    @Retryable(value = {StaleStateException.class}, maxAttempts = 5)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product patchProduct(int id, Product product, int delay) {
        Product old = repository.findById(id).orElse(null);

        if (old == null) {
            return null;
        }

        old.merge(product);

        if (delay > 0) {
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Product result = repository.save(old);

        LOG.info("Product: service inserted id " + result.getProductId());

        return result;
    }
}
