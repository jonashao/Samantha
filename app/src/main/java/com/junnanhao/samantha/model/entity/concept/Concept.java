package com.junnanhao.samantha.model.entity.concept;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * Define concept and their id
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class Concept extends RealmObject {
    @PrimaryKey private long id;
    private String meaning;
    private Concept parent;
}
