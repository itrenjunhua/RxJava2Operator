package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-08   11:32
 * <p>
 * 描述：RxJava2操作符之连接操作符。包含：<br/>
 * 【 {@code publish()} 操作符 {@link #publishOperator()} 】、【 {@code connect()} 操作符 {@link #connectOperator()} 】、
 * 【 {@code refCount()} 操作符 {@link #refCountOperator()} 】、【 {@code replay()} 操作符 {@link #replayOperator()} 】
 * <br/><br/>
 * <b>注：什么是可连接的 Observable(Connectable Observable)：
 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
 * 可连接的 Observable 是一种特殊的 Observable 对象，并不是 订阅(Subscrib) 的时候就发射数据，而是只有对其调用 {@code connect()} 操作符的时候才开始发射数据，
 * 所以可以用来更灵活的控制数据发射的时机。</b>
 * <br/><br/>
 * <b>ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ConnectableOperator {
    /**
     * 操作符：publish() 操作符<br/>
     * 说明：将一个普通的 Observable 对象转化为一个可连接的(Connectable Observable)。需要注意的是如果发射数据已经开始了再进行订阅只能接收以后发射的数据。
     */
    public static void publishOperator() {
        // 并不会打印任何数据，因为转换成为一个 Connectable Observable 后没有调用 {@code connect()} 操作符
        Observable.just(0, 1, 2, 3)
                .publish()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("publish operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：connect() 操作符<br/>
     * 说明：Connect操作符就是用来触发 Connectable Observable 发射数据的。
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * 调用 {@code connect()} 操作符后会返回一个 {@link Disposable} 对象，通过这个 {@link Disposable} 对象，我们可以调用其{@code dispose()} 方法来终止数据的发射；
     * 另外，即使还没有订阅者订阅的时候就调用 {@code connect()} 操作符也是可以使其开始发射数据的(只要调用了 {@code connect()} 方法就会开始发射数据，不管是否有订阅者订阅的事件)。
     */
    public static void connectOperator() {
        final ConnectableObservable<Integer> connectableObservable = Observable.just(0, 1, 2, 3)
                .publish(); // 使用 publish() 操作符转换成一个 Connectable Observable
        connectableObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("connect operator result => " + integer);
            }
        });

        // 使用intervalRange操作符发射一定范围内的数据，当数据发射完成之后再调用 ConnectableObservable 对象的 connect() 方法开发发射 ConnectableObservable 的数据
        Observable.intervalRange(4, 3, 1000, 1000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.i("intervalRange operator onNext => " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Logger.i("intervalRange operator onComplete");
                        connectableObservable.connect(); // 调用了 connect() 方法之后才会开发发射数据
                    }
                });
    }

    /**
     * 操作符：refCount() 操作符<br/>
     * 说明：RefCount 操作符就是将一个 Connectable Observable 对象再重新转化为一个普通的 Observable 对象，这时候订阅者进行订阅时就会触发数据的发射
     */
    public static void refCountOperator() {
        ConnectableObservable<Integer> connectableObservable = Observable.just(0, 1, 2, 3).publish();
        connectableObservable.refCount()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("refCount operator onNext => " + integer);
                    }
                });
    }

    /**
     * 操作符：replay() 操作符<br/>
     * 说明：Replay 操作符返回一个 Connectable Observable 对象并且可以缓存其发射过的数据，这样即使有订阅者在其发射数据之后进行订阅也能收到其之前发射过的数据。<br/><br/>
     * <b>注意：<br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 使用 {@code replay()} 操作符我们最好还是限定其缓存的大小，否则缓存的数据太多了可会占用很大的一块内存，有多个重载方法可以指定缓存大小、运行线程的。
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② {@code replay()} 操作符直接返回一个 Connectable Observable 对象,不用在调用 {@code publish()} 操作符</b>
     */
    public static void replayOperator() {
        final ConnectableObservable<Integer> connectableObservable = Observable.just(0, 1, 2, 3).replay();
        // 还没有订阅就开始发射数据
        connectableObservable.connect();
        // 指定延时1秒后发射一个数据，然后接收到数据之后才开始订阅 ConnectableObservable 发射的数据
        Observable.just("A")
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.i("delay 1000 milliseconds " + s);
                        connectableObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Logger.i("replay operator result => " + integer);
                            }
                        });
                    }
                });
    }
}
