package com.mindbar.scorey.service;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;
import com.mindbar.scorey.util.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;

/**
 * @author mishadoff
 */
public class SentimentService {
    private static String[] categories = new String[]{"pos", "neg"};
    public static DynamicLMClassifier<NGramProcessLM> classifier;
    public static HashSet<String> goodWordsSet = new HashSet<String>();
    public static HashSet<String> badWordsSet = new HashSet<String>();

    public static void init() {
        classifier = DynamicLMClassifier.createNGramProcess(categories, 8);
    }

    public static void initPosNeg() throws IOException {
        String goodWordsString = Files.readFromFile(new ClassPathResource("common/pos.txt").getFile(), "UTF-8");
        String[] goodWords = StringUtils.tokenize(goodWordsString);
        for (String s : goodWords) {
            goodWordsSet.add(s);
        }
        String badWordsString = Files.readFromFile(new ClassPathResource("common/neg.txt").getFile(), "UTF-8");
        String[] badWords = StringUtils.tokenize(badWordsString);
        for (String s : badWords) {
            badWordsSet.add(s);
        }
    }

    public static void main(String[] args) throws IOException {
        classifier = DynamicLMClassifier.createNGramProcess(categories, 8);
        System.out.println("Start training...");
        train();
        System.out.println("Finished training...");
    }

    public static void train() throws IOException {
        for (int i = 0; i < categories.length; i++) {
            String cat = categories[i];
            ClassLoader classLoader = SentimentService.class.getClassLoader();
            File polarityDir = new File(classLoader.getResource("data/" + cat).getFile());
            Classification c = new Classification(cat);
            for (File f : polarityDir.listFiles()) {
                System.out.println("Train: " + f.getName());
                String s = Files.readFromFile(f, "UTF-8");
                Classified<CharSequence> classified = new Classified<CharSequence>(s, c);
                classifier.handle(classified);
            }


        }
    }
}
