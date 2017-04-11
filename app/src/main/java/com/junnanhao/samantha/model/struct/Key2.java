package com.junnanhao.samantha.model.struct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Jonas on 2017/4/11.
 * group two keys together to generate a combined key.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode
public class Key2 {
    public String viewType;
    private String property;
}
