package com.example.stocks.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StocksConfig {

    @Bean
    public WebDriver getDriver(){
        return new FirefoxDriver();
    }
}
