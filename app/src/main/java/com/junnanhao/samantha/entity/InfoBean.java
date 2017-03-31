package com.junnanhao.samantha.entity;

import java.util.List;


import io.realm.RealmList;
import io.realm.RealmObject;
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
    long id;
    private int type;
    private RealmList<MenuItem> actions;
    private RealmList<Couple> data;
}
