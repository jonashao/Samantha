package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/3/31.
 * Use List of Couple to simulate Map
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class Couple extends RealmObject {
    int key;
    String value;
}
