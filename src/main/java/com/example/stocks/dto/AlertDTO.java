package com.example.stocks.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlertDTO {
    private String message;

    private String price;

    private Action action;

    private Type type;
}
