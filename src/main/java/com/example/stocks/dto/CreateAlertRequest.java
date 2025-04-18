package com.example.stocks.dto;

import lombok.Data;

@Data
public class CreateAlertRequest {

    private String stockSymbol;
    private AlertType alertType;
    private String from;
    private String to;
    private String percent;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public CreateAlertRequest(String stockSymbol, AlertType alertType, String from, String to, String percent) {
        this.stockSymbol = stockSymbol;
        this.alertType = alertType;
        this.from = from;
        this.to = to;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "CreateAlertRequest{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", alertType=" + alertType +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", targetPrice='" + percent + '\'' +
                '}';
    }
}
