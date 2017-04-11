package com.junnanhao.samantha.ui.utils;

import android.widget.TextView;

import java.util.List;

import lombok.AllArgsConstructor;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.junnanhao.samantha.util.StringUtils.ellipsis;

/**
 * Created by Jonas on 2017/4/11.
 * setter for the pattern of two text:
 * <br/>
 * show main and second text when both set.
 * <br/>
 * show only main text when one set.
 */

public class SubViewSetter {
    private short status = 0b00;
    private String[] data = new String[3];
    private List<TextView> views;

    public SubViewSetter(List<TextView> views) {
        this.views = views;
    }

    public void set(Value value) {
        short which = 0b10;
        if (value.isMain) {
            which = 0b01;
        }
        status |= which;

        data[which] = value.value;
        String v = ellipsis(data[which], 14);
        if (status == 3) {
            views.get(which).setText(v);
            views.get(which).setVisibility(VISIBLE);
            views.get(0).setVisibility(INVISIBLE);
        } else {
            views.get(0).setText(v);
            views.get(0).setVisibility(VISIBLE);
            views.get(1).setVisibility(INVISIBLE);
            views.get(2).setVisibility(INVISIBLE);
        }
    }


    @AllArgsConstructor(suppressConstructorProperties = true)
    public static class Value {
        boolean isMain;
        String value;
    }

    public static final Setter<SubViewSetter, Value> SETTER = new Setter<SubViewSetter, Value>() {
        @Override
        public void set(SubViewSetter object, Value value) {
            object.set(value);
        }
    };
}
