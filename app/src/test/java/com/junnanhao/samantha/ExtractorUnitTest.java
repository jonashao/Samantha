package com.junnanhao.samantha;

import com.google.common.base.Joiner;
import com.junnanhao.samantha.model.entity.Concept;
import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
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
    public void timeExtractor_pattern_correct() throws Exception {
        Pattern pattern = Pattern.compile(
                "((上午?|下午?|早上?|晚上?)((([1-9]{1})|([0-1][0-9])|([1-2][0-3]))(点|:)(([0-5][0-9])分|半)?|现在)((-|至)(([1-9]{1})|([0-1][0-9])|([1-2][0-3]))(点|:)(([0-5][0-9])分|半)?)?)");



        String test = "顺丰从现在至 17:30";
        Matcher matcher = pattern.matcher(test);
        assertTrue(matcher.find());
//        assertEquals(matcher.);
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
