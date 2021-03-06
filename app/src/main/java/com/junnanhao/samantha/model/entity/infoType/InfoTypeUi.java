package com.junnanhao.samantha.model.entity.infoType;

import com.junnanhao.samantha.model.entity.ConceptUiMapper;

import io.realm.RealmList;
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
    private RealmList<ConceptUiMapper> viewMap;
}
