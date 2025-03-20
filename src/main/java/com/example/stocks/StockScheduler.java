package com.example.stocks;

import com.example.stocks.controllers.StocksController;
import com.example.stocks.entity.Stock;
import com.example.stocks.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StockScheduler {

    @Autowired
    StocksController controller;

    @Autowired
    StockService stockService;

   // @Scheduled(fixedDelay = 1000000,initialDelay = 5000)
    public void runHistoricDataFetch(){
        controller.generateData();
    }

    @Scheduled(fixedDelay = 10000,initialDelay = 5000)
    public void runCurrentDataFetch(){
        Map<String,String> map=controller.getUpdatedValues();

        List<Stock> stocks = stockService.getStocks();
        for (Stock stock : stocks) {
            String price = map.get(stock.getStockSymbol());
            stock.setCurrentPrice(new BigDecimal(price));
        }
        stockService.updateAll(stocks);
    }
}
