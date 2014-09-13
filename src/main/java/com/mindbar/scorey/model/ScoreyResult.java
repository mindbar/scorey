package com.mindbar.scorey.model;

import lombok.Data;

import java.util.Map;

/**
 * @author mishadoff
 */
@Data
public class ScoreyResult {
    private Map<String, String> scores;

    private String bestCategory;
}
