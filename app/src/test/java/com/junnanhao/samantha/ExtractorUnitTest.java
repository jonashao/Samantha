package com.junnanhao.samantha;

import com.google.common.base.Joiner;
import com.junnanhao.samantha.model.entity.Concept;
import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.Synonyms;
import com.junnanhao.samantha.workflow.extractor.ConceptsExtractor;
import com.junnanhao.samantha.workflow.extractor.Extractor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
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

    private static final String test2 = "【邮政EMS】你好，请带证件于下午17点到化工天桥底马路边取，可叫同学代领，联系18988900358，谢谢！";


    @Test
    public void pickNumber_pattern_correct() throws Exception {
        String p = "(?<=号)([\\(（【\\[ ]?)(\\d{1,4})([）\\)】\\] ]?)";
        Pattern pattern = Pattern.compile(p);
        String test = "编号555";
        Matcher matcher = pattern.matcher(test);
        assertTrue(matcher.find());
        assertEquals(matcher.group(2), "555");

        matcher = pattern.matcher("取货号(195)");
        assertTrue(matcher.find());
        assertEquals(matcher.group(2), "195");

        matcher = pattern.matcher("快递编号(5012)");
        assertTrue(matcher.find());
        assertEquals(matcher.group(2), "5012");

        matcher = pattern.matcher("货号 292");
        assertTrue(matcher.find());
        assertEquals(matcher.group(2), "292");

        matcher = pattern.matcher("货号[8596]");
        assertTrue(matcher.find());
        assertEquals(matcher.group(2), "8596");

    }

    @Test
    public void parameter_replace_correct() throws Exception {
        String p = "\\(([^\\(\\)]*)\\)|（([^（）]*)）";
        Pattern pattern = Pattern.compile(p);
        String test = "hi（me）!";
        Matcher matcher = pattern.matcher(test);
        assertTrue(matcher.find());

        System.out.println(matcher.group());
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));

        ConceptsExtractor extractor = new ConceptsExtractor(null);
        String re = extractor.preHandling("请1点前到（大学城华工一饭天桥底）领取");
        assertEquals(re, "请1点前到领取大学城华工一饭天桥底");
    }


    @Test
    public void timeExtractor_pattern_correct() throws Exception {

        String time = "(([1-9]{1})|([0-1][0-9])|([1-2][0-3]))((点(([0-5][0-9])分|半)?)|:([0-5][0-9]))";

        Pattern timePattern = Pattern.compile(time);
        assertTrue(timePattern.matcher("19点半").find());
        assertTrue(timePattern.matcher("19点25").find());
        assertTrue(timePattern.matcher("19点19分").find());
        assertTrue(timePattern.matcher("19:50").find());

        String duration = String.format("(((%s)前?|现在)((-|至|到)%s)?)", time, time);
        Pattern durationPattern = Pattern.compile(duration);
        assertTrue(durationPattern.matcher("19点半").find());
        assertTrue(durationPattern.matcher("19点25").find());
        assertTrue(durationPattern.matcher("19点19分").find());
        assertTrue(durationPattern.matcher("19:50").find());
        assertTrue(durationPattern.matcher("19:00-19:30").find());
        assertTrue(durationPattern.matcher("19:00到19:30").find());
        assertTrue(durationPattern.matcher("19:00至19:30").find());
        assertTrue(durationPattern.matcher("19点至19点半").find());
        assertTrue(durationPattern.matcher("现在至19点").find());
        assertTrue(durationPattern.matcher("19:00前").find());

        Matcher m = durationPattern.matcher("19点半");
        assertTrue(m.find());
        assertEquals(m.group(0), "19点半");
        Matcher matcher = durationPattern.matcher("北京时间19点半前来拿快递");
        assertTrue(matcher.find());
        assertEquals(matcher.group(), "19点半前");

        assertFalse(durationPattern.matcher("248").find());

        String spaceTest = "—顺丰快递——从现在至 17:30——凭";
        spaceTest = spaceTest.replaceAll("\\s", "");
        assertEquals(spaceTest, "—顺丰快递——从现在至17:30——凭");
        Matcher spaceMather = durationPattern.matcher(spaceTest);
        assertTrue(spaceMather.find());
        assertEquals(spaceMather.group(), "现在至17:30");
        assertEquals(spaceMather.group(2), "现在");

        String durationTest = "我是宅急送快递员，请于12点50分至1点30分内过来华工一饭天桥大路边取";
        Matcher durationMatcher = durationPattern.matcher(durationTest);
        assertTrue(durationMatcher.find());
        assertEquals(durationMatcher.group(), "12点50分至1点30分");
    }


    @Test
    public void filterExtractor_match_correct() throws Exception {
        String test = "饭天桥（自、代取须出示学生卡）拿取";
        String replaced = test.replaceAll("\\([^\\(\\)]*\\)|（[^（）]*）", "");
        assertEquals(replaced, "饭天桥拿取");
    }

    @Test
    public void conceptExtractor_match_correct() throws Exception {
        List<ConceptDesc> descriptions = new ArrayList<>();
        descriptions.add(new ConceptDesc()
                .formatter("(((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-1])|(18[0,5-9]))\\d{8})")
                .concept(new Concept().id(1))
                .cather("$1")
        );

        String[] placeAdverbialModifiers = new String[]{"来", "到"};
        String[] placeVerb = new String[]{"取", "拿", "领"};

        String placeFormatter = Joiner.on("|").join(placeAdverbialModifiers) +
                "([^" + Joiner.on("").join(placeVerb) + "]*)" + Joiner.on("|").join(placeVerb);
        descriptions.add(new ConceptDesc()
                .concept(new Concept().id(1))
                .formatter(placeFormatter)
                .cather("$1")
        )
        ;

        descriptions.add(new ConceptDesc()
                .concept(new Concept().id(2))
                .formatter("((上午?|下午?|早上?|晚上?)(([1-9]{1})|([0-1][0-9])|([1-2][0-3]))(点|:)(([0-5][0-9])分)?((-|至)(([1-9]{1})|([0-1][0-9])|([1-2][0-3]))(点|:)(([0-5][0-9])分)?)?)")
                .cather("$1")
        );

        Extractor extractor = new ConceptsExtractor(descriptions);
        InfoBean bean = extractor.extract(test2);

        assertEquals(bean.data().size(), 3);
        assertEquals(bean.data().get(0).value(), "18988900358");
    }


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
