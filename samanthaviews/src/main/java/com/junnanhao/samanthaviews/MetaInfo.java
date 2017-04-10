package com.junnanhao.samanthaviews;

/**
 * Created by Jonas on 2017/4/6.
 * Encapsulated information type and value to represent in cell folder.
 */

public class MetaInfo {
    public static final String TYPE_PHONE = "phone";

    private String type;
    private String value;

    public MetaInfo(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String type() {
        return type;
    }

    public void type(String type) {
        this.type = type;
    }

    public String value() {
        return value;
    }

    public void value(String value) {
        this.value = value;
    }
}
