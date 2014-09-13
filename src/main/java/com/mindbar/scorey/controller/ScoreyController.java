package com.mindbar.scorey.controller;

import com.mindbar.scorey.model.Article;
import com.mindbar.scorey.model.ScoreyResult;
import com.mindbar.scorey.service.ArticleService;
import com.mindbar.scorey.service.ScoreyService;
import com.mindbar.scorey.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author mishadoff
 */
@Controller
public class ScoreyController {

    @Value( "${test.prop}" )
    private String testProp;

    @Value( "${sentimentService.train}" )
    private boolean train;

    @Autowired
    private ScoreyService scoreyService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        try {
            List<Article> iphone5 = articleService.getArtilesByDevice("iphone5");
            System.out.println(iphone5.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "search";
    }

    @PostConstruct
    public void init() {
        try {
            System.out.println(testProp);
            if (train) {
                SentimentService.init();
                SentimentService.train();
            }
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
