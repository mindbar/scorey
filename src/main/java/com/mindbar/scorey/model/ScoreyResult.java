package com.mindbar.scorey.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mishadoff
 */
@Data
public class ScoreyResult {
    private Map<String, String> scores;

    private List<ArticleMeta> articles;
    private String bestCategory;
}
