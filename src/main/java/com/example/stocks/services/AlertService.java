package com.example.stocks.services;

import com.example.stocks.entity.Alert;
import com.example.stocks.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public List<Alert> getAlerts(){
        return alertRepository.findAllByActive(true);
    }

}
