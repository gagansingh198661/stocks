package com.example.stocks.controllers;

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

}
