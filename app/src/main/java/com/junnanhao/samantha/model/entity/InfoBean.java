package com.junnanhao.samantha.model.entity;


import com.junnanhao.samantha.model.entity.concept.Concept;
import com.junnanhao.samantha.model.entity.concept.ConceptValue;
import com.junnanhao.samantha.model.entity.infoType.InfoType;

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
    private RealmList<ConceptUiMapper> ui;
    private RealmList<ActionMenuItem> actions;
    private RealmList<ConceptValue> data;

    public String valueOfConcept(Concept concept) {
        for (ConceptValue conceptValue : data) {
            if (conceptValue.concept().equals(concept)) {
                return conceptValue.value();
            }
        }
        return null;
    }

    public String valueOfUi(String name) {
        for (ConceptUiMapper mapper : ui) {
            for (View view : mapper.views()) {
                if (view.name().equals(name)) {
                    return valueOfConcept(mapper.concept());
                }
            }
        }
        return "";
    }

}
