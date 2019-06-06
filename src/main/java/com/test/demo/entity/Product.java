package com.test.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private int productId;
    private String productDesc;
    private double productPrice;

    @Version
    private int version;

    public Product() {
    }

    public Product(String productDesc, double productPrice) {
        this.productDesc = productDesc;
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void merge(Product product) {
        if (product.getProductDesc() != null) {
            this.productDesc = product.getProductDesc();
        }

        if (product.getProductPrice() != 0) {
            this.productPrice = product.getProductPrice();
        }
    }
}
