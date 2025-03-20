package com.example.stocks.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "alert")
public class Alert {

    @Id
    private Long id;
    @Column(name="stocksymbol")
    private String stocksymbol;

    @Column(name="lowerlimit")
    private BigDecimal lowerlimit;

    @Column(name="upperlimit")
    private BigDecimal upperlimit;

    @Column(name="active")
    private boolean active;

    public String getStocksymbol() {
        return stocksymbol;
    }

    public void setStocksymbol(String stocksymbol) {
        this.stocksymbol = stocksymbol;
    }

    public BigDecimal getLowerlimit() {
        return lowerlimit;
    }

    public void setLowerlimit(BigDecimal lowerlimit) {
        this.lowerlimit = lowerlimit;
    }

    public BigDecimal getUpperlimit() {
        return upperlimit;
    }

    public void setUpperlimit(BigDecimal upperlimit) {
        this.upperlimit = upperlimit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
