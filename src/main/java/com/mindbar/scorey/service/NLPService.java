package com.mindbar.scorey.service;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mishadoff
 */
@Service
public class NLPService {

    private SentenceDetectorME sentenceDetector;
    private Tokenizer tokenizer;

    // init
    {
        System.out.println("NLP SERVICE INIT");
        InputStream modelIn = null;
        InputStream tokenModelIn = null;
        try {
            modelIn = new ClassPathResource("model/en-sent.bin").getInputStream();
            tokenModelIn = new ClassPathResource("model/en-token.bin").getInputStream();
            SentenceModel model = new SentenceModel(modelIn);
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            sentenceDetector = new SentenceDetectorME(model);
            tokenizer = new TokenizerME(tokenModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    public String[] getSentences(String text) {
        return sentenceDetector.sentDetect(text);
    }

    public String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    public List<String> getSnippetTokens(String metric, String article) {
        int SIZE = 10;

        String[] sentences = getSentences(article);
        List<String> snippetWords = new ArrayList<String>();
        for (String s : sentences) {
            String[] tokens = tokenize(s);
            for (int j = 0; j < tokens.length; j++) {
                String curVal = tokens[j];
                if (curVal.equalsIgnoreCase(metric)) {
                    for (int k = 0; k < tokens.length; k++) {
                        snippetWords.add(tokens[k]);
                    }
                    break;
                }
            }
        }

        return snippetWords;
    }
}
