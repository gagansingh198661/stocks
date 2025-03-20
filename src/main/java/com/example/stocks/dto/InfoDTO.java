package com.example.stocks.dto;


import com.example.stocks.entity.Alert;
import com.example.stocks.entity.Stock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class InfoDTO {
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<Alert> getAlertList() {
        return alertList;
    }

    public void setAlertList(List<Alert> alertList) {
        this.alertList = alertList;
    }

    private Action action;

    private Stock stock;

    private List<Alert> alertList;

}
