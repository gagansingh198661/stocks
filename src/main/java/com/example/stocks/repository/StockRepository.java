package com.example.stocks.repository;

import com.example.stocks.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByStockSymbol(String stockSymbol);

    List<Stock> findAllByActive(boolean active);
}
