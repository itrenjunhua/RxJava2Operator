package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
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
 * 描述：RxJava2操作符之组合操作符<br/>
 * 包含 merger/concat、zip、join、combineLatest、switchOnNext/switchMap、startWith、and/then/when
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CombinationOperator {
    /**
     * 操作符：merger() 操作符<br/>
     * 说明：将两个后多个Observable/Iterable发射的数据组合并成一个。
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * Merge 操作符可能会让合并的Observables发射的数据交错(可以使用 Concat 操作符【查看 {@link #concatOperator()} 】,不会让数据交错，它会按顺序一个接着一个发射多个Observables的发射项)。
     *
     * @see #concatOperator()
     */
    public static void mergeOperator() {
        Observable<Long> observable1 = Observable.intervalRange(1, 5, 500, 1000, TimeUnit.MILLISECONDS);
        Observable<Long> observable2 = Observable.intervalRange(6, 5, 1000, 1000, TimeUnit.MILLISECONDS);
        Observable.merge(observable1, observable2)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long l) throws Exception {
                        Logger.i("merge operator result => " + l);
                    }
                });
    }

    /**
     * 操作符：concat() 操作符<br/>
     * 说明：将两个后多个Observable/Iterable发射的数据组合并成一个。
     * <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * Concat 操作符不会让合并的Observables发射的数据交错，它会按顺序一个接着一个发射多个Observables的发射项(而 Merge 操作符【查看 {@link #mergeOperator()} 】,可能会让数据交错)。
     *
     * @see #mergeOperator()
     */
    public static void concatOperator() {
        Observable<Long> observable1 = Observable.intervalRange(1, 5, 500, 1000, TimeUnit.MILLISECONDS);
        Observable<Long> observable2 = Observable.intervalRange(6, 5, 1000, 1000, TimeUnit.MILLISECONDS);
        Observable.concat(observable1, observable2)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long l) throws Exception {
                        Logger.i("concat operator result => " + l);
                    }
                });
    }

    /**
     * 操作符：zip() 操作符<br/>
     * 说明：使用一个指定的函数将多个Observable发射的数据组合在一起，然后将这个函数的结果作为单项数据发射。类似操作符 {@code zipIterable()}、{@code zipArray()}
     * <br/><br/>
     * <b>注意：</b>
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① Zip操作符将多个Observable发射的数据按顺序组合起来，每个数据只能组合一次，而且都是有序的。
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * <b>② 最终组合的数据的数量由发射数据最少的Observable来决定。</b>
     */
    public static void zipOperator() {
        Observable<Integer> observable1 = Observable.just(0, 1, 2, 3);
        Observable<String> observable2 = Observable.just("A", "B", "C");
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + " : " + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("zip operator result => " + s);
            }
        });

    }

    /**
     * 操作符：join() 操作符<br/>
     * 说明：无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射.<br/>
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 源Observable.join(所要组合的目标Observable)；
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 第一个函数，接收从源Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了源Observable发射出来数据的有效期；
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ③ 第二个函数，接收从目标Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了目标Observable发射出来数据的有效期；
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ④ 第三个函数，接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据。
     */
    public static void joinOperator() {
        Observable<Long> observable1 = Observable.intervalRange(10, 20, 1000, 1000, TimeUnit.MILLISECONDS);
        Observable<Long> observable2 = Observable.interval(1000, TimeUnit.MILLISECONDS);
        observable1.join(observable2, new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long l) throws Exception {
                return Observable.just(l);
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
     * 操作符：combineLatest() 操作符<br/>
     * 说明：当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），然后发射这个函数的结果.<br/>
     * 比如：Observable1发射了A并且Observable2发射了B和C，{@code combineLatest()}将会分组处理AB和AC。<br/><br/>
     * 必须满足的两个条件:
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * 1)所有的Observable都发射过数据；
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * 2)满足条件1的时候任何一个Observable发射一个数据，就将所有Observable最新发射的数据按照提供的函数组装起来发射出去。<br/><br/>
     * <b>注意：
     * </b><br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * <b>在这两个条件下,可能会忽略掉一些发射的数据.</b>
     */
    public static void combineLatestOperator() {
        Observable<Integer> observable1 = Observable.just(0, 1, 2);
        Observable<String> observable2 = Observable.just("A", "B", "C");
        Observable.combineLatest(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return "BiFunction.apply: " + integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("combineLatest operator result => " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("combineLatest operator onError => " + e);
            }

            @Override
            public void onComplete() {
                Logger.i("combineLatest operator onComplete");
            }
        });
    }

    /**
     * 操作符：switchXxx() 操作符<br/>
     * 说明：将一个发射Observable序列的Observable转换为这样一个Observable：它逐个发射那些Observable最近发射的数据。
     * 用来将一个发射多个小Observable的源Observable转化为一个Observable，然后发射这多个小Observable所发射的数据。<br/>
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * <b>需要注意的就是，如果一个小的Observable正在发射数据的时候，源Observable又发射出一个新的小Observable，
     * 则前一个Observable发射的数据会被抛弃，直接发射新的小Observable所发射的数据。</b>
     */
    public static void switchOperator() {
        Observable<Observable<String>> observable0 = Observable.just(0, 1, 2, 3)
                .map(new Function<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> apply(Integer integer) throws Exception {
                        return Observable.just("new data " + integer);
                    }
                });

        // switchOnNext() 操作符 还有重载方法和类似方法
        Observable.switchOnNext(observable0).subscribe(new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                Logger.i("switchOnNext operator result => " + string);
            }
        });

        // switchMap() 操作符 还有重载方法和类似方法
        Observable<Integer> observable = Observable.just(0, 1, 2, 3);
        observable.switchMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just("switchMap new Data " + integer);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i("switch operator result => " + s);
            }
        });
    }

    /**
     * 操作符：startWith() 操作符<br/>
     * 说明：在发射原来的Observable的数据序列之前，先发射一个指定的数据序列或数据项(在数据序列的开头插入一条指定的项)。<br/>
     * 这个指定的项可以是单个的发射项；也可以是数组、列表或者一个Observable。
     */
    public static void startWithOperator() {
        Observable.just(0, 1, 2, 3)
                .startWith(100)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.i("startWith operator result => " + integer);
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
