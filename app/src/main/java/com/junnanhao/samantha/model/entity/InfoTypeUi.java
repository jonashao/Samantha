package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/10.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class InfoTypeUi extends RealmObject {
    private String resNameEdit;
    private String layoutName;
    private int color;
}
