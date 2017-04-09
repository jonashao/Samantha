package com.junnanhao.samantha.model.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * info classification output.
 * Main types are: Travel, Financial, Notification. To be added.
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Data
public class InfoType extends RealmObject {
    @PrimaryKey private int id;
    private String resNameEdit;
    private RealmList<ConceptDescription> conceptDescriptions;
    private RealmList<Sender> senders;
    private String subjects;
}
