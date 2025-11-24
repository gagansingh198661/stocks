package com.example.stocks.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class NotificationResponse {
    private int status;
    private String message;
    public NotificationResponse(int status,String message){
        this.status=status;
        this.message=message;
    }

}
