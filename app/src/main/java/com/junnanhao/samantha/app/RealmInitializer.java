package com.junnanhao.samantha.app;

import android.app.Application;
import android.content.Context;

import com.junnanhao.samantha.model.entity.ConceptUiMapper;
import com.junnanhao.samantha.model.entity.SenderBook;
import com.junnanhao.samantha.model.entity.Synonyms;
import com.junnanhao.samantha.model.entity.concept.Concept;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;
import com.junnanhao.samantha.model.entity.infoType.InfoType;
import com.junnanhao.samantha.model.entity.template.TemplateItem;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

class RealmInitializer {
    static void init(final Context context) {
        Realm.init(context);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfig);
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    InputStream is = context.getAssets().open("db_concept.json");
                    realm.createOrUpdateAllFromJson(Concept.class, is);
                    is.close();

                    is = context.getAssets().open("db.json");
                    realm.createOrUpdateAllFromJson(SenderBook.class, is);
                    is.close();

                    is = context.getAssets().open("db_concept_description.json");
                    realm.createOrUpdateAllFromJson(ConceptDesc.class, is);
                    is.close();

                    is = context.getAssets().open("db_info_type.json");
                    realm.createOrUpdateAllFromJson(InfoType.class, is);
                    is.close();

                    is = context.getAssets().open("db_synonyms.json");
                    realm.createOrUpdateAllFromJson(Synonyms.class, is);
                    is.close();

                    is = context.getAssets().open("db_template_items.json");
                    realm.createOrUpdateAllFromJson(TemplateItem.class, is);
                    is.close();

                    is = context.getAssets().open("db_concept_ui_mapper.json");
                    realm.createOrUpdateAllFromJson(ConceptUiMapper.class, is);
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
