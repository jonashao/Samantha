package com.junnanhao.samantha.model.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * pattern for extracting info from sources
 */

@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Data
public class Template extends RealmObject {
    private InfoType type;
    private String pattern;
    private RealmList<ConceptFormat> conceptFormats;

}
