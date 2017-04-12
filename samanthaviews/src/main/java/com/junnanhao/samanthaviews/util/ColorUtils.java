package com.junnanhao.samanthaviews.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by Jonas on 2017/4/10.
 * Utils for analyzing color
 */

public class ColorUtils {

    public static boolean isColorDark(@ColorInt int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5;
    }
}
