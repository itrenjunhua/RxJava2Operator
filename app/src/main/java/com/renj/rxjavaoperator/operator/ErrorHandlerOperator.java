package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-07   14:53
 * <p>
 * 描述：RxJava2之操作符错误处理操作符。包含：<br/>
 * 【 {@code onErrorReturn()} 操作符 {@link #onErrorReturnOperator()} 】、【 {@code onErrorResumeNext(Function)} 操作符 {@link #onErrorResumeNextOperator1()} 】、
 * 【 {@code onErrorResumeNext(Observable)} 操作符 {@link #onErrorResumeNextOperator2()} 】、【 {@code onErrorReturnItem()} 操作符 {@link #onErrorReturnItemOperator()} 】、
 * 【 {@code onExceptionResumeNext()} 操作符 {@link #onExceptionResumeNextOperator()} 】、【 {@code retry()} 操作符 {@link #retryOperator()} 】、
 * 【 {@code retryWhen()} 操作符 {@link #retryWhenOperator()} 】
 * <br/><br/>
 * <b>ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ErrorHandlerOperator {
    /**
     * 操作符：onErrorReturn() 操作符<br/>
     * 说明：用返回的字符串替换错误的项，然后调用 onComplete()方法
     */
    public static void onErrorReturnOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                e.onError(new Exception("onErrorReturn Exception!!!"));
                // e.onError(new Throwable("onErrorReturn Throwable!!!"));

                e.onNext("B");
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return "onErrorReturn: New";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("onErrorReturn operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onErrorReturn operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("onErrorReturn operator onComplete");
            }
        });
    }

    /**
     * 操作符：onErrorResumeNext(Function)  操作符<br/>
     * 说明：创建一个新的 Observable 发射，发射完新的 Observable 中的所有数据后调用 onComplete()方法
     */
    public static void onErrorResumeNextOperator1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                e.onError(new Exception("onErrorResumeNext(Function) Exception!!!"));
                // e.onError(new Throwable("onErrorResumeNext(Function) Throwable!!!"));

                e.onNext("B");
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Throwable throwable) throws Exception {
                return Observable.just("onErrorResumeNext(Function): New", "a");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("onErrorResumeNext(Function) operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onErrorResumeNext(Function) operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("onErrorResumeNext(Function) operator onComplete");
            }
        });
    }

    /**
     * 操作符：onErrorResumeNext(Observable) 操作符<br/>
     * 说明：创建一个新的 Observable ，使用新的 Observable 继续发射数据，数据项发射完成不会自动调用 onComplete()方法
     */
    public static void onErrorResumeNextOperator2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                e.onError(new Exception("onErrorResumeNext(Observable) Exception!!!"));
                // e.onError(new Throwable("onErrorResumeNext(Observable) Throwable!!!"));

                e.onNext("B");
            }
        }).onErrorResumeNext(new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                observer.onNext("onErrorResumeNext(Observable):New");
                observer.onNext("a");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("onErrorResumeNext(Observable) operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onErrorResumeNext(Observable) operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("onErrorResumeNext(Observable) operator onComplete");
            }
        });
    }

    /**
     * 操作符：onErrorReturnItem()  操作符<br/>
     * 说明：用指定的字符串替换错误的项，然后调用 onComplete()方法
     */
    public static void onErrorReturnItemOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                e.onError(new Exception("onErrorReturnItem Exception!!!"));
                // e.onError(new Throwable("onErrorReturnItem Throwable!!!"));

                e.onNext("B");
            }
        }).onErrorReturnItem("onErrorReturnItem: New").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("onErrorReturnItem operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onErrorReturnItem operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("onErrorReturnItem operator onComplete");
            }
        });
    }

    /**
     * 操作符：onExceptionResumeNext()  操作符<br/>
     * 说明：<b>{@code onExceptionResumeNext()} 操作符区分异常类型</b>
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 如果抛出的是 {@link Exception} ,会先发射新的 Observable 的所有项后调用 onComplete() 方法；
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 如果抛出的是 {@link Throwable} ，那么会直接调用 onError() 方法。
     */
    public static void onExceptionResumeNextOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                e.onError(new Exception("onExceptionResumeNext Exception!!!"));
                // e.onError(new Throwable("onExceptionResumeNext Throwable!!!"));

                e.onNext("B");
            }
        }).onExceptionResumeNext(Observable.just("onExceptionResumeNext: New", "a")).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("onExceptionResumeNext operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onExceptionResumeNext operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("onExceptionResumeNext operator onComplete");
            }
        });
    }

    /**
     * 操作符：retry() 操作符<br/>
     * 说明：在发生错误的时候会重新进行订阅,而且可以重复多次，所以发射的数据可能会产生重复。
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * 有多个重载方法，可以指定重复次数、判断是否需要重复等。
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * <b>如果重复指定次数还有错误的话就会将错误返回给观察者,会调用 onError() 方法。</b>
     */
    public static void retryOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                e.onError(new Exception("onExceptionResumeNext Exception!!!"));
                // e.onError(new Throwable("onExceptionResumeNext Throwable!!!"));

                e.onNext("B");
            }
        }).retry(10).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("retry operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("retry operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("retry operator onComplete");
            }
        });
    }

    /**
     * 操作符：retryWhen() 操作符<br/>
     * 说明：当错误发生时，retryWhen会接收onError的throwable作为参数，并根据定义好的函数返回一个Observable，如果这个Observable发射一个数据，就会重新订阅这个Observable。
     */
    public static void retryWhenOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");

                // e.onError(new Exception("onExceptionResumeNext Exception!!!"));
                e.onError(new Throwable("onExceptionResumeNext Throwable!!!"));

                e.onNext("B");
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Observable<Throwable> throwableObservable) throws Exception {
                return Observable.just("a", "retryWhen: New", "c");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("retryWhen operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("retryWhen operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("retryWhen operator onComplete");
            }
        });
    }
}
