package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
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
public class User extends RealmObject {
    @PrimaryKey private long id;
    private String username;
    private String avatar;
}
