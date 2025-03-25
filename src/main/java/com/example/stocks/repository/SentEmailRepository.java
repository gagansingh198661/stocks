package com.example.stocks.repository;

import com.example.stocks.entity.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail,Long> {
    List<SentEmail> findAllByStocksymbol(String stockSymbol);
}
