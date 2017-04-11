package com.junnanhao.samantha.util;

/**
 * Created by Jonas on 2017/4/11.
 * String helper functions
 */

public class StringUtils {
    public static String ellipsis(String string, int maxLen) {
        if (string == null) {
            return null;
        }
        if (string.length() < maxLen || maxLen < 3) {
            return string;
        } else {
            return string.substring(0, maxLen - 3) + "...";
        }
    }

}
