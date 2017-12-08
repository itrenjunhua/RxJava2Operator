package com.renj.rxjavaoperator.operator;


import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-08   9:33
 * <p>
 * 描述：RxJava2操作符之算术和聚合操作符。包含：<br/>
 * 【 {@code count()} 操作符 {@link #countOperator()} 】、【 {@code concat()} 操作符 {@link CombinationOperator#concatOperator()} 】、
 * 【 {@code reduce()} 操作符 {@link TransformOperator#reduceOperator()} 】、【 {@code collect()}/{@code collectInto()} 操作符 {@link #collectOperator()} 】<br/><br/>
 * <b>注：</b>
 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
 * {@code concat()} 操作符查看 {@link CombinationOperator} 类中的 {@link CombinationOperator#concatOperator()} 方法
 * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
 * {@code reduce()} 操作符查看 {@link TransformOperator} 类中的 {@link TransformOperator#reduceOperator()} 方法
 * <br/><br/>
 * <b>ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MathematicalAndAggregateOperator {
    /**
     * 操作符：count() 操作符<br/>
     * 说明：Count 操作符用来统计源 Observable 发射了多少个数据，最后将数目给发射出来；<br/>
     * 如果源 Observable 发射错误，则会将错误直接报出来；在源 Observable 没有终止前，count 是不会发射统计数据的。
     */
    public static void countOperator() {
        Observable<Integer> observable = Observable.just(0, 1, 2, 3, 4);
        observable.count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.i("count operator result => " + aLong);
                    }
                });
    }

    /**
     * 操作符：collect()/collectInto() 操作符<br/>
     * 说明：Collect 用来将源 Observable 发射的数据给收集到一个数据结构里面，需要使用两个参数：<br/>
     * ① 第一个产生收集数据结构的函数；<br/>
     * ② 第二个接收第一个函数产生的数据结构和源Observable发射的数据作为参数的函数。<br/>
     */
    public static void collectOperator() {
        Observable<Integer> observable = Observable.just(0, 1, 2, 3, 4);

        // collect()
        observable.collect(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 10;
            }
        }, new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer integer, Integer integer2) throws Exception {
                Logger.i("collect second params => integer: " + integer + " ; integer2: " + integer2);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("collect operator result => " + integer);
            }
        });

        // collectInto()
        observable.collectInto(0, new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer integer, Integer integer2) throws Exception {
                Logger.i("collectInto second params => integer: " + integer + " ; integer2: " + integer2);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("collectInto operator result => " + integer);
            }
        });
    }
}
