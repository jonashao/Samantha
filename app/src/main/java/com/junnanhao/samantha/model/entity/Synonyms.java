package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/10.
 * Mapping synonyms to single identifier
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class Synonyms extends RealmObject {
    @PrimaryKey String identifier;
    String best;
    String candidates;
}
