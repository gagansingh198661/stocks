package com.example.stocks;

import com.example.stocks.controllers.StocksController;
import com.example.stocks.dto.InfoDTO;
import com.example.stocks.entity.Alert;
import com.example.stocks.entity.Stock;
import com.example.stocks.firebase.dto.NotificationRequest;
import com.example.stocks.firebase.service.FCMService;
import com.example.stocks.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Component
public class StockScheduler {

    @Autowired
    StocksController controller;

    @Autowired
    StockService stockService;

    @Autowired
    FCMService fcmService;

    private ConcurrentHashMap<Long, Long> alertIdTimeStamp = new ConcurrentHashMap<Long, Long>();

    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();


    // @Scheduled(fixedDelay = 1000000,initialDelay = 5000)
    public void runHistoricDataFetch(){
        controller.generateData();
    }

    @Scheduled(fixedDelay = 15000,initialDelay = 10000)
    public void runCurrentDataFetch(){
        Map<String,String> map=controller.getUpdatedValues();

        List<Stock> stocks = stockService.getStocks();
        for (Stock stock : stocks) {
            if(stock.getStockSymbol()!=null){
                try {
                    if(map.get(stock.getStockSymbol())!=null){
                        BigDecimal price = new BigDecimal(map.get(stock.getStockSymbol()).trim());
                        stock.setPreviousPrice(stock.getCurrentPrice());
                        stock.setCurrentPrice(price);
                    }

                }
                catch(Exception e){
                    System.out.println("Error for : '"+map.get(stock.getStockSymbol()) + "' Exception e :"+e);
                    }
//            if(stock.isOwn()){
//                if(price.compareTo(stock.getCurrentPrice())>0){
//                    stock.setPreviousPrice(stock.getCurrentPrice());
//                    stock.setCurrentPrice(price);
//                }
//            }else{
//                if(stock.getCurrentPrice().compareTo(price)>0){
//                    stock.setPreviousPrice(stock.getCurrentPrice());
//                    stock.setCurrentPrice(price);
//                }
//            }

            }


        }
        stockService.updateAll(stocks);
        List<InfoDTO> list =controller.getNotifications();
        if(list!=null){
            List<Alert> alerts = new LinkedList<>();

            for (InfoDTO infoDTO : list){
                if(infoDTO.getAlerts()!=null)
                for(Alert alert :infoDTO.getAlerts()) {
                    if (alert.getAlertDTO() != null) {
                        Long timestamp = alertIdTimeStamp.get(alert.getId());
                        if (timestamp == null) {
                            alertIdTimeStamp.put(alert.getId(), System.currentTimeMillis());
                        } else {
                            long timeElapsed = System.currentTimeMillis() - timestamp;
                            timeElapsed = timeElapsed / 60000l;
                            if (timeElapsed < 2)
                                continue;
                            else {
                                alertIdTimeStamp.put(alert.getId(), System.currentTimeMillis());
                            }
                        }
                        System.out.println("getAlertDTO : " + alert.getAlertDTO());
                        NotificationRequest request = fcmService.createNotificationRequest(alert);
                        System.out.println("Token : " + request.getToken());
                        try {
                            fcmService.sendMessageToToken(request);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            }
        }

    }


   // @Scheduled(fixedDelay = 15000,initialDelay = 5000)
    public void runCurrentDataFetchTest(){
        Map<String,String> map=controller.mapForTesting();

        List<Stock> stocks = stockService.getStocks();
        for (Stock stock : stocks) {
            BigDecimal price = new BigDecimal(map.get(stock.getStockSymbol()));
            if(stock.isOwn()){
                if(price.compareTo(stock.getCurrentPrice())>0){
                    stock.setPreviousPrice(stock.getCurrentPrice());
                    stock.setCurrentPrice(price);
                }
            }else{
                if(stock.getCurrentPrice().compareTo(price)>0){
                    stock.setPreviousPrice(stock.getCurrentPrice());
                    stock.setCurrentPrice(price);
                }
            }

        }
        stockService.updateAll(stocks);
    }




}
