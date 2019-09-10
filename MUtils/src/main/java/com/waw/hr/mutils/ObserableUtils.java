package com.waw.hr.mutils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * Created by heboot on 2018/2/6.
 */

public class ObserableUtils {

    public static int initValue = 0;

    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {

                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);
    }


    public static Observable<Integer> countdownByMILLISECONDS(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {

                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return aLong.intValue();
                    }
                })
                .take(countTime);

    }


    public static Observable<Long> countdownBySECONDS(long time) {
        if (time < 0) time = 0;

        final long countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {

                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);

    }


}
