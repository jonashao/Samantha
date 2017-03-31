package com.junnanhao.samantha.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jonas on 2017/1/25.
 * provide context
 */

@Module
class AppModule {

    private final Context mContext;

    AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
