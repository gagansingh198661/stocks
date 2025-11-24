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
            List<Alert> alerts= infoDTO.getAlerts();
            if(alerts!=null&&alerts.size()>0) {
                for (Alert alert : alerts) {
                    if (alert.getAlertType().equals(AlertType.TARGET.name())&&alert.isActive()) {
                        if(alert.getLowerlimit()!=null&&currentPrice!=null) {
                            if(alert.getType()!=null){
                                if (alert.getType().equals("Increase")&&currentPrice.doubleValue() >= alert.getLowerlimit().doubleValue()) {
                                    AlertDTO alertDTO = new AlertDTO("Target Price reached : " + alert.getLowerlimit().doubleValue(), currentPrice.toPlainString(), alert.getLowerlimit().toString(), Action.SELL, Type.IMPORTANT);
                                    alert.setAlertDTO(alertDTO);
                                }
                                else if (alert.getType().equals("Decrease")&&currentPrice.doubleValue() <= alert.getLowerlimit().doubleValue()) {
                                    AlertDTO alertDTO = new AlertDTO("Target Price reached : " + alert.getLowerlimit().doubleValue(), currentPrice.toPlainString(), alert.getLowerlimit().toString(), Action.BUY, Type.IMPORTANT);
                                    alert.setAlertDTO(alertDTO);
                                }
                            }

                        }
                    }
                }
            }


        }
        return infoDTO;
    }
}
