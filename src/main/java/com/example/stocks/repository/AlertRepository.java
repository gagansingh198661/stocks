package com.example.stocks.repository;

import com.example.stocks.entity.Alert;
import com.example.stocks.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {

    List<Alert> findAllByActive(boolean active);

}
