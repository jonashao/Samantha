package com.junnanhao.samantha.app;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Jonas on 2017/3/31.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
