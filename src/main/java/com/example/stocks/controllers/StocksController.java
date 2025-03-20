package com.example.stocks.controllers;

import com.example.stocks.entity.Stock;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public List<Stock> getData(){
        List<Stock> stockList =  stockService.getStocks();
        return stockList;
    }

    @PutMapping("/update")
    public Stock updateStock(Stock stock){
        return stockService.update(stock);
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


    public Map<String,String> getUpdatedValues(){
       Map<String,String> stockSymbolURLMap = createUrlMapForBasic();
       Map<String,String> stockSymbol_CurrentPriceMap = new HashMap<>();
       for(Map.Entry<String,String> entry:stockSymbolURLMap.entrySet()){
           String url = entry.getValue();
           String price = getCurrentValue(url);
           stockSymbol_CurrentPriceMap.put(entry.getKey(),price);
       }
       return stockSymbol_CurrentPriceMap;

    }

    private Map<String,String> createUrlMapForBasic(){
        List<String> stocksList=generateStockList();
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
