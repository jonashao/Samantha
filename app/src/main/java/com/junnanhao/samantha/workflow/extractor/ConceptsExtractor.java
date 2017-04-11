package com.junnanhao.samantha.workflow.extractor;

import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.ConceptValue;
import com.junnanhao.samantha.model.entity.InfoBean;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;

/**
 * Created by Jonas on 2017/4/9.
 * Extract information bean from src by concept descriptions
 */

public class ConceptsExtractor implements Extractor {
    private List<ConceptDesc> descriptions;
    private Pattern formatPattern = Pattern.compile("\\$\\{?(\\d+)\\}?");


    public ConceptsExtractor(List<ConceptDesc> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public InfoBean extract(String src) {
        InfoBean bean = new InfoBean()
                .data(new RealmList<ConceptValue>())
                .id(UUID.randomUUID().hashCode());

        src = src.replaceAll("\\([^\\(\\)]*\\)|（[^（）]*）", "");
        src = src.replaceAll("\\s", "");

        for (ConceptDesc description : descriptions) {
            Pattern pattern = Pattern.compile(description.formatter());
            Matcher matcher = pattern.matcher(src);

            String formatter = description.cather();

            if (matcher.find() && formatter != null) {

                Matcher formatMather = formatPattern.matcher(formatter);
                while (formatMather.find()) {
                    String groupValue = formatMather.group(1);
                    if (groupValue != null) {
                        formatter = formatMather.replaceFirst(matcher.group(Integer.valueOf(groupValue)));
                        formatMather = formatPattern.matcher(formatter);
                    }
                }

                bean.data().add(new ConceptValue(description.concept(), formatter));
            }
        }
        return bean;
    }


}
