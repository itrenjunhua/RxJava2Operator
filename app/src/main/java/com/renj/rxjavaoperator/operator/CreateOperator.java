package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-06   10:52
 * <p>
 * 描述：RxJava2操作符之创建操作符<br/>
 * 主要包含 create、just、fromXxx、empty、never、error、range、defer、repat、timer、interval、intervalRange 操作符及他们的重载方法
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CreateOperator {
    /**
     * 操作符：create()操作符<br/>
     * 说明：通过调用观察者的方法从头创建一个Observable
     */
    public static void createOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("create operator result => " + integer);
            }
        });
    }

    /**
     * 操作符：just()操作符<br/>
     * 说明：将对象或者对象集合转换为一个会发射这些对象的Observable
     */
    public static <T> void justOperator(T params) {
        Observable.just(params)
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        Logger.i("just operator1 result => " + t);
                    }
                });
    }

    /**
     * 操作符：just()操作符<br/>
     * 说明：将对象或者对象集合转换为一个会发射这些对象的Observable
     */
    public static <T> void justOperator(T... params) {
        Observable.just(params)
                .subscribe(new Consumer<T[]>() {
                    @Override
                    public void accept(T[] ts) throws Exception {
                        for (T t : ts) {
                            Logger.i("just operator2 result => " + t);
                        }
                    }
                });
    }

    /**
     * 操作符：empty()/never()/error() 特殊操作符<br/>
     * 说明：创建行为受限的特殊Observable
     */
    public static void specialOperator() {
        // empty()  发送一个空事件，只会回调 onComplete() 方法
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Logger.i("empty operator onNext => " + o);
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("empty operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("empty operator onComplete");
            }
        });

        // never() 不会回调任何方法
        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Logger.i("never operator onNext => " + o);
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("never operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("never operator onComplete");
            }
        });

        // error() 发送一个错误事件，回调 onError() 方法
        Observable.error(new Exception("error operator send message"))
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Logger.i("error operator onNext => " + o);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e("error operator onError => " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("error operator onComplete");
                    }
                });
    }

    /**
     * 操作符：fromArray()/fromIterable()/fromFuture() 操作符<br/>
     * 说明：将其它的对象、数字或列表数据结构转换为Observable
     */
    public static void fromOperator() {
        String[] array = {"A", "B", "C", "D"};
        // Observable.fromIterable(list) // 将集合类型的数据结构转换为单个的Obsevable发射
        // Observable.fromFuture(futrue,timeout,timeUnit,scheduler) // 将对象转换为Observable，可指定延迟时间和线程

        // 将数组类型的数据结构转换为单个的Obsevable发射
        Observable.fromArray(array)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.i("fromXxx operator result => " + s);
                    }
                });
    }

    /**
     * 操作符：range() 操作符<br/>
     * 说明：创建发射指定范围的 <b><i>整数</i></b> 序列的Observable,range操作符,发射从 start 开始的 count 个数
     */
    public static void rangeOperator() {
        Observable.range(3, 4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("range operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：defer()操作符<br/>
     * 说明：在观察者订阅之前不创建这个Observable，当被订阅时为每一个观察者创建一个新的Observable
     */
    public static void deferOperator() {
        Observable<Integer> defer = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1);
            }
        });
        // 每订阅一次就发射一个新的Observable
        defer.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("defer operator result1 => " + integer);
            }
        });
        defer.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("defer operator result2 => " + integer);
            }
        });
    }

    /**
     * 操作符：repeat() 操作符，严格说不是创建曹组符了，它作用在Observable上<br/>
     * 说明：<b><i>作用在Observable上</i></b>,会对其重复发射count次，当没指定次数时，将一直不停的发射
     */
    public static void repatOperator() {
        Observable.just(1)
                .repeat(5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("repeat operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：timer()操作符<br/>
     * 说明：Timer会在指定时间后发射一个数字0，注意其默认运行在RxJava的 computation 线程，可以指定运行的线程
     */
    public static void timerOperator() {
        Observable.timer(1000, TimeUnit.MILLISECONDS/*, AndroidSchedulers.mainThread()*/)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.i("Thread => " + Thread.currentThread() + " ; timer operator result => " + aLong);
                    }
                });
    }

    /**
     * 操作符：interval() 操作符<br/>
     * 说明：interval 会间隔一段时间发射一个整数，整数从0开始每发射，后面的一个数就在原来的基础上加1。<br/>
     * 注意其默认运行在RxJava的 computation 线程，可以指定运行的线程，同时又多个重载方法，还可以指定发射第一个数之前的延迟时间
     */
    public static void intervalOperator() {
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.i("Thread => " + Thread.currentThread() + " ; interval operator result => " + aLong);
                    }
                });
    }

    /**
     * 操作符：intervalRange() 操作符<br/>
     * 说明：和 interval 操作符类似，不同的是可以指定数字的开始大小和一共发射的个数，可以指定线程，默认在 RxJava的 computation 线程
     */
    public static void intervalRangeOperator() {
        Observable.intervalRange(3, 5, 2000, 1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.i("Thread => " + Thread.currentThread() + " ; intervalRange operator result => " + aLong);
                    }
                });
    }
}
