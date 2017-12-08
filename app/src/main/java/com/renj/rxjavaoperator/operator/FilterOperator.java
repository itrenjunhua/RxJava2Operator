package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-06   16:27
 * <p>
 * 描述：RxJava2操作符之过滤操作符。包含：<br/>
 * 【 {@code filter()} 操作符 {@link #filterOperator()} 】、【 {@code distinct()} 操作符 {@link #distinctOperator()} 】、
 * 【 {@code elementAt()} 操作符 {@link #elementAtOperator()} 】、【 {@code debounce()} 操作符 {@link #debounceOperator()} 】、
 * 【 {@code ignoreElements()} 操作符 {@link #ignoreElementsOperator()} 】、【 {@code sample()} 操作符 {@link #sampleOperator()} 】、
 * 【 {@code first()}/{@code firstElement()} 操作符 {@link #firstOperator()} 】、【 {@code last()}/{@code lastElement()} 操作符 {@link #lastOperator()} 】、
 * 【 {@code skip()}/{@code skipLast()} 操作符 {@link #skipOperator()} 】、【 {@code take()}/{@code takeLast()} 操作符 {@link #takeOperator()} 】
 * <br/><br/>
 * <b>ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FilterOperator {
    /**
     * 操作符：filter() 操作符<br/>
     * 说明：过滤掉没有通过谓词测试的数据项，只发射通过测试的
     */
    public static void filterOperator() {
        Observable.just(0, 1, 2, 3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3; // 返回true的才能发射
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("filter operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：distinct() 操作符<br/>
     * 说明：过滤掉重复数据项
     */
    public static void distinctOperator() {
        Observable.just(0, 1, 3, 2, 3, 0, 4)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("distinct operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：elementAt() 操作符<br/>
     * 说明：取特定位置的数据项
     */
    public static void elementAtOperator() {
        Observable.just(0, 1, 2, 3)
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("elementAt operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：debounce() 操作符<br/>
     * 说明：只有在空闲了一段时间后才发射数据，通俗的说，就是如果一段时间没有操作，就执行一次操作.还可以根据一个函数来进行限流。这个函数的返回值是一个临时Observable，
     * 如果源Observable在发射一个新的数据的时候，上一个数据根据函数所生成的临时Observable还没有结束,没有调用onComplete，那么上一个数据就会被过滤掉。如果是最后一个,还是会发射.
     */
    public static void debounceOperator() {
        Observable.just(0, 1, 2, 3)
                // .debounce(1000, TimeUnit.MILLISECONDS, Schedulers.newThread())
                .debounce(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.just(integer);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("debounce operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：ignoreElements() 操作符<br/>
     * 说明：忽略所有的数据，只保留终止通知(onError或onCompleted)
     */
    public static void ignoreElementsOperator() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        try {
                            e.onNext(1);
                            e.onComplete();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            e.onError(e1);
                        }
                    }
                })
                .ignoreElements()
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.i("ignoreElements operator onComplete()");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e("ignoreElements operator onError()");
                    }
                });
    }

    /**
     * 操作符：sample() 操作符<br/>
     * 说明：定期发射最新的数据，等于是数据抽样。多个重载方法，可指定线运行所在线程等，默认 RxJava的 computation 线程
     */
    public static void sampleOperator() {
        Observable.just(0, 1, 2, 3, 4)
                .sample(1000, TimeUnit.MILLISECONDS, true/*false*/)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.i("Thread " + Thread.currentThread() + "; sample operator result => " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i("Thread " + Thread.currentThread() + "; sample operator onError => " + e);
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("Thread " + Thread.currentThread() + "; sample operator onComplete ");
                    }
                });
    }

    /**
     * 操作符：first()/firstElement() 操作符<br/>
     * 说明：只发射满足条件的第一条数据
     */
    public static void firstOperator() {
        Observable<Integer> observable = Observable.just(0, 1, 2, 3);

        observable.first(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("first operator result => " + integer);
                    }
                });

        observable.firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("firstElement operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：last()/lastElement() 操作符<br/>
     * 说明：只发射最后一条数据
     */
    public static void lastOperator() {
        Observable<Integer> observable = Observable.just(0, 1, 2, 3);

        observable.last(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("last operator result => " + integer);
                    }
                });

        observable.lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("lastElement operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：skip()/skipLast() 操作符<br/>
     * 说明：跳过前面/最后 count 项 ，多个重载方法，可设置延迟时间和运行线程，默认非主线程
     */
    public static void skipOperator() {
        Observable<Integer> observable = Observable.just(0, 1, 2, 3, 4);

        // 跳过前面2项
        observable.skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("Thread: " + Thread.currentThread() + " ;skip operator result => " + integer);
                    }
                });

        // 跳过最后2项
        observable.skipLast(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("Thread: " + Thread.currentThread() + " ;skipLast operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：take()/takeLast() 操作符<br/>
     * 说明：只取前面/最后 count 项 ，多个重载方法，可设置延迟时间和运行线程，默认非主线程
     */
    public static void takeOperator() {
        Observable<Integer> observable = Observable.just(0, 1, 2, 3, 4);

        // 只取前面2项
        observable.take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("Thread: " + Thread.currentThread() + " ;take operator result => " + integer);
                    }
                });

        // 只取最后2项
        observable.takeLast(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("Thread: " + Thread.currentThread() + " ;takeLast operator result => " + integer);
                    }
                });
    }
}
