package com.junnanhao.samantha.model.entity;

import com.junnanhao.samantha.model.entity.template.Template;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.<br/>
 * Encapsulate sender type and address/number
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class Sender extends RealmObject {
    /**
     * Type of source, like email and sms
     */
    private String type;
    @PrimaryKey private String value;
    private RealmList<Template> templates;
}
