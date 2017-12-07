package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-07   16:50
 * <p>
 * 描述：RxJava2操作符之辅助操作符<br/>
 * 包含 delay、doXxx、subscribeOn、observeOn、materialize、dematerialize、timeInterval、timestamp、timeout、using
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class UtilityOperator {
    /**
     * 操作符：delay() 操作符<br/>
     * 说明：延迟一段时间发射结果数据。多个重载方法，可以指定运行线程、出错时是否延迟发送等
     */
    public static void delauOperator() {
        Observable.just(0, 1, 2)
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("delay operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：doXxx() 操作符<br/>
     * 说明：Do操作符就是给Observable的生命周期的各个阶段加上一系列的回调监听，当Observable执行到这个阶段的时候，这些回调就会被触发。
     * 这里只是列举了一部分，还有很多
     */
    public static void doOperator() {
        Observable.just("A").doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("doOnNext");
            }
        }).doAfterNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("doAfterNext");
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Logger.i("doOnSubscribe");
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Logger.i("doOnComplete");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("result => " + s);
            }
        });
    }

    /**
     * 操作符：subscribeOn()、observeOn() 操作符<br/>
     * 说明：SubscribeOn：指定Observable应该在哪个调度程序上执行；ObserveOn：指定Subscriber的调度程序（工作线程）
     */
    public static void threadSchedulerOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Logger.i("Observable runing thread => " + Thread.currentThread());
                e.onNext("A-B-C-D");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.i("Observer runing thread => " + Thread.currentThread() + " ;result => " + s);
                    }
                });
    }

    /**
     * 操作符：materialize()、dematerialize() 操作符<br/>
     * 说明：Meterialize操作符将OnNext/OnError/OnComplete都转化为一个Notification对象并按照原来的顺序发射出来,dematerialize相反
     */
    public static void notifycationOperator() {
        Observable.just("RxJava Notifycation1", "RxJava Notifycation2")
                .materialize()
                .subscribe(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        String value = stringNotification.getValue();
                        Logger.i("materialize operator result => " + value);
                    }
                });

        Observable.just(Notification.createOnNext("RxJava Notifycation1"), Notification.createOnNext("RxJava Notifycation2"))
                .<String>dematerialize()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.i("dematerialize operator result => " + s);
                    }
                });
    }

    /**
     * 操作符：timeInterval() 操作符<br/>
     * 说明：TimeInterval会拦截发射出来的数据，取代为前后两个发射两个数据的间隔时间。对于第一个发射的数据，其时间间隔为订阅后到首次发射的间隔。多个重载，可以指定时间单位和线程。<br/>
     * 可以与 {@code timestamp()} 操作符比较，查看 {@link #timeIntervalOperator()} 方法
     */
    public static void timeIntervalOperator() {
        Observable.just(0, 1, 2, 3)
                .timeInterval()
                .subscribe(new Consumer<Timed<Integer>>() {
                    @Override
                    public void accept(Timed<Integer> integerTimed) throws Exception {
                        Logger.i("timeInterval operator result => " + "time: " + integerTimed.time() + " ; value: " + integerTimed.value());
                    }
                });
    }

    /**
     * 操作符：timestamp() 操作符<br/>
     * 说明：TimeStamp会将每个数据项给重新包装一下，加上了一个时间戳来标明每次发射的时间。多个重载，可以指定时间单位和线程。<br/>
     * 可以与 {@code timeInterval()} 操作符比较，查看 {@link #timeIntervalOperator()} 方法
     */
    public static void timeStampOperator() {
        Observable.just(0, 1, 2, 3)
                .timestamp()
                .subscribe(new Consumer<Timed<Integer>>() {
                    @Override
                    public void accept(Timed<Integer> integerTimed) throws Exception {
                        Logger.i("timestamp operator result => " + "time: " + integerTimed.time() + " ; value: " + integerTimed.value());
                    }
                });
    }

    /**
     * 操作符：timeout() 操作符<br/>
     * 说明：Timeout操作符给Observable加上超时时间，每发射一个数据后就重置计时器，当超过预定的时间还没有发射下一个数据，就抛出一个超时的异常。有多个重载，可以自定义更多的功能
     */
    public static void timeoutOperator() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(0);
                        e.onNext(1);
                        Thread.sleep(1500);
                        e.onNext(2);
                    }
                })
                .timeout(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.i("timeout operator result => " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("timeout operator onError => " + e);
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("timeout operator onComplete ");
                    }
                });
    }

    /**
     * 操作符：using() 操作符<br/>
     * 说明： 创建一个只在Observable的生命周期内存在的一次性资源<br/><br/>
     * 参数说明：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 创建这个一次性资源的函数
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 创建Observable的函数
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ③ 释放资源的函数
     */
    public static void usingOperator() {
        Observable.using(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "inner data";
            }
        }, new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.just(s);
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("using inner data => " + s);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("using operator result => " + s);
            }
        });
    }
}
