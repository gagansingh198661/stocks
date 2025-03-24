package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AlertDTO {
    private String message;

    private String price;

    private Action action;

    private Type type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public AlertDTO(){}
    public AlertDTO(String message,String price,Action action,Type type){
        this.message=message;
        this.price=price;
        this.action=action;
        this.type=type;
    }
}
