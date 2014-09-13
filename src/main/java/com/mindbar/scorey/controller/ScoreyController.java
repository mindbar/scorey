package com.mindbar.scorey.controller;

import com.aliasi.classify.DynamicLMClassifier;
import com.mindbar.scorey.model.ScoreyResult;
import com.mindbar.scorey.service.ScoreyService;
import com.mindbar.scorey.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * @author mishadoff
 */
@Controller
public class ScoreyController {

    @Autowired
    private ScoreyService scoreyService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "search";
    }

    @PostConstruct
    public void init() {
        try {
            SentimentService.init();
            SentimentService.train();
        } catch (IOException io) {
            System.out.println("[ERROR] Reading file");
        }
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(
            @RequestParam(value = "q", defaultValue = "") String queryText,
            ModelMap map) {
        ScoreyResult result = scoreyService.process(queryText);
        map.addAttribute("result", result);
        map.addAttribute("query", queryText);
        return "search";
    }

    @RequestMapping(value = "json/search", method = RequestMethod.GET)
    public @ResponseBody ScoreyResult searchJson(
            @RequestParam(value = "q", defaultValue = "") String queryText,
            ModelMap map) {
        ScoreyResult result = scoreyService.process(queryText);
        return result;
    }
}
