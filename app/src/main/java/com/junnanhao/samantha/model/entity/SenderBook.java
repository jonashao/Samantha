package com.junnanhao.samantha.model.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * a address list, matching sender with specific enterprise or in other word,
 * templates that are likely to use from the enterprise
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class SenderBook extends RealmObject {
    @PrimaryKey private int id;
    private Sender sender;
    private RealmList<Template> templates;
}
