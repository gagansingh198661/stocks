package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AlertDTO {
    private String message;

    private String currentprice;

    public String getCurrentprice() {
        return currentprice;
    }

    public AlertDTO(String message, String currentprice, String previousPrice, Action action, Type type) {
        this.message = message;
        this.currentprice = currentprice;
        this.previousprice = previousPrice;
        this.action = action;
        this.type = type;
    }

    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice;
    }

    public String getpreviousprice() {
        return previousprice;
    }

    public void setPreviousprice(String previousprice) {
        this.previousprice = previousprice;
    }

    private String previousprice;

    private Action action;

    private Type type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

}
