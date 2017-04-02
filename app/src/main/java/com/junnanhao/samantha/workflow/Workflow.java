package com.junnanhao.samantha.workflow;

import android.content.Context;

import com.junnanhao.samantha.app.RxBus;
import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.util.ClassUtils;
import com.junnanhao.samantha.workflow.scanner.Scanner;
import com.junnanhao.samantha.workflow.scanner.SmsScanner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class Workflow {
    private Context mContext;
    private Subject<Object> bus;

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
                    public void accept(LinkedList<Raw> raws) throws Exception {
                        extract(raws);
                    }
                }));
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
        Timber.d("scan source");
        LinkedList<Raw> data = new LinkedList<>();
        for (Scanner scanner : scanners) {
            List<Raw> scanned = scanner.scan();
            if (scanned != null) {
                data.addAll(scanner.scan());
            }
        }
        Timber.d("scanned: %s", data.toString());
        return data;
    }


    private void extract(LinkedList<Raw> rawList) {
        System.out.println(rawList.toString());
//        Realm realm = Realm.getDefaultInstance();
//        for (Raw raw : rawList) {
//            RawClassifier classifier = realm
//                    .where(RawClassifier.class)
//                    .equalTo("sender", raw.sender())
//                    .findFirst();
//            if (classifier == null) continue;
//
//            for (ExtractPattern next : classifier.templates()) {
//                Extractor extractor = new Extractor(next);
//                extractor.extract(raw);
//            }
//        }
    }

    public void scan() {
        RxBus.get().post(scanSource());
    }
}
