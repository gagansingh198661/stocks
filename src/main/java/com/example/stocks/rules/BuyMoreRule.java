package com.example.stocks.rules;

import com.example.stocks.dto.*;
import com.example.stocks.entity.Alert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BuyMoreRule implements Rule{
    @Override
    public InfoDTO applyRule(InfoDTO infoDTO, List<Alert> stockAlerts) {
        List<AlertDTO> alertDTOList = infoDTO.getAlertDTOList();
        if (alertDTOList==null){
            alertDTOList=new LinkedList<>();
        }
        BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
        BigDecimal boughtPrice = infoDTO.getStock().getBoughtPrice();
        if (boughtPrice==null){
            return infoDTO;
        }
        if (stockAlerts!=null&&stockAlerts.size()!=0){
            for (Alert alert:
                    stockAlerts) {
                if (alert.getAlertType().equals(AlertType.BUYMORE.name())){
                    int percent = alert.getPercent();
                    if(belowPercent(boughtPrice,currentPrice,percent)){
                        AlertDTO alertDTO = new AlertDTO("Stock : "+ infoDTO.getStock().getStockSymbol() +" has declined by "+percent+"%", boughtPrice.toPlainString(),currentPrice.toString(), Action.HOLD, Type.NORMAL);
                        alertDTOList.add(alertDTO);
                    }
                }
            }
        }
        return null;
    }

    private boolean belowPercent(BigDecimal bought, BigDecimal current, int percent) {
        if(bought.compareTo(current)>0){
            BigDecimal result = bought.subtract(current);
            result = result.multiply(new BigDecimal(100));
            result = result.abs();
            result = result.divide(bought,0, RoundingMode.HALF_UP);
            if(result.compareTo(new BigDecimal(percent))>=0){
                return  true;
            }else {
                return false;
            }
        }
        return false;
    }
}
