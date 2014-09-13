package com.mindbar.scorey.service;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author mishadoff
 */
public class SentimentService {
    private static String[] categories = new String[]{"pos", "neg"};
    public static DynamicLMClassifier<NGramProcessLM> classifier;

    public static void init() {
        classifier = DynamicLMClassifier.createNGramProcess(categories, 8);
    }

    public static void main(String[] args) throws IOException {
        classifier = DynamicLMClassifier.createNGramProcess(categories, 8);
        System.out.println("Start training...");
        train();
        System.out.println("Finished training...");

       /* while (true) {
            System.out.println("> ");
            String input = new Scanner(System.in).nextLine();
            if ("quit".equals(input)) {
                System.out.println("Bye!");
                break;
            } else {
                Classification c = classifier.classify(input);
                System.out.println("Best category: " + c.bestCategory());
            }
        }*/
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

    private static String resourceString(URL url) throws IOException {
        System.out.println("URL:" + url.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()) ,1024);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        br.close();
        return stringBuilder.toString();
    }
}
