package com.example.stocks.entity;

import jakarta.persistence.*;

@Entity
@Table(name="sentemail")
public class SentEmail {

    @Id
    @Column(name = "emailid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(name = "stockSymbol")
    private String stocksymbol;

    @Column(name = "previousprice")
    private String previousprice;

    @Column(name = "currentprice")
    private String currentprice;


    public String getStocksymbol() {
        return stocksymbol;
    }

    public void setStocksymbol(String stocksymbol) {
        this.stocksymbol = stocksymbol;
    }

    public String getPreviousprice() {
        return previousprice;
    }

    public void setPreviousprice(String previousprice) {
        this.previousprice = previousprice;
    }

    public String getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice;
    }
}
