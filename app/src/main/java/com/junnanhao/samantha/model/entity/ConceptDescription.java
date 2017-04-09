package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/9.
 * Describe concept in
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class ConceptDescription extends RealmObject {
    @PrimaryKey private int id;
    private Concept concept;
    private String formatter;
    private String cather = "$1";
    private String resType = "id";
    private String resName;
}
