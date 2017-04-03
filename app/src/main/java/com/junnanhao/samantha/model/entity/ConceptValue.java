package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/3/31.
 * Use List of ConceptValue to simulate Map
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class ConceptValue extends RealmObject {
    Concept concept;
    String value;
}
