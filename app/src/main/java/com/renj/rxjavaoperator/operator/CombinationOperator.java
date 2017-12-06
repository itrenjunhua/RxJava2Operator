package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import rx.functions.Func2;
import rx.joins.Pattern2;
import rx.joins.Plan0;
import rx.observables.JoinObservable;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-06   17:26
 * <p>
 * 描述：组合操作符<br/>
 * 包含 join、and/then/when
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CombinationOperator {

    /**
     * 操作符：join() 操作符<br/>
     * 说明：无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射
     */
    public static void joinOperator() {
        Observable<Long> observable1 = Observable.intervalRange(10, 20, 1000, 1000, TimeUnit.MILLISECONDS);
        Observable<Long> observable2 = Observable.interval(1000, TimeUnit.MILLISECONDS);
        observable1.join(observable2, new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long s) throws Exception {
                return Observable.just(s);
            }
        }, new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long l) throws Exception {
                return Observable.just(l);
            }
        }, new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long s, Long l) throws Exception {
                return s + " - " + l;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e("join operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("join operator onComplete");
            }

            @Override
            public void onNext(String s) {
                Logger.i("join operator onNext => " + s);
            }
        });
    }

    /**
     * 操作符：and/then/when 操作符<br/>
     * 说明：通过模式(And条件)和计划(Then次序)组合两个或多个Observable发射的数据集<br/><br/>
     * <b>注意：</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 需要导入包含 and/then/when 操作符的库 <b>{@code compile 'io.reactivex:rxjava-joins:0.22.0'}</b> ,
     * 同时 {@code Observable} 和 {@code Observer} 也要使用 {@code rx.Observable} 和 {@code rx.Observer} 包下的。
     */
    public static void andThenWhenOperator() {
        rx.Observable<Integer> observable1 = rx.Observable.just(0, 1, 2, 3, 4);
        rx.Observable<String> observable2 = rx.Observable.just("A", "B", "C", "D");

        Pattern2<String, Integer> pattern2 = JoinObservable.from(observable2).and(observable1);
        Plan0<String> plan0 = pattern2.then(new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                return "then => " + s + integer;
            }
        });

        JoinObservable.when(plan0)
                .toObservable()
                .subscribe(new rx.Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.i("and/then/when operator onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("and/then/when operator onError => " + e);
                    }

                    @Override
                    public void onNext(String s) {
                        Logger.i("and/then/when operator onNext => " + s);
                    }
                });
    }
}
