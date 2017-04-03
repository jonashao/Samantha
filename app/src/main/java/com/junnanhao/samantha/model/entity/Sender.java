package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/3.
 * encapsulate sender type and address/number
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
    private String value;
}
