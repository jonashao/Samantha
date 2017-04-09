package com.junnanhao.samantha.workflow;

import android.content.Context;

import com.junnanhao.samantha.app.RxBus;
import com.junnanhao.samantha.model.entity.Concept;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.InfoType;
import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.model.entity.SenderBook;
import com.junnanhao.samantha.model.entity.Template;
import com.junnanhao.samantha.util.ClassUtils;
import com.junnanhao.samantha.workflow.extractor.ConceptsExtractor;
import com.junnanhao.samantha.workflow.extractor.Extractor;
import com.junnanhao.samantha.workflow.extractor.TemplateExtractor;
import com.junnanhao.samantha.workflow.scanner.Scanner;
import com.junnanhao.samantha.workflow.scanner.SmsScanner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class Workflow {
    private Context mContext;

    private List<Scanner> scanners = new ArrayList<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public Workflow(Context context) {
        mContext = context;
    }

    public void start() {
        setupScanners();

        disposable.add(RxBus.get()
                .toFlowable(ClassUtils.<LinkedList<Raw>>castClass(LinkedList.class))
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<LinkedList<Raw>>() {
                    @Override
                    public void accept(LinkedList<Raw> rawList) throws Exception {
                        extract(rawList);
                    }
                }));
    }

    public void stop() {
        disposable.clear();
    }

    private void setupScanners() {
        scanners.add(new SmsScanner(mContext));
    }


    /**
     * Make sure having permission to scan source.
     *
     * @return all information scanned from sources
     */
    private LinkedList<Raw> scanSource() {
        LinkedList<Raw> data = new LinkedList<>();
        for (Scanner scanner : scanners) {
            List<Raw> scanned = scanner.scan();
            if (scanned != null) {
                data.addAll(scanner.scan());
            }
        }
        return data;
    }


    private void extract(LinkedList<Raw> rawList) {
        Realm realm = Realm.getDefaultInstance();

        final List<InfoBean> beans = new ArrayList<>(rawList.size());
        for (Raw raw : rawList) {
            // 首先用发件人迅速锁定信息类别
            SenderBook senderBook = realm
                    .where(SenderBook.class)
                    .equalTo("sender.type", raw.sender().type())
                    .equalTo("sender.value", raw.sender().value())
                    .findFirst();
            if (senderBook != null) {
                // 如果可以锁定类别，在该类别的少数个模板中逐个匹配，匹配成功即提取信息
                for (Template next : senderBook.templates()) {
                    Extractor extractor = new TemplateExtractor(next);
                    InfoBean infoBean = extractor.extract(raw.body());
                    if (infoBean != null) {
                        beans.add(infoBean.raw(raw));
                        break;
                    }
                }
            } else {
                // 如果不能锁定类别，查找包含该主题的所有分类（少数）
                String subject = raw.subject();
                if (subject != null) {
                    RealmResults<InfoType> infoTypes = realm.where(InfoType.class)
                            .contains("subjects", subject)
                            .findAll();
                    for (InfoType infoType : infoTypes) {
                        // 在每个包含该主题的分类中，逐个匹配类别的必备元信息，匹配足够信息即提取
                        Extractor extractor = new ConceptsExtractor(infoType.conceptDescriptions());
                        InfoBean infoBean = extractor.extract(raw.body());
                        if (infoBean != null) {
                            beans.add(infoBean.raw(raw).type(infoType));
                            break;
                        }
                    }
                }
            }


        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(beans);
            }
        });
    }

    public void scan() {
        RxBus.get().post(scanSource());
    }
}
