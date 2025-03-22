package com.example.stocks.rules;

import com.example.stocks.dto.InfoDTO;

import java.util.Map;

public interface Rule {
    public InfoDTO applyRule(InfoDTO infoDTO);
}
