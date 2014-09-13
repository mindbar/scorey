package com.mindbar.scorey.service;

import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author mishadoff
 */
public class SentimentService {
    private static String[] categories = new String[]{"pos", "tag"};

    public static void main(String[] args) {
        DynamicLMClassifier<NGramProcessLM> classifier =
                DynamicLMClassifier.createNGramProcess(categories, 8);
    }

    private static void train() {
        for (int i = 0; i < categories.length; i++) {
            String cat = categories[i];

        }
    }
}
