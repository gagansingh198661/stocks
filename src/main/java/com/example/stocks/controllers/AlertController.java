package com.example.stocks.controllers;

import com.example.stocks.dto.CreateAlertRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
public class AlertController {

    @CrossOrigin(origins = "http://localhost:8100")
    @PostMapping("/alert/createAlert")
    public HttpEntity<?> createAlert(CreateAlertRequest alertRequest){
        System.out.println("Alert Request : "+ alertRequest);
        return new HttpEntity<>(HttpStatus.CREATED);
    }
}
