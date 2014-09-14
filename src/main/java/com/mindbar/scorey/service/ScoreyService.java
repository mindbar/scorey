package com.mindbar.scorey.service;

import com.mindbar.scorey.metrics.Metric;
import com.mindbar.scorey.model.ArticleMeta;
import com.mindbar.scorey.model.ScoreyResult;
import com.mindbar.scorey.util.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

        List<ArticleMeta> meta = articleService.scoreForMetric(query);
        Map<String, String> scores = new HashMap<String, String>();

        double totalBattery = 0;
        double totalPerformance = 0;
        double totalDisplay = 0;
        for (ArticleMeta am : meta) {
            totalBattery += am.getArticleScores().get(Metric.BATTERY);
            totalPerformance += am.getArticleScores().get(Metric.PERFORMANCE);
            totalDisplay += am.getArticleScores().get(Metric.DISPLAY);
        }
        totalBattery /= meta.size();
        totalPerformance /= meta.size();
        totalDisplay /= meta.size();

            scores.put("Battery", FormatUtils.formatDouble(totalBattery));
            scores.put("Performance", FormatUtils.formatDouble(totalPerformance));
            scores.put("Display", FormatUtils.formatDouble(totalDisplay));

        articleService.getArticlesByDevice(query);

        result.setScores(scores);
        result.setArticles(meta);
        //result.setBestCategory(SentimentService.classifier.classify(query).bestCategory());
        return result;
    }
}
