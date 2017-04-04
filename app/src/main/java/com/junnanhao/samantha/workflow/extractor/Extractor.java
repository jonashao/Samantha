package com.junnanhao.samantha.workflow.extractor;

import com.junnanhao.samantha.model.entity.ConceptFormat;
import com.junnanhao.samantha.model.entity.ConceptValue;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.InfoType;
import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.model.entity.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;
import timber.log.Timber;


/**
 * Created by Jonas on 2017/1/25.
 * Extract actionable information items from src
 * Automatic update matching patterns
 */
public class Extractor {
    private Template template;
    private Pattern mPattern;

    public Extractor(Template pattern) {
        // todo:check if need update pattern
        template = pattern;
        mPattern = Pattern.compile(template.pattern());
    }

    public InfoBean extract(final Raw raw) {
        final Matcher matcher = mPattern.matcher(raw.body());
        InfoBean bean = null;

        if (matcher.find()) {
            final InfoType category = template.type();
            final List<ConceptValue> itemList = extract(matcher);

            bean = new InfoBean()
                    .type(category)
                    .id(UUID.randomUUID().hashCode())
//                    .raw(raw)
                    .data(new RealmList<ConceptValue>());
            bean.data().addAll(itemList);
        }
        return bean;
    }

    private List<ConceptValue> extract(Matcher matcher) {
        List<ConceptValue> list = new ArrayList<>();
        Timber.d(matcher.replaceFirst("$10"));
        Timber.d(matcher.group(10));
        for (ConceptFormat item : template.conceptFormats()) {
            String v = matcher.replaceFirst(item.formatter());
            if (v != null) {
                list.add(new ConceptValue(item.concept(), v));
            }
        }
        return list;
    }

}
