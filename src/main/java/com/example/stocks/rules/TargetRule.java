package com.example.stocks.rules;

import com.example.stocks.dto.Action;
import com.example.stocks.dto.AlertDTO;
import com.example.stocks.dto.InfoDTO;
import com.example.stocks.dto.Type;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class TargetRule implements Rule{

    @Override
    public InfoDTO applyRule(InfoDTO infoDTO) {

        List<AlertDTO> alertList = infoDTO.getAlertDTOList();
        if (alertList==null){
            alertList=new LinkedList<AlertDTO>();
            infoDTO.setAlertDTOList(alertList);
        }
        if(infoDTO.getStock()!=null){
            BigDecimal currentPrice = infoDTO.getStock().getCurrentPrice();
            BigDecimal targetPrice = infoDTO.getStock().getTargetPrice();
            if(currentPrice!=null&&targetPrice!=null){
                if(infoDTO.getStock().isOwn()){
                    if(currentPrice.compareTo(targetPrice)>=0){
                        AlertDTO alert = new AlertDTO("Target Price reached : Sell Stock : "+infoDTO.getStock().getStockSymbol(), currentPrice.toPlainString(), targetPrice.toString(), Action.SELL, Type.IMPORTANT);
                        alertList.add(alert);
                    }
                }else{
                    if(currentPrice.compareTo(targetPrice)<=0){
                        AlertDTO alert = new AlertDTO("Target Price reached : Buy Stock", currentPrice.toPlainString(), targetPrice.toString(), Action.SELL, Type.IMPORTANT);
                        alertList.add(alert);
                    }
                }
            }
        }
        return infoDTO;
    }
}
