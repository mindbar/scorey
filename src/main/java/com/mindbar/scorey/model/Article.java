package com.mindbar.scorey.model;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by aleksandr on 13.09.14.
 */
@Data
public class Article {
    private String url;
    private String title;
    private String text;

    public static Article parseFile(File file) throws Exception {
        Article article = new Article();

        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();

            article.setUrl(br.readLine());
            article.setTitle(br.readLine());

            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            article.setText(sb.toString());
        } finally {
            br.close();
        }

        return article;
    }
}
