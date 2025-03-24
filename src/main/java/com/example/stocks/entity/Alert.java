package com.example.stocks.entity;

import com.example.stocks.dto.AlertDTO;
import com.example.stocks.dto.AlertType;
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

    @Column(name="percent")
    private Integer percent;

    @Column(name = "alerttype")
    private String alertType;

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }


    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

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
