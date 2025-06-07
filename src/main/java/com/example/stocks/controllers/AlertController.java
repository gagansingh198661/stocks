package com.example.stocks.controllers;

import com.example.stocks.dto.AlertRequest;
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


    @PostMapping("/alert")
    public Boolean createAlert(@RequestBody AlertRequest alertRequest){
        System.out.println("Alert Request : "+ alertRequest);
        try{
            return alertService.createAlert(alertRequest);
        }catch(Exception e){
            return false;
        }
    }

    @DeleteMapping("/alert/{id}")
    public Boolean deleteAlert(@PathVariable Long id){
        System.out.println("Delete alert : "+ id);
        try{
            alertService.deleteAlert(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @PatchMapping("/alert/{id}/{state}")
    public Boolean toggleAlert(@PathVariable Long id,@PathVariable Boolean state){
        try{
            Alert alert = alertService.getAlertById(id);
            alert.setActive(state);
            alertService.updateAlert(alert);
            return true;
        }catch(Exception e){
            return false;
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
