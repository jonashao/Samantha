package com.junnanhao.samantha.app;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Jonas on 2017/4/2.
 * EventBus implemented by RxJava2
 */

public class RxBus {
    private FlowableProcessor<Object> bus = PublishProcessor.create().toSerialized();

    public void post(Object o) {
        bus.onNext(o);
    }

    private RxBus() {
    }

    private static volatile RxBus instance = null;

    public static RxBus get() {

        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }

        return instance;
    }

    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return bus.ofType(aClass);
    }

    public boolean hasSubscribers() {
        return bus.hasSubscribers();
    }

}
