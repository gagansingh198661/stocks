package com.example.stocks.dto;


import com.example.stocks.entity.Alert;
import com.example.stocks.entity.Stock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class InfoDTO {

    private Action action;

    private Stock stock;

    private List<AlertDTO> alertDTOList;

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    private List<Alert> alerts;

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

    public List<AlertDTO> getAlertDTOList() {
        return alertDTOList;
    }

    public void setAlertDTOList(List<AlertDTO> alertList) {
        this.alertDTOList = alertList;
    }



}
