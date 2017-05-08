package com.junnanhao.samantha.model.entity;


import com.junnanhao.samantha.model.entity.concept.Concept;
import com.junnanhao.samantha.model.entity.concept.ConceptValue;
import com.junnanhao.samantha.model.entity.infoType.InfoType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/3/31.
 * InfoBean is an analogy for information extracted from text.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(fluent = true)
public class InfoBean extends RealmObject {
    @PrimaryKey long id;
    private InfoType type;
    private Raw raw;
    private RealmList<ActionMenuItem> actions;
    private RealmList<ConceptValue> data;
    private Date createdTime;
    private Date startTime;
    private Date endTime;
    private boolean archived = false;

    public String valueOfConcept(Concept concept) {
        ConceptValue conceptValue = conceptValue(concept);
        return conceptValue != null ? conceptValue.value() : "";
    }

    public ConceptValue conceptValue(Concept concept) {
        for (ConceptValue conceptValue : data) {
            if (conceptValue.concept().equals(concept)) {
                return conceptValue;
            }
        }
        return null;
    }

    public String valueOfUi(String name) {
        for (ConceptUiMapper mapper : type.ui().viewMap()) {
            for (View view : mapper.views()) {
                if (view.name().equals(name)) {
                    return valueOfConcept(mapper.concept());
                }
            }
        }
        return "";
    }

    public List<ConceptValue> valuesOfUi(String name) {
        List<ConceptValue> list = new ArrayList<>();
        for (ConceptUiMapper mapper : type.ui().viewMap()) {
            for (View view : mapper.views()) {
                if (view.name().equals(name)) {
                    list.add(conceptValue(mapper.concept()));
                }
            }
        }
        return list;
    }

}
