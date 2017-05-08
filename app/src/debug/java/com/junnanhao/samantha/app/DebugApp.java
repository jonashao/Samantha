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
        Timber.plant(new Timber.DebugTree());
        RealmInitializer.init(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }
}
