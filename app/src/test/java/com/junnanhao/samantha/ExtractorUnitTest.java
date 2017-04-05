package com.junnanhao.samantha;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jonas on 2017/4/3.
 * test regular expression extraction
 */

public class ExtractorUnitTest {
    private static final String smsTxt = "订单E156985659,郝俊楠您已购3月15日G92次12车2A号广州南12:20开。";
    private static final String smsTxt2 = "订单E15698536595,郝俊楠您已购3月15日G92次12车2A号广州南12:20开。";

    private static final String pattern =
            "订单(E\\d{9,10}),(.+)您已购(\\d+)月(\\d+)日([A-Z]\\d{1,5})次(\\d{1,2})车(\\w+)号([^\\d]+)(\\d{1,2}):(\\d{1,2})(开)。.*";


    private static final String pattern2 = "(^【[^【】]+】|^\\[[^\\[\\]]+\\]|【[^【】]+】$|\\[[^\\[\\]]+\\]$)";
    private static final String test = "[京东]得到的【hi】";

    @Test
    public void patternSubject_isCorrect() throws Exception {
        Pattern pattern = Pattern.compile(pattern2);
        Matcher matcher = pattern.matcher(test);

        System.out.println(matcher.replaceAll(""));

        System.out.println(matcher.groupCount());
        if (matcher.find()) {
            int count = matcher.groupCount();
            for (int i = 0; i < count; i++) {
                String group_i = matcher.group(i);
                if (group_i != null) {
                    System.out.println(group_i);
                }
            }
        }
    }

    @Test
    public void pattern_isCorrect() throws Exception {
        System.out.println(smsTxt);

        Pattern mPattern = Pattern.compile(pattern);
        Matcher matcher = mPattern.matcher(smsTxt);

        assertTrue(matcher.find());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.replaceFirst("$10:$11"));

        assertEquals(matcher.group(1), "E156985659");
        assertEquals(matcher.group(2), "郝俊楠");
        String time = matcher.replaceFirst("$9:$10");
        System.out.println();
        assertEquals(time, "12:20");
        String seat = matcher.replaceFirst("$6车$7号");
        assertEquals(seat, "12车2A号");

        System.out.println(matcher.replaceFirst("d$99"));
        assertFalse(mPattern.matcher(smsTxt2).find());
    }
}
