package com.example.stocks.controllers;

import com.example.stocks.dto.CreateAlertRequest;
import com.example.stocks.dto.InfoDTO;
import com.example.stocks.entity.Alert;
import com.example.stocks.entity.Stock;
import com.example.stocks.ruleEngine.RuleEngine;
import com.example.stocks.rules.BuyMoreRule;
import com.example.stocks.rules.PercentRule;
import com.example.stocks.rules.Rule;
import com.example.stocks.rules.TargetRule;
import com.example.stocks.services.AlertService;
import com.example.stocks.services.EmailService;
import com.example.stocks.services.StockService;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StocksController {

    @Value("${longTermStocks}")
    private String longTermStocks;

    @Value("${shortTermStocks}")
    private String shortTermStocks;

    @Autowired
    private WebDriver driver;

    @Autowired
    private StockService stockService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private EmailService emailService;

    static Map<String, InfoDTO> mapSymbolStock= new HashMap<>();

    static Map<String,String> stockSymbol_CurrentPriceMap1 = new HashMap<>();

    @GetMapping("/generateData")
    public void generateData(){
        List<String> urlist=createUrlListForHistory();
        List<String> stockInfo=new LinkedList<>();
        //options.addArguments(ChromeOptions.);
        for (String url : urlist) {
            driver.get(url);
            String header = getHeaderInfo();
            stockInfo.add(url);
            stockInfo.add(header);
            stockInfo.addAll(getHistoricalData());
        }
        writeToFile(stockInfo);
        System.out.println("Data Scraping finished");
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/getData")
    public InfoDTO[] getData(){
        InfoDTO[] infoDtoArray = new InfoDTO[mapSymbolStock.values().size()];
        int counter = 0;
        for (InfoDTO infoDTO : mapSymbolStock.values()) {
            infoDtoArray[counter] = infoDTO;
            counter++;
        }
        return infoDtoArray;
    }

    @CrossOrigin(origins = "http://localhost:8100")
    @GetMapping("/getStock")
    public InfoDTO getStock(@RequestParam(required = true) String stockSymbol){
        InfoDTO infoDto = stockService.getStock(stockSymbol);
        RuleEngine.applyRules(infoDto,infoDto.getAlerts());
        mapSymbolStock.put(stockSymbol,infoDto);
        return infoDto;
    }


    @PutMapping("/update")
    public Stock updateStock(Stock stock){
        return stockService.update(stock);
    }



    @PutMapping("/updateMapTesting/")
    public Map<String, String> updateStockMap(String symbol, String price){
        stockSymbol_CurrentPriceMap1.put(symbol,price);
        return stockSymbol_CurrentPriceMap1;
    }

    public Map<String,String> mapForTesting(){
        return stockSymbol_CurrentPriceMap1;
    }


    private void writeToFile(List<String> data){
        try{
            String fileLocation = new File("C:/Users/Admin/Desktop/QuestTrade")+ "/stocks.txt";
            BufferedWriter fos = new BufferedWriter(new FileWriter(fileLocation));
            for (String str:data){
                fos.write(str+"\n");
            }
            fos.close();
        }catch(IOException exception){
            System.out.println(exception);
        }

    }


    private List<Rule> addRules(){
        TargetRule targetRule = new TargetRule();
        PercentRule percentRule = new PercentRule();
        BuyMoreRule buyMoreRule = new BuyMoreRule();
        List<Rule> ruleList = new LinkedList<>();
        ruleList.add(targetRule);
        ruleList.add(percentRule);
        ruleList.add(buyMoreRule);
        return ruleList;
    }

    public Map<String,String> getUpdatedValues(){

        RuleEngine.setRuleList(addRules());
        Map<String,String> stockSymbolURLMap = createUrlMapForBasic();
        Map<String,String> stockSymbol_CurrentPriceMap = new HashMap<>();
        for(Map.Entry<String,String> entry:stockSymbolURLMap.entrySet()){
           String url = entry.getValue();
           if(isConnectedToInternet()){
               String price = getCurrentValue(url);
               stockSymbol_CurrentPriceMap.put(entry.getKey(),price);
           }
        }
        updateResponseMap(stockSymbol_CurrentPriceMap);
        return stockSymbol_CurrentPriceMap;

    }

    private boolean isConnectedToInternet(){
        try (Socket socket = new Socket("www.google.com", 80)) {
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    private void updateResponseMap(Map<String, String> stockSymbolCurrentPriceMap) {
        stockSymbolCurrentPriceMap.forEach((x,y)->{
            InfoDTO infoDTO = mapSymbolStock.get(x);
            if(infoDTO==null){
                infoDTO = new InfoDTO();
            }
            Stock stock= infoDTO.getStock();
            if (stock==null){
                stock=stockService.findByStockSymbol(x);
            }
            if (stock == null) {
                System.out.println("Stock : "+y);
                stock=createStock(x,y);
            }
            System.out.println(x+" :  "+y);
            stock.setCurrentPrice(new BigDecimal(y));
            infoDTO.setStock(stock);
            mapSymbolStock.put(stock.getStockSymbol(),infoDTO);
        });
        List<Alert> alerts = alertService.getAlerts();
        Map<String,List<Alert>> mapSymbolAlertList = new HashMap<>();
        alerts.stream().forEach(x-> {
            mapSymbolAlertList.putIfAbsent(x.getStocksymbol(), new LinkedList<Alert>());
            List<Alert> alertList=mapSymbolAlertList.get(x.getStocksymbol());
            alertList.add(x);
            mapSymbolAlertList.put(x.getStocksymbol(), alertList);
        });
        for(Map.Entry<String,List<Alert>> entry : mapSymbolAlertList.entrySet()){
            String symbol = entry.getKey();
            InfoDTO infoDTO=mapSymbolStock.get(symbol);
            infoDTO.setAlertDTOList(null);
            infoDTO.setAlerts(entry.getValue());
            RuleEngine.applyRules(infoDTO,entry.getValue());
        }
        checkAndSendMail(mapSymbolStock);

        //checkForAlerts
        //basedonalerts set action
    }

    private void checkAndSendMail(Map<String, InfoDTO> mapSymbolStock) {
        emailService.process(mapSymbolStock);
    }

    private Stock createStock(String symbol,String currentPrice){

        System.out.println("Symbol :"+ symbol+"  price :"+currentPrice);
        Stock stock = new Stock();
        stock.setStockSymbol(symbol);
        stock.setCurrentPrice(new BigDecimal(currentPrice));
        stock.setPreviousPrice(new BigDecimal(currentPrice));
        return stockService.update(stock);
    }

    private Map<String,String> createUrlMapForBasic(){
        List<String> stocksList=stockService.getStocks().stream().map(x->x.getStockSymbol()).toList();
        Map<String,String> stockSymbolURLMap= stocksList.stream().collect(Collectors.toMap(x-> x,x-> "https://finance.yahoo.com/quote/"+x+"/"));
        return stockSymbolURLMap;
    }
    private String getCurrentValue(String url){
        driver.get(url);
        List<WebElement>  spanElements = driver.findElements(By.tagName("span"));
        for (WebElement element:
             spanElements) {
            String value = element.getAttribute("data-testid");
            if(value!=null){
                String price = element.getAttribute("innerHTML");
                return price;
            }
        }
        return new String();
    }


    private List<String> getHistoricalData() {
        WebElement tBody = driver.findElement(By.cssSelector("#nimbus-app > section > section > section > article > div.container > div.table-container.yf-1jecxey > table > tbody"));
        List<WebElement> trElements = tBody.findElements(By.tagName("tr"));
        List<String> stockData = new LinkedList<>();
        for (WebElement row:
                trElements) {
            List<WebElement> tdElements = row.findElements(By.tagName("td"));
            StringBuilder rowData = new StringBuilder("");
            for (WebElement tdElement:
                    tdElements) {
                if(rowData.isEmpty()){
                    rowData = new StringBuilder(tdElement.getAttribute("innerHTML"));
                }else{
                    rowData = rowData.append("|").append(tdElement.getAttribute("innerHTML"));
                }
            }
            stockData.add(rowData.toString());
        }
        return stockData;

    }

    private String getHeaderInfo() {
        WebElement table_container = driver.findElement(By.cssSelector("#nimbus-app > section > section > section > article > div.container > div.table-container"));
        WebElement tHeadElem = table_container.findElement(By.tagName("thead"));
        String str = "";
        List<WebElement> headers =  tHeadElem.findElements(By.tagName("th"));
        for (WebElement header: headers) {
            String innerHtml = header.getAttribute("innerHTML");
            int index = innerHtml.indexOf("<");
            if(str.equals("")) {
                str = innerHtml;
            }else {
                if(innerHtml.indexOf("<")==-1){
                    str = str + "|" + header.getAttribute("innerHTML");
                }else
                    str = str + "|" + header.getAttribute("innerHTML").substring(0,index);
            }
        }
        return str;
    }

    private List<String> generateStockList(){
        List<String> allStocks = new LinkedList<>();

        List<String> shortTermStocksList = Arrays.stream(shortTermStocks.split(",")).toList();
        List<String> longTermStocksList = Arrays.stream(longTermStocks.split(",")).toList();
        allStocks.addAll(shortTermStocksList);
        allStocks.addAll(longTermStocksList);

        return allStocks;
    }

    private List<String> createUrlListForHistory(){
        List<String> stocksList=generateStockList();
        List<String> urlList=stocksList.stream().map(x-> "https://finance.yahoo.com/quote/"+x+"/history/").toList();
        return urlList;
    }
}
