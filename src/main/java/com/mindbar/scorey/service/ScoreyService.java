package com.mindbar.scorey.service;

import com.aliasi.classify.Classified;
import com.mindbar.scorey.metrics.MetricConstants;
import com.mindbar.scorey.model.ScoreyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mishadoff
 */
@Service
public class ScoreyService {

    @Autowired
    ArticleService articleService;

    public ScoreyResult process(String query) throws Exception {
        ScoreyResult result = new ScoreyResult();


        Map<String, String> scores = new HashMap<String, String>();
            scores.put("Battery", String.format("%.2f", articleService.scoreForCategory(query, MetricConstants.BATTERY)));
            scores.put("Price", "" + 9.2);
            scores.put("Performance", "" + 8.0);
            scores.put("Display", "" + 6.1);

        result.setScores(scores);
        //result.setBestCategory(SentimentService.classifier.classify(query).bestCategory());
        return result;
    }
}
