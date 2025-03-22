package com.example.stocks.rules;

import com.example.stocks.dto.Action;
import com.example.stocks.dto.AlertDTO;
import com.example.stocks.dto.InfoDTO;
import com.example.stocks.dto.Type;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TargetRule implements Rule{

    @Override
    public InfoDTO applyRule(InfoDTO infoDTO) {
        BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
        BigDecimal targetPrice = infoDTO.getStock().getTargetPrice();
        List<AlertDTO> alertList = infoDTO.getAlertList();
        if(infoDTO.getStock().isOwn()){
            if(currentPrice.compareTo(targetPrice)>=0){
                AlertDTO alert = new AlertDTO("Target Price reached : Sell Stock",targetPrice.toString(), Action.SELL, Type.IMPORTANT);
                alertList.add(alert);
            }
        }else{
            if(currentPrice.compareTo(targetPrice)<=0){
                AlertDTO alert = new AlertDTO("Target Price reached : Buy Stock",targetPrice.toString(), Action.SELL, Type.IMPORTANT);
                alertList.add(alert);
            }
        }

        return infoDTO;
    }
}
