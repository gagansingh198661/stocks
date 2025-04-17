package com.example.stocks.services;

import com.example.stocks.dto.AlertDTO;
import com.example.stocks.dto.InfoDTO;
import com.example.stocks.entity.SentEmail;
import com.example.stocks.repository.SentEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SentEmailRepository sentEmailRepository;

    private static Map<String,List<SentEmail>> stockSymbolEmailSentMap= new HashMap();

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("budsy.remo@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void process(Map<String, InfoDTO> mapSymbolStock) {
        List<InfoDTO> infoDTOList=mapSymbolStock.values().stream().toList();
        List<SentEmail> sentEmailList = new LinkedList<>();
        for (InfoDTO infoDTO:
             infoDTOList) {
            List<AlertDTO> alertDTOList=infoDTO.getAlertDTOList();
            for (AlertDTO alertDto:
                 alertDTOList) {

                if(notSent(alertDto,infoDTO)) {
                    sendEmail("iqalertsg@gmail.com", "IQALERT", alertDto.getMessage());
                    SentEmail sentEmail = new SentEmail();
                    sentEmail.setCurrentprice(alertDto.getCurrentprice());
                    sentEmail.setPreviousprice(alertDto.getpreviousprice());
                    sentEmail.setStocksymbol(infoDTO.getStock().getStockSymbol());
                    sentEmailList.add(sentEmail);
                    List<SentEmail> sentEmailList1=stockSymbolEmailSentMap.get(infoDTO.getStock().getStockSymbol());
                    if (sentEmailList1==null){
                        sentEmailList1=new LinkedList<>();
                    }
                    sentEmailList1.add(sentEmail);
                    sentEmailList.add(sentEmail);
                    stockSymbolEmailSentMap.put(infoDTO.getStock().getStockSymbol(),sentEmailList1);
                }
            }
        }
        sentEmailRepository.saveAll(sentEmailList);

    }

    private boolean notSent(AlertDTO alertDto, InfoDTO infoDTO) {
        List<SentEmail> sentEmailList =stockSymbolEmailSentMap.get(infoDTO.getStock().getStockSymbol());
        if(sentEmailList==null||sentEmailList.size()==0){
            sentEmailList=sentEmailRepository.findAllByStocksymbol(infoDTO.getStock().getStockSymbol());
            if(sentEmailList==null||sentEmailList.size()==0) {
                return true;
            }
            stockSymbolEmailSentMap.put(infoDTO.getStock().getStockSymbol(),sentEmailList);
        }
        for (SentEmail sentEmail:
             sentEmailList) {
            if (alertDto.getCurrentprice().equals(sentEmail.getCurrentprice())&&
                    alertDto.getpreviousprice().equals(sentEmail.getPreviousprice())){
                return false;
            }
        }
        return true;
    }
}
