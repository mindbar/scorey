package com.mindbar.scorey.service;

import com.mindbar.scorey.metrics.Metric;
import com.mindbar.scorey.model.Article;
import com.mindbar.scorey.util.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksandr on 13.09.14.
 */
@Service
public class ArticleService {

    public List<Article> getArtilesByDevice(String device) throws Exception {
        ArrayList<Article> articles = new ArrayList<Article>();
        File folder = new ClassPathResource("devices/" + device).getFile();
        for (File file : listFilesForFolder(folder)) {
            articles.add(Article.parseFile(file));
        }
        return articles;
    }

    private List<File> listFilesForFolder(final File folder) {
        List<File> files = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry);
            }
        }
        return files;
    }

    public int[] processArticle(Article a, String category) {
        String article = a.getText().toLowerCase();
        String[] articleTokens = StringUtils.tokenize(article);
        List<String> snippetWords = StringUtils.getSnippetTokens(category, articleTokens);
        int pos = 0;
        int neg = 0;

        for (String word : snippetWords) {
            if (SentimentService.goodWordsSet.contains(word)) {
                pos++;
            }
            if (SentimentService.badWordsSet.contains(word)) {
                neg++;
            }
        }
        return new int[]{pos, neg};
    }

    public double scoreForMetric(String device, Metric metric) throws Exception {
        List<Article> articles = getArtilesByDevice(device);
        int totalPos = 0;
        int totalNeg = 0;
        for (Article a : articles) {
            System.out.println("Processing article: " + a.getUrl());
            int[] scores = processArticle(a, metric.getKey());
            System.out.println("Scores: POS=" + scores[0] + " NEG=" + scores[1]);
            totalPos += scores[0];
            totalNeg += scores[1];
        }
        return totalPos * 10.0 / (totalPos + totalNeg + 0.001); // TODO handle no data
    }
}
