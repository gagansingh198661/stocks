package com.example.stocks.services;

import com.example.stocks.entity.Stock;
import com.example.stocks.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getStocks(){
        return stockRepository.findAll();
    }

    public Stock update(Stock stock){
        return stockRepository.saveAndFlush(stock);
    }

    public List<Stock> updateAll(List<Stock> stocks){
        return stockRepository.saveAllAndFlush(stocks);
    }

    public Stock findByStockSymbol(String stockSymbol){
        System.out.println(stockSymbol);
        return stockRepository.findByStockSymbol(stockSymbol);
    }
}
