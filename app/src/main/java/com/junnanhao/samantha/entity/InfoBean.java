package com.junnanhao.samantha.entity;


import android.util.SparseArray;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/3/31.
 * InfoBean is an analogy for information extracted from text.
 */
@Data
@Accessors(fluent = true)
public class InfoBean {
    int type;
    List<MenuItem> menuItems;
    SparseArray<String> data;
}
