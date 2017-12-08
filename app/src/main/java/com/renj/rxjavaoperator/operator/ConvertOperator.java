package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-08   14:28
 * <p>
 * 描述：RxJava2操作符之转换操作符。包含：<br/>
 * 【 {@code toXxx()} 操作符 {@link #toXxxOperator()} 】
 * <br/><br/>
 * <b>ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ConvertOperator {
    /**
     * 操作符：toXxx() 操作符<br/>
     * 说明：将 Observable 转换为其它的对象或数据结构
     */
    public static void toXxxOperator() {
        // toList()
        Observable.just("A", "B", "C")
                .toList()
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        Logger.i("toList operator result => " + strings);
                    }
                });

        // toMap()
        Observable.just("a", "b", "c")
                .toMap(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + s;
                    }
                })
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> stringStringMap) throws Exception {
                        Logger.i("toMap operator result => " + stringStringMap);
                    }
                });

        // toMultimap()
        Observable.just(0, 1, 2, 3)
                .toMultimap(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer * 2;
                    }
                })
                .subscribe(new Consumer<Map<Integer, Collection<Integer>>>() {
                    @Override
                    public void accept(Map<Integer, Collection<Integer>> integerCollectionMap) throws Exception {
                        Logger.i("toMultimap operator result => " + integerCollectionMap);
                    }
                });

        // 还有很多，不在一一列举...
    }
}
