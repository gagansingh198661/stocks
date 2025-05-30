package com.example.stocks.rules;

import com.example.stocks.dto.*;
import com.example.stocks.entity.Alert;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class TargetRule implements Rule{

    @Override
    public InfoDTO applyRule(InfoDTO infoDTO,List<Alert> stockAlerts) {


        if(infoDTO.getStock()!=null){
            BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
            BigDecimal targetPrice = infoDTO.getStock().getTargetPrice();
            List<Alert> alerts= infoDTO.getAlerts();
            if(alerts!=null&&alerts.size()>0) {
                for (Alert alert : alerts) {
                    if (alert.getAlertType().equals(AlertType.TARGET)) {
                        if (currentPrice.longValue() >= alert.getLowerlimit().longValue()) {
                            AlertDTO alertDTO = new AlertDTO("Target Price reached : Sell Stock : " + infoDTO.getStock().getStockSymbol(), currentPrice.toPlainString(), targetPrice.toString(), Action.SELL, Type.IMPORTANT);
                            alert.setAlertDTO(alertDTO);
                        }
                    }
                }
            }

        }
        return infoDTO;
    }
}
