package com.mindbar.scorey.controller;

import com.mindbar.scorey.service.ScoreyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(
            @RequestParam(value = "q", defaultValue = "") String queryText,
            ModelMap map) {
        return "search";
    }
}
