package com.junnanhao.samantha.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.junnanhao.samantha.BuildConfig;
import com.junnanhao.samantha.model.entity.Sender;
import com.junnanhao.samantha.model.entity.SenderBook;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        Timber.plant(new Timber.DebugTree());

        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfig);

        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    InputStream is = getAssets().open("db.json");
                    realm.createAllFromJson(SenderBook.class, is);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
