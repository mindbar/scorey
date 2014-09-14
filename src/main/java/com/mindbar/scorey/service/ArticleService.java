package com.mindbar.scorey.service;

import com.aliasi.util.Files;
import com.mindbar.scorey.metrics.Metric;
import com.mindbar.scorey.model.Article;
import com.mindbar.scorey.model.ArticleMeta;
import com.mindbar.scorey.util.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aleksandr on 13.09.14.
 */
@Service
public class ArticleService {

    public List<Article> getArticlesByDevice(String device) throws Exception {
        ArrayList<Article> articles = new ArrayList<Article>();
        ClassPathResource classPathResource = new ClassPathResource("devices/" + device);
        if (classPathResource == null) {
            throw new Exception("Not found training samples");
        }
        File folder = classPathResource.getFile();
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

    public int[] processArticle(Article a, String category) throws IOException {
        String article = a.getText().toLowerCase();
        String[] articleTokens = StringUtils.tokenize(article);
        List<String> snippetWords = StringUtils.getSnippetTokens(category, articleTokens);

        snippetWords.addAll(SentimentService.synonymsSet.get(category));

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

    public List<ArticleMeta> scoreForMetric(String device) throws Exception {
        List<Article> articles = getArticlesByDevice(device);
        List<ArticleMeta> metas = new ArrayList<ArticleMeta>();
        for (Article a : articles) {
            ArticleMeta meta = new ArticleMeta();
                meta.setTitle(a.getTitle());
                meta.setUrl(a.getUrl());
                Map<Metric, Double> articleScores = new HashMap<Metric, Double>();
            for (Metric m : Metric.values()) {
                System.out.println("Processing article: " + a.getUrl().hashCode() + " for metric " + m.getKey());
                int[] scores = processArticle(a, m.getKey());
                int pos = scores[0];
                int neg = scores[1];
                double score = 5.0;
                if (pos + neg > 0) {
                    score = pos * 10.0 / (pos + neg);
                }
                articleScores.put(m, score);
            }
            meta.setArticleScores(articleScores);
        }
        return metas;
    }


}
