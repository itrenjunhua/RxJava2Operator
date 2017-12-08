package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-07   18:02
 * <p>
 * 描述：RxJava2操作符之条件和布尔操作符。包含：<br/>
 * 【 {@code all()} 操作符 {@link #allOperator()} 】、【 {@code amb()} 操作符 {@link #ambOperator()} 】、
 * 【 {@code contains()} 操作符 {@link #containsOperator()} 】、【 {@code sequenceEqual()} 操作符 {@link #sequenceEqualOperator()} 】、
 * 【 {@code isEmpty()}/{@code defaultIfEmpty()} 操作符 {@link #emptyOperator()} 】、
 * 【 {@code skipUntil()}/{@code skipWhile()} 操作符 {@link #skipOperator()} 】、
 * 【 {@code takeUntil()}/{@code takeWhile()} 操作符 {@link #takeOperator()} 】
 * <br/><br/>
 * <b>ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ConditionalAndBooleanOperator {
    /**
     * 操作符：all() 操作符<br/>
     * 说明：判断Observable发射的所有的数据项是否都满足某个条件
     */
    public static void allOperator() {
        Observable.just(0, 1, 2, 3)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 2;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.i("all operator result => " + aBoolean);
            }
        });
    }

    /**
     * 操作符：amb() 操作符<br/>
     * 说明：给定多个Observable，只让第一个发射数据的Observable发射全部数据
     */
    public static void ambOperator() {
        Observable<Long> observable = Observable.just(0L, 1L, 2L, 3L);
        Observable<Long> longObservable = Observable.intervalRange(4, 5, 1000, 1000, TimeUnit.MILLISECONDS);
        List<Observable<Long>> lists = new ArrayList<>();
        lists.add(observable);
        lists.add(longObservable);
        Observable.amb(lists)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.i("amb operator result => " + aLong);
                    }
                });
    }

    /**
     * 操作符：contains() 操作符<br/>
     * 说明：判断Observable是否会发射一个指定的数据项
     */
    public static void containsOperator() {
        Observable.just(0, 1, 2, 3, 4)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Logger.i("contains operator result => " + aBoolean);
                    }
                });
    }

    /**
     * 操作符：<br/>
     * 说明：判断两个Observable是否按相同的数据序列。有多个重载方法<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * 判断两个Observable发射的数据序列是否相同（发射的数据相同，数据的序列相同，结束的状态相同），如果相同返回 true，否则返回 false
     */
    public static void sequenceEqualOperator() {
        Observable<Integer> observable1 = Observable.just(0, 1, 2, 3);
        Observable<Integer> observable2 = Observable.just(0, 1, 2, 3);
        Observable.sequenceEqual(observable1, observable2).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.i("sequenceEqual operator result => " + aBoolean);
            }
        });
    }

    /**
     * 操作符：isEmpty()/defaultIfEmpty() 操作符<br/>
     * 说明：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * {@code isEmpty()} 操作符用来判断源 Observable 是否发射过数据，没有发射过数据返回true；
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * {@code defaultIfEmpty()} 操作符会判断源 Observable 是否发射数据，如果源 Observable 发射了数据则正常发射这些数据，如果没有则发射一个默认的数据。
     */
    public static void emptyOperator() {
        // isEmpty()
        Observable.empty()
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Logger.i("isEmpty operator result => " + aBoolean);
                    }
                });

        // defaultIfEmpty(1)
        Observable.<Integer>empty()
                .defaultIfEmpty(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("defaultIfEmpty operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：skipUntil()/skipWhile() 操作符；注意和 {@code takeUntil()}、{@code takeWhile()} 操作符对比，可以查看 {@link #takeOperator()} 方法说明<br/>
     * 说明：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * {@code skipUntil()} 根据一个标志 Observable 来判断的，当这个标志 Observable <b>没有发射数据的时候，所有源 Observable 发射的数据都会被跳过；
     * 当标志Observable发射了一个数据，则开始正常地发射数据。</b>
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * {@code skipWhile()} 根据一个函数来判断是否跳过数据，<b>当函数返回值为 true 的时候则一直跳过源 Observable 发射的数据；
     * 当函数返回 false 的时候则开始正常发射数据。</b>
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * <b>注意：{@code skipWhile()} 操作符根据函数来判断是否跳过，当函数返回 true 时，就每发射一次数据都会执行函数，并且跳过当前数据；
     * 当函数返回了 false 时，那么后面就会正常发射数据(包括当前数据也会正常发射)，不在执行这个函数了。</b>
     */
    public static void skipOperator() {
        Observable<Integer> observable = Observable.just(3, 2, 1, 0, 3);
        // observable.skipUntil(Observable.just(1)) // 不会跳过数据
        observable.skipUntil(Observable.timer(1000, TimeUnit.MILLISECONDS)) // 延迟1秒将跳过所有原Observable发射的数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("skipUntil operator result => " + integer);
                    }
                });

        observable.skipWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                // 当比较的结果为 true 时，那么每发射一次数据就会在比较一次，并且跳过当前数据，
                // 当比较结果为 false 时，不在进行比较了,会正常发射后面的数据(包括当前数据也会正常发射)，不在跳过数据(当前数据也会发射成功)
                return integer > 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("skipWhile operator result => " + integer);
            }
        });
    }

    /**
     * 操作符：takeUntil()/takeWhile() 操作符；注意和 {@code skipUntil()}、{@code skipWhile()} 操作符对比，可以查看 {@link #skipOperator()} ()} 方法说明<br/>
     * 说明：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * {@code takeUntil()} 根据一个标志 Observable 来判断的，<b>当这个标志 Observable 发射了数据的时候，所有源 Observable 发射的数据都会被跳过；
     * 当标志 Observable 没有发射一个数据的时候，则正常地发射数据。</b>
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * {@code takeWhile()} 根据一个函数来判断是否跳过数据，<b>当函数返回值为 true 的时候则正常发射源 Observable 的数据；
     * 当函数返回 false 的时候将不在发射任何数据。</b>
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * <b>注意：{@code takeWhile()} 操作符根据函数来判断是否跳过，当函数返回 true 时，就每发射一次数据都会执行函数，并且数据发送成功；
     * 当函数返回了 false 时，那么就会跳过所有的数据了(包括当前的数据也会跳过)，并且不在执行这个函数了.</b>
     */
    public static void takeOperator() {
        Observable<Integer> observable = Observable.just(3, 2, 1, 0, 3);
        // observable.takeUntil(Observable.timer(1000, TimeUnit.MILLISECONDS)) // 延迟1秒将不会跳过源 Observable 的任何数据
        observable.takeUntil(Observable.just(1)) // 会跳过源 Observable 的所有数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("takeUntil operator result => " + integer);
                    }
                });

        observable.takeWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                // 当比较结果为 true 时，那么每发射一次数据就会再比较一次，并且数据发送成功；
                // 当比较结果为 false 时，那么就会跳过所有的数据了(包括当前的数据也会跳过)，也不在进行比较了。
                return integer > 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("takeWhile operator result => " + integer);
            }
        });
    }
}
