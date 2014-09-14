package com.mindbar.scorey.model;

import com.mindbar.scorey.metrics.Metric;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by aleksandr on 13.09.14.
 */
@Data
public class ArticleMeta {
    private String url;
    private String title;

    Map<Metric, Double> articleScores;
}
