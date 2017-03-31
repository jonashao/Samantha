package com.junnanhao.samantha.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.junnanhao.samantha.BuildConfig;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

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

//        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                try {
//                    InputStream is = getAssets().open("db_RawClassifier.json");
//                    realm.createAllFromJson(RawClassifier.class, is);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

    }

}
