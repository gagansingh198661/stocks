package com.example.stocks.dto;

import lombok.Data;

@Data
public class UpdateTokenRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
