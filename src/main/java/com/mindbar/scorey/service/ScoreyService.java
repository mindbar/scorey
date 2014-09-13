package com.mindbar.scorey.service;

import com.mindbar.scorey.model.ScoreyResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mishadoff
 */
@Service
public class ScoreyService {

    public ScoreyResult process(String query) {
        ScoreyResult result = new ScoreyResult();

        Map<String, Double> scores = new HashMap<String, Double>();
            scores.put("Battery", 7.3);
            scores.put("Price", 9.2);
            scores.put("Performance", 8.0);
            scores.put("Display", 6.1);

        result.setScores(scores);
        return result;
    }
}
