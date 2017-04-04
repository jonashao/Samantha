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
    private static final String smsTxt2 = "订单E1569853659,郝俊楠您已购3月15日G92次12车2A号广州南12:20开。";

    private static final String pattern =
            "订单(E\\d{9,10}),(.+)您已购(\\d+)月(\\d+)日([A-Z]\\d{1,5})次(\\d{1,2})车(\\w+)号([^\\d]+)(\\d{1,2}):(\\d{1,2})(开)。.*";

    @Test
    public void pattern_isCorrect()  throws Exception{
        System.out.println(smsTxt);

//        Assert.assertEquals( s,"E156985659");
        Pattern mPattern = Pattern.compile(pattern);
        Matcher matcher =  mPattern.matcher(smsTxt);

        assertTrue(matcher.find());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.replaceFirst("$10:$11"));

        assertEquals(matcher.group(1),"E156985659");
        assertEquals(matcher.group(2),"郝俊楠");
        String time =  matcher.replaceFirst("$9:$10");
        System.out.println();
        assertEquals(time,"12:20");
        String seat =  matcher.replaceFirst("$6车$7号");
        assertEquals(seat,"12车2A号");

        assertFalse(mPattern.matcher(smsTxt2).find());

    }
}
