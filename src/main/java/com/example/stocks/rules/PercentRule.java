package com.example.stocks.rules;

import com.example.stocks.dto.*;
import com.example.stocks.entity.Alert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PercentRule implements Rule{
    @Override
    public InfoDTO applyRule(InfoDTO infoDTO, List<Alert> stockAlerts) {

        if(infoDTO.getStock()!=null&&infoDTO.getStock().isOwn()) {
            if (infoDTO.getAlertDTOList() != null &&
                    stockAlerts != null &&
                    infoDTO.getStock().getCurrentPrice()!=null &&
                    infoDTO.getStock().getPreviousPrice()!=null
            ) {
                List<AlertDTO> alertDTOList = infoDTO.getAlertDTOList();
                BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
                BigDecimal previousPrice = infoDTO.getStock().getPreviousPrice();

                for (Alert alert : stockAlerts) {
                    if (alert.getAlertType()!=null&&alert.getAlertType().equals(AlertType.PERCENT.name())) {
                        int percent = alert.getPercent();
                        if (abovePercent(previousPrice, currentPrice, percent)) {
                            AlertDTO alertDTO = new AlertDTO("Stock : "+infoDTO.getStock().getStockSymbol()+" has risen by " + percent + "%", previousPrice.toPlainString(), currentPrice.toPlainString(),  Action.HOLD, Type.NORMAL);
                            alertDTOList.add(alertDTO);
                        }
                    }
                }
            }
        }else{
            if (infoDTO.getAlertDTOList() != null &&
                    stockAlerts != null &&
                    infoDTO.getStock().getCurrentPrice()!=null &&
                    infoDTO.getStock().getPreviousPrice()!=null) {
                List<AlertDTO> alertDTOList = infoDTO.getAlertDTOList();
                BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
                BigDecimal previousPrice = infoDTO.getStock().getPreviousPrice();

                for (Alert alert: stockAlerts) {
                    if(alert.getAlertType()!=null&&alert.getAlertType().equals(AlertType.PERCENT.name())){
                        int percent = alert.getPercent();
                        if(belowPercent(previousPrice,currentPrice,percent)){
                            AlertDTO alertDTO = new AlertDTO("Stock has declined by "+percent+"%",previousPrice.toString(),currentPrice.toString(),Action.HOLD,Type.NORMAL);
                            alertDTOList.add(alertDTO);
                        }
                    }
                }
            }
        }


        return infoDTO;
    }

    private boolean belowPercent(BigDecimal previous, BigDecimal current, int percent) {
        if(current.compareTo(previous)<0){
            BigDecimal result = current.subtract(previous);
            result = result.multiply(new BigDecimal(100));
            result = result.abs();
            result = result.divide(previous,0, RoundingMode.HALF_UP);
            if(result.compareTo(new BigDecimal(percent))>=0){
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
            result = result.divide(previous,0,RoundingMode.HALF_UP);
            result = result.multiply(new BigDecimal(100));
            if(result.compareTo(new BigDecimal(percent))>=0){
                return  true;
            }else {
                return false;
            }
        }
        return false;
    }
}
