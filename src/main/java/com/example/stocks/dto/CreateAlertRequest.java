package com.example.stocks.dto;

import lombok.Data;

@Data
public class CreateAlertRequest {

    private String stockSymbol;
    private AlertType alertType;
    private String from;
    private String to;
    private String percent;

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
