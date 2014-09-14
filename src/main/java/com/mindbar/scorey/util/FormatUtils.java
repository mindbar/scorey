package com.mindbar.scorey.util;

/**
 * @author mishadoff
 */
public final class FormatUtils {
    private FormatUtils() {}

    public static String formatDouble(double d) {
        return String.format("%.2f", d);
    }
}
