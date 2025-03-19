package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stock")
public class Stock {


    @Id
    @Column(name="stocksymbol")
    private String stockSymbol;

    @Column(name="name")
    private String name;

    @Column(name="lowestprice")
    private Float lowestPrice;

    @Column(name="highestprice")
    private Float highestPrice;

    @Column(name="currentprice")
    private Float currentPrice;

    @Column(name="targetprice")
    private Float targetPrice;

    @Column(name="sold")
    private boolean sold;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Float lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Float getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Float highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(Float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }
}
