package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * info classification output.
 * Main types are: Travel, Financial, Notification. Tobe added.
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Data
public class InfoType extends RealmObject {
    @PrimaryKey int id;
}
