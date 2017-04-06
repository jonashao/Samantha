package com.junnanhao.samantha.app;

import android.app.Application;

import com.junnanhao.samantha.model.entity.Concept;
import com.junnanhao.samantha.model.entity.SenderBook;

import java.io.IOException;
import java.io.InputStream;

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
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    InputStream is = getAssets().open("db_concept.json");
                    realm.createAllFromJson(Concept.class, is);
                    is.close();

                    is = getAssets().open("db.json");
                    realm.createOrUpdateAllFromJson(SenderBook.class, is);
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
