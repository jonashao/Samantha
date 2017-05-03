package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * Map concept to their formatter in a template pattern
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class ConceptFormat extends RealmObject {
    private Concept concept;
    /**
     * recombine matched groups, like: ${1}年${1}月${3}日 as the "date" concept formatter
     */
    private String formatter;
    private Boolean required;
}