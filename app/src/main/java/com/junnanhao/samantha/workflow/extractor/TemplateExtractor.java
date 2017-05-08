package com.junnanhao.samantha.workflow.extractor;

import com.junnanhao.samantha.model.entity.template.ConceptFormat;
import com.junnanhao.samantha.model.entity.concept.ConceptValue;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.infoType.InfoType;
import com.junnanhao.samantha.model.entity.template.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;


/**
 * Created by Jonas on 2017/1/25.
 * Extract actionable information items from src
 */
public class TemplateExtractor implements Extractor {
    private Template template;
    private Pattern mPattern;
    private Pattern formatPattern = Pattern.compile("\\$\\{(\\d+)\\}");

    public TemplateExtractor(Template pattern) {
        template = pattern;
        mPattern = Pattern.compile(template.pattern());
    }

    @Override
    public InfoBean extract(final String src) {
        final Matcher matcher = mPattern.matcher(src);
        InfoBean bean = null;

        if (matcher.find()) {
            final InfoType category = template.type();
            final List<ConceptValue> itemList = extract(matcher);

            bean = new InfoBean()
                    .type(category)
                    .id(UUID.randomUUID().hashCode())
                    .data(new RealmList<ConceptValue>());
            bean.data().addAll(itemList);
        }
        return bean;
    }

    private List<ConceptValue> extract(Matcher matcher) {
        List<ConceptValue> list = new ArrayList<>();

        for (ConceptFormat item : template.conceptFormats()) {
            String formatter = item.formatter();

            Matcher formatMather = formatPattern.matcher(formatter);
            while (formatMather.find()) {
                String groupValue = formatMather.group(1);
                if (groupValue != null) {
                    formatter = formatMather.replaceFirst(matcher.group(Integer.valueOf(groupValue)));
                    formatMather = formatPattern.matcher(formatter);
                }
            }

            String v = matcher.replaceFirst(formatter);
            v = v.replaceAll("null", "");
            list.add(new ConceptValue(item.concept(), v));
        }
        return list;
    }


}
