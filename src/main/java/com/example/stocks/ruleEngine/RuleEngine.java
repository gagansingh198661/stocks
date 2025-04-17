package com.example.stocks.ruleEngine;

import com.example.stocks.dto.InfoDTO;
import com.example.stocks.entity.Alert;
import com.example.stocks.rules.Rule;

import java.util.List;
import java.util.Map;

public class RuleEngine {
    static List<Rule> ruleList;

    public static InfoDTO applyRules(InfoDTO infoDTO, List<Alert> stockAlerts){
        for (Rule rule:
             ruleList) {
             infoDTO = rule.applyRule(infoDTO,stockAlerts);
        }
        return infoDTO;
    }

    public static void setRuleList(List<Rule> ruleList) {
        RuleEngine.ruleList = ruleList;
    }
}
