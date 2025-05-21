package com.example.stocks.controllers;

import com.example.stocks.dto.CreateAlertRequest;
import com.example.stocks.entity.Alert;
import com.example.stocks.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
public class AlertController {

    @Autowired
    private AlertService alertService;

    @CrossOrigin(origins = "http://localhost:8100")
    @PostMapping("/alert/createAlert")
    public HttpEntity<?> createAlert(@RequestParam CreateAlertRequest alertRequest){
        System.out.println("Alert Request : "+ alertRequest);
        try{
            alertService.createAlert(alertRequest);
            return new HttpEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new HttpEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @DeleteMapping("/alert/deleteAlert")
    public HttpEntity<?> deleteAlert(@RequestParam Long id){
        System.out.println("Delete alert : "+ id);
        try{
            alertService.deleteAlert(id);
            return new HttpEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new HttpEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @PatchMapping("/alert/patch")
    public HttpEntity<?> patch(@RequestBody Alert alert){
        System.out.println("Patch alert : "+ alert);
        try{
            alertService.updateAlert(alert);
            return new HttpEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new HttpEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
