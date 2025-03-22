package com.example.stocks.rules;

import com.example.stocks.dto.*;
import com.example.stocks.entity.Alert;

import java.math.BigDecimal;
import java.util.List;

public class PercentRule implements Rule{
    @Override
    public InfoDTO applyRule(InfoDTO infoDTO) {
        BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
        BigDecimal previousPrice = infoDTO.getStock().getPreviousPrice();
        List<AlertDTO> alertDTOList = infoDTO.getAlertDTOList();
        List<Alert> alertList = infoDTO.getAlerts();
        if(infoDTO.getStock().isOwn()){
            for (Alert alert: alertList) {
                if(alert.getAlertType().equals(AlertType.PERCENT)){
                    int percent = alert.getPercent();
                    if(abovePercent(previousPrice,currentPrice,percent)){
                        AlertDTO alertDTO = new AlertDTO("Stock has risen by "+percent+"%",null,Action.HOLD,Type.NORMAL);
                        alertDTOList.add(alertDTO);
                    }
                }
            }

        }else{
            for (Alert alert: alertList) {
                if(alert.getAlertType().equals(AlertType.PERCENT)){
                    int percent = alert.getPercent();
                    if(belowPercent(previousPrice,currentPrice,percent)){
                        AlertDTO alertDTO = new AlertDTO("Stock has declined by "+percent+"%",null,Action.HOLD,Type.NORMAL);
                        alertDTOList.add(alertDTO);
                    }
                }
            }
        }

        return infoDTO;
    }

    private boolean belowPercent(BigDecimal previous, BigDecimal current, int percent) {
        if(current.compareTo(previous)<0){
            BigDecimal result = current.subtract(previous);
            result = result.divide(previous);
            result = result.multiply(new BigDecimal(100));
            if(result.compareTo(new BigDecimal(percent))>0){
                return  true;
            }else {
                return false;
            }
        }
        return false;
    }

    private boolean abovePercent(BigDecimal previous, BigDecimal current, int percent){
        if(current.compareTo(previous)>0){
            BigDecimal result = current.subtract(previous);
            result = result.divide(previous);
            result = result.multiply(new BigDecimal(100));
            if(result.compareTo(new BigDecimal(percent))>0){
                return  true;
            }else {
                return false;
            }
        }
        return false;
    }
}
