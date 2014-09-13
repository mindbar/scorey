package com.mindbar.scorey.service;

import com.mindbar.scorey.model.Article;
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
        };
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
}
