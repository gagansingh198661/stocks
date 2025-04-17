package com.example.stocks.rules;

import com.example.stocks.dto.InfoDTO;
import com.example.stocks.entity.Alert;

import java.util.List;
import java.util.Map;

public interface Rule {
    public InfoDTO applyRule(InfoDTO infoDTO, List<Alert> stockAlerts);
}
