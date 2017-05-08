package com.junnanhao.samantha.info.utils;

import android.widget.TextView;

import java.util.List;

import lombok.AllArgsConstructor;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.junnanhao.samantha.app.util.StringUtils.ellipsis;

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
        String v;
        if (value.isMain) {
            which = 0b01;
            v = ellipsis(value.value, 14);
        } else {
            v = ellipsis(value.value, 20);
        }
        data[which] = value.value;
        status |= which;

        if (status == 3) {
            views.get(1).setText(data[1]);
            views.get(2).setText(data[2]);
            views.get(1).setVisibility(VISIBLE);
            views.get(2).setVisibility(VISIBLE);
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

}
