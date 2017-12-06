package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-06   14:43
 * <p>
 * 描述：RxJava2操作符之变换操作符<br/>
 * 包含 map、flatMap、concatMap、reduce、scan、buffer、groupBy、window
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class TransformOperator {
    /**
     * 操作符：map() 操作符<br/>
     * 说明：映射，通过对序列的每一项都应用一个函数变换Observable发射的数据，实质是对序列中的每一项执行一个函数，函数的参数就是这个数据项
     */
    public static void mapOpertor() {
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "map transform " + integer + "; return String Type";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.i("map operator ersult => " + s);
                    }
                });
    }

    /**
     * 操作符：flatMap() 操作符<br/>
     * 说明： 对Observable发射的数据都应用(apply)一个函数，这个函数返回一个Observable，然后合并这些Observables，并且发送合并的结果
     */
    public static void flatMapOperator() {
        Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                //.concatMap() // concatMap()：异步时能够保证顺序不变  flatMap()：异步时不能保证顺序不变
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("flatMap tramsform " + (integer * integer) + "; return ObservableSource<?> Type");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        Logger.i("flatMap operator result => " + string);
                    }
                });
    }

    /**
     * 操作符：reduce() 操作符<br/>
     * 说明：对Observable发射的每一项数据应用一个函数，但是只发射最终的值，与 {@code scan()} {@link #scanOperator()} 操作符做比较
     */
    public static void reduceOperator() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("reduce operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：scan() 操作符<br/>
     * 说明：对Observable发射的每一项数据应用一个函数，然后按顺序依次发射这些值,与 {@code reduce()} {@link #reduceOperator()} 操作符做比较
     */
    public static void scanOperator() {
        Observable.just(1, 2, 3, 4)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("scan operator result => " + integer);
                    }
                });
    }

    /**
     * 操作符：buffer() 操作符<br/>
     * 说明：可以简单的理解为缓存，它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个，有多个重载方法，可指定延迟时间、间隔时间、线程等。<br/>
     * 与 {@code window()} 操作符 {@link #windowOperator()} 类似
     */
    public static void bufferOperator() {
        Observable.just(0, 1, 2, 3, 4, 5, 6)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Logger.i("buffer operator result => " + integers);
                    }
                });
    }

    /**
     * 操作符：groupBy() 操作符<br/>
     * 说明：将原来的Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据，有多个重载方法
     */
    public static void groupByOperator() {
        Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer % 3; // 分为 0 1 2 三个组
                        // return 2; // 只有一个组
                    }
                })
                .subscribe(new Consumer<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void accept(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) throws Exception {
                        Logger.i("get group => " + integerIntegerGroupedObservable);
                        integerIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Logger.i("groupBy operator result => Group: " + integerIntegerGroupedObservable.getKey() + " - value: " + integer);
                            }
                        });
                    }
                });
    }

    /**
     * 操作符：window() 操作符<br/>
     * 说明：定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项，有多个重载方法。<br/>
     * 类似于 {@code buffer()} 操作符 {@link #bufferOperator()}，但 {@code buffer()} 发射的是数据，{@code window()}发射的是Observable，每一个Observable发射原始Observable的数据的一个子集
     */
    public static void windowOperator() {
        Observable.just(0, 1, 2, 3, 4, 5, 6)
                .window(3) // 分为3个窗口发射
                .subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(final Observable<Integer> integerObservable) throws Exception {
                        Logger.i("get window => " + integerObservable);
                        integerObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Logger.i("window operator result => " + integer);
                            }
                        });
                    }
                });
    }
}
