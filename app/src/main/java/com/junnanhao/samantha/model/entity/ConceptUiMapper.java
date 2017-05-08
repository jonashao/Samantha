package com.junnanhao.samantha.model.entity;

import com.junnanhao.samantha.model.entity.concept.Concept;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/5/6.
 * Map concept to views.
 * for example: logistic company -> strip title and detail title
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(fluent = true)
public class ConceptUiMapper extends RealmObject {
    @PrimaryKey private int id;
    private Concept concept;
    private RealmList<View> views;
}
