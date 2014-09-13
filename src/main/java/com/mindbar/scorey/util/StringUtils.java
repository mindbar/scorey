package com.mindbar.scorey.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mishadoff
 */
public final class StringUtils {
    private StringUtils() {}

    public static String[] tokenize(String article) {
        String replaced = article.replaceAll("[!?,]", "");
        String[] strs = replaced.split("\\s+");
        return strs;
    }

    public static List<String> getSnippetTokens(String metric, String[] tokens) {
        int SIZE = 10;

        List<String> snippetWords = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            String currentValue = tokens[i];
            if (currentValue.equalsIgnoreCase(metric)) {
                int from = Math.max(0, i - SIZE);
                int to = Math.min(i + SIZE, tokens.length);
                for (int j = from; j < to; j++) {
                    snippetWords.add(tokens[j]);
                }
            }
        }

        return snippetWords;
    }
}
