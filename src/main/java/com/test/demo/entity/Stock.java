package com.test.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    private int stockId;
    private String stockDesc;
    private int stockQuantity;

    @Version
    private int version;

    public Stock() {
    }

    public Stock(String stockDesc, int stockQuantity) {
        this.stockDesc = stockDesc;
        this.stockQuantity = stockQuantity;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockDesc() {
        return stockDesc;
    }

    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void merge(Stock stock) {
        if (stock.getStockDesc() != null) {
            this.stockDesc = stock.getStockDesc();
        }

        if (stock.getStockQuantity() != 0) {
            this.stockQuantity = stock.getStockQuantity();
        }
    }
}
