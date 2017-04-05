package com.junnanhao.samantha.workflow;

import android.content.Context;

import com.junnanhao.samantha.app.RxBus;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.model.entity.SenderBook;
import com.junnanhao.samantha.model.entity.Template;
import com.junnanhao.samantha.util.ClassUtils;
import com.junnanhao.samantha.workflow.extractor.Extractor;
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
            SenderBook senderBook = realm
                    .where(SenderBook.class)
                    .equalTo("sender.type",raw.sender().type())
                    .equalTo("sender.value", raw.sender().value())
                    .findFirst();
            if (senderBook == null) continue;

            for (Template next : senderBook.templates()) {
                Extractor extractor = new Extractor(next);
                InfoBean infoBean = extractor.extract(raw);
                if(infoBean!=null) {
                    beans.add(infoBean);
                    break;
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
