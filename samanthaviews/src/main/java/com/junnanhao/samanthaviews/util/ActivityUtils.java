package com.junnanhao.samanthaviews.util;

import java.io.File;

/**
 * Created by Jonas on 2017/4/10.
 */

public class ActivityUtils {
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
