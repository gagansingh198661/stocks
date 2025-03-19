package com.example.stocks;

import com.example.stocks.controllers.StocksController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockScheduler {

    @Autowired
    StocksController controller;

   // @Scheduled(fixedDelay = 1000000,initialDelay = 5000)
    public void runHistoricDataFetch(){
        controller.generateData();
    }

    @Scheduled(fixedDelay = 10000,initialDelay = 5000)
    public void runCurrentDataFetch(){
        controller.getUpdatedValues();
    }
}
