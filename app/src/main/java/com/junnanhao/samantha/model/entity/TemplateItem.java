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
public class TemplateItem extends RealmObject {
    @PrimaryKey private int id;
    private Template template;
    private User publisher;
    private String title;
    private String version;
    private String description;
    private String updateDate;
    private int subscriberNum;
    private int starNum;
}
