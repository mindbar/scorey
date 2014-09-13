package com.mindbar.scorey.service;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author mishadoff
 */
public class SentimentService {
    private static String[] categories = new String[]{"pos", "neg"};
    static DynamicLMClassifier<NGramProcessLM> classifier;

    public static void main(String[] args) throws IOException {
        classifier = DynamicLMClassifier.createNGramProcess(categories, 8);
        System.out.println("Start training...");
        train();
        System.out.println("Finished training...");

        while (true) {
            System.out.println("> ");
            String input = new Scanner(System.in).nextLine();
            if ("quit".equals(input)) {
                System.out.println("Bye!");
            } else {
                Classification c = classifier.classify(input);
                System.out.println("Best category: " + c.bestCategory());
            }
        }
    }

    public static void train() throws IOException {
        for (int i = 0; i < categories.length; i++) {
            String cat = categories[i];

            File polarityDir = new File("/home/mishadoff/coding/hack/scorey/src/main/resources/data/" + cat);
            Classification c = new Classification(cat);
            for (File f : polarityDir.listFiles()) {
                System.out.println("Train: " + f.getName());
                String s = Files.readFromFile(f, "UTF-8");
                Classified<CharSequence> classified
                        = new Classified<CharSequence>(s, c);
                classifier.handle(classified);
            }


        }
    }
}
