package com.junnanhao.samantha.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.junnanhao.samantha.BuildConfig;
import com.junnanhao.samantha.model.entity.concept.Concept;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;
import com.junnanhao.samantha.model.entity.infoType.InfoType;
import com.junnanhao.samantha.model.entity.SenderBook;
import com.junnanhao.samantha.model.entity.Synonyms;
import com.junnanhao.samantha.model.entity.template.TemplateItem;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class DebugApp extends Application {

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
                    InputStream is = getAssets().open("db_concept.json");
                    realm.createAllFromJson(Concept.class, is);
                    is.close();

                    is = getAssets().open("db.json");
                    realm.createOrUpdateAllFromJson(SenderBook.class, is);
                    is.close();

                    is = getAssets().open("db_concept_description.json");
                    realm.createOrUpdateAllFromJson(ConceptDesc.class, is);
                    is.close();

                    is = getAssets().open("db_info_type.json");
                    realm.createOrUpdateAllFromJson(InfoType.class, is);
                    is.close();

                    is = getAssets().open("db_synonyms.json");
                    realm.createOrUpdateAllFromJson(Synonyms.class, is);
                    is.close();

                    is = getAssets().open("db_template_items.json");
                    realm.createOrUpdateAllFromJson(TemplateItem.class, is);
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
