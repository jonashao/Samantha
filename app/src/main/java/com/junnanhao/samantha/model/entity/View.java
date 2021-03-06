package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
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
public class View extends RealmObject {
    @PrimaryKey String name;
}

