package com.junnanhao.samantha.model.entity;


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
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class InfoBean extends RealmObject {
    @PrimaryKey long id;
    private InfoType type;
    private Raw raw;
    private RealmList<ActionMenuItem> actions;
    private RealmList<ConceptValue> data;
}
