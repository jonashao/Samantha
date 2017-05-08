package com.junnanhao.samantha.info.utils;

import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Jonas on 2017/4/11.
 * Property constants
 */

public class Setters {

    public static final Setter<TextView, String> TEXT_VIEW_TEXT = new Setter<TextView, String>() {
        @Override
        public void set(TextView object, String value) {
            object.setText(value);
        }
    };

    public static final Setter<ImageView, Integer> IMAGE_VIEW_RESOURCE_ID = new Setter<ImageView, Integer>() {
        @Override
        public void set(ImageView object, Integer value) {
            object.setImageResource(value);
        }
    };


}
