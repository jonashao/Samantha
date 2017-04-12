package com.junnanhao.samantha.ui.utils;


public interface Setter<T, V> {
    String TEXT_VIEW = "textView";
    String IMAGE_VIEW = "imageView";
    String INFO_VIEW = "infoView";
    String TYPE_META = "meta";

    String PROPERTY_TITLE = "title";
    String PROPERTY_SUBJECT = "subject";
    String PROPERTY_TEXT = "text";
    String PROPERTY_LOCATION = "location";
    String PROPERTY_TIME = "time";
    String PROPERTY_IMAGE_RESOURCE = "imageResource";
    String PROPERTY_PHONE = "phone";
    String PROPERTY_ANY = "any";

    void set(T object, V value);
}
