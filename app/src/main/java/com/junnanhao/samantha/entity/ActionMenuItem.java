package com.junnanhao.samantha.entity;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/3/31.
 * describe how to display and react with action menu
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class ActionMenuItem extends RealmObject {
    String title;
    int type;
    String data;
}

