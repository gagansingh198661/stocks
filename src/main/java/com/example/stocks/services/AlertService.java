package com.example.stocks.services;

import com.example.stocks.dto.AlertRequest;
import com.example.stocks.dto.AlertType;

import com.example.stocks.entity.Alert;
import com.example.stocks.entity.Stock;
import com.example.stocks.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public List<Alert> getAlerts(){
        return alertRepository.findAllByActive(true);
    }

    public boolean createAlert(AlertRequest request){
        try {
            List<Alert> alerts = alertRepository.findAllByStocksymbolAndLowerlimit(request.getStockSymbol(),new BigDecimal(request.getTargetPrice()));
            if(alerts.size()==0) {
                Alert alert = new Alert();
                alert.setAlertType(AlertType.TARGET.name());
                alert.setStocksymbol(request.getStockSymbol());
                alert.setLowerlimit(new BigDecimal(request.getTargetPrice()));
                alert.setUpperlimit(new BigDecimal(request.getTargetPrice()));
                alert.setActive(true);
                alert = alertRepository.save(alert);
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;

    }

    public List<Alert> getAlertsByStockSymbol(String stockSymbol){
        List<Alert> alerts = alertRepository.findAllByStocksymbol(stockSymbol);
        if (alerts.size()==0){
            System.out.println("No alerts found for : "+stockSymbol);
        }
        return alerts;
    }

    public void deleteAlert(long id){
        try {
            alertRepository.deleteById(id);
        }catch(Exception e){
            System.out.println("Exception : "+e);
        }
    }

    public void updateAlert(Alert alert){
        alertRepository.saveAndFlush(alert);
    }



    //this alert will always be target based
    public boolean createAlertFromStock(Stock stock) {
        try {
            List<Alert> alerts = getAlertsByStockSymbol(stock.getStockSymbol());
            boolean alertFound = false;
            for (Alert alert :
                    alerts) {
                if (alert.getAlertType().equalsIgnoreCase("Target")) {
                    if (stock.getTargetPrice().toString().equals(alert.getLowerlimit().toString())) {
                        alertFound = true;
                    }
                }
            }
            if (!alertFound) {
                Alert alert = new Alert();
                alert.setAlertType(AlertType.TARGET.name());
                alert.setActive(true);
                alert.setLowerlimit(stock.getTargetPrice());
                alert.setUpperlimit(stock.getTargetPrice());
                alert.setStocksymbol(stock.getStockSymbol());
                alertRepository.save(alert);
            }
            return true;
        }catch(Exception e){
            System.out.println("Error while creating alert :" + e);
        }
        return false;
    }

    public Alert getAlertById(long id){
        return alertRepository.findById(id).get();
    }
}
