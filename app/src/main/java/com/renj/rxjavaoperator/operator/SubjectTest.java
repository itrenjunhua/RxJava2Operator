package com.renj.rxjavaoperator.operator;

import com.renj.rxjavaoperator.Logger;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-11   9:46
 * <p>
 * 描述：RxJava2之Subject使用示例，包括：<br/>
 * 【 {@code AsyncSubject} 类 {@link #asyncSubject()} 】、【 {@code BehaviorSubject} 类 {@link #behaviorSubject()} 】、
 * 【 {@code PublishSubject} 类 {@link #publishSubject()} 】、【 {@code ReplaySubject} 类 {@link #replaySubject()} 】、
 * 【 {@code SerializedSubject} 类(注意，在 RxJava2 SerializedSubject 类修饰符修改为默认的，不再是 public) {@link #serializedSubject()} 】
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SubjectTest {
    /**
     * AsyncSubject 类<br/>
     * 说明：使用AsyncSubject无论输入多少参数，永远只输出最后一个参数<br/><br/>
     * 注意：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 一定要用Subcect.create()的方式创建并使用，不要用just(T)、from(T)、create(T)创建，否则会导致失效
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 如果因为发生了错误而终止，AsyncSubject 将不会发射任何数据，只是简单的向前传递这个错误通知
     */
    public static void asyncSubject() {
        AsyncSubject asyncSubject = AsyncSubject.create();
        asyncSubject.onNext(0);
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);

        // 注释抛出错误
        // asyncSubject.onError(new Throwable());
        // asyncSubject.onError(new Exception());

        // 如果不调用 onComplete() 方法，就接收不到任何的数据
        asyncSubject.onComplete();

        asyncSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("AsyncSubject.create() result => " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("AsyncSubject.create() error => " + throwable);
            }
        });

        // 如果用 just 等发射数据，会将 AsyncSubject 转换成 普通的 Observable
        // Observable<Integer> observable = AsyncSubject.just(0, 1, 2);
        // Observable<Object> observable1 = AsyncSubject.from();
        AsyncSubject.just(0, 1, 2, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Logger.i("AsyncSubject.just(T...) result => " + integer);
                    }
                });
    }

    /**
     * BehaviorSubject 类<br/>
     * 说明：发送离订阅最近的上一个值，没有上一个值的时候会发送默认值<br/><br/>
     * 注意：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 一定要用Subcect.create()的方式创建并使用，不要用just(T)、from(T)、create(T)创建，否则会导致失效
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 如果遇到错误<b>程序会直接中断</b>
     */
    public static void behaviorSubject() {
        // Observable<Integer> observable = BehaviorSubject.just(0);
        // Observable<Object> from = BehaviorSubject.from();
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.onNext("A");
        behaviorSubject.onNext("B");
        behaviorSubject.onNext("C");

        behaviorSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("BehaviorSubject_1 result => " + s);
            }
        });

        // 注释抛出错误
        // behaviorSubject.onError(new Throwable());
        // behaviorSubject.onError(new Exception());

        behaviorSubject.onNext("D");

        // 不调用 onCompleted() 方法，也能接收到数据
        // behaviorSubject.onCompleted();
        behaviorSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("BehaviorSubject_2 result => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("BehaviorSubject_2 error => " + throwable);
            }
        });

    }

    /**
     * 操作符：PublishSubject 类<br/>
     * 说明：从哪里订阅就从哪里开始发送数据，与 {@link ReplaySubject} 类做比较，查看 {@link #replaySubject()} 方法<br/><br/>
     * 注意：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 一定要用Subcect.create()的方式创建并使用，不要用just(T)、from(T)、create(T)创建，否则会导致失效
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 遇到错误，如果重写些错误回调，向前传递这个错误通知，没有写错误回调的话，程序将直接报错，抛出异常，终止程序
     */
    public static void publishSubject() {
        // Observable<Integer> observable = PublishSubject.just(0);
        // Observable<Object> from = PublishSubject.from();

        PublishSubject<String> publishSubject = PublishSubject.create();
        // 在这里订阅，接收所有值 在这个示例中接收 a b  c
        publishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("PublishSubject result_1 => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("PublishSubject error1 => " + throwable);
            }
        });
        publishSubject.onNext("a");
        // 在这里订阅，已经发送的值接收不到 在这个示例中接收 b c
        publishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("PublishSubject result_2 => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("PublishSubject error2 => " + throwable);
            }
        });

        // 注释抛出错误
        // publishSubject.onError(new Throwable());
        // publishSubject.onError(new Exception());

        publishSubject.onNext("b");
        // 在这里订阅，已经发送的值接收不到 在这个示例中接收 c
        publishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("PublishSubject result_3 => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("PublishSubject error3 => " + throwable);
            }
        });
        publishSubject.onNext("c");
        // 在这里订阅，已经发送的值接收不到 在这个示例中已经接收不到任何数据了
        publishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("PublishSubject result_4 => " + s);
            }
        }/*, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("PublishSubject error4 => " + throwable);
            }
        }*/);
    }

    /**
     * 操作符：ReplaySubject 类<br/>
     * 说明：无论何时订阅，都会将所有历史订阅内容全部发出，与 {@link PublishSubject} 类做比较，查看 {@link #publishSubject()} 方法<br/><br/>
     * 注意：
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ① 一定要用Subcect.create()的方式创建并使用，不要用just(T)、from(T)、create(T)创建，否则会导致失效
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;
     * ② 遇到错误，如果重写些错误回调，向前传递这个错误通知，没有写错误回调的话，程序将直接报错，抛出异常，终止程序
     */
    public static void replaySubject() {
        // Observable<Integer> observable = ReplaySubject.just(0);
        // Observable<Object> from = ReplaySubject.from();
        ReplaySubject<String> replaySubject = ReplaySubject.create();

        // 在这里订阅，接收所有值 接收 a b  c
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("ReplaySubject result_1 => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("ReplaySubject error1 => " + throwable);
            }
        });
        replaySubject.onNext("Aa");
        // 在这里订阅，已经发送的值接收不到 接收 b c
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("ReplaySubject result_2 => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("ReplaySubject error2 => " + throwable);
            }
        });

        // 注释抛出错误
        // replaySubject.onError(new Throwable());
        // replaySubject.onError(new Exception());

        replaySubject.onNext("Bb");
        // 在这里订阅，已经发送的值接收不到 接收 c
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("ReplaySubject result_3 => " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("ReplaySubject error3 => " + throwable);
            }
        });
        replaySubject.onNext("Cc");
        // 在这里订阅，已经发送的值接收不到 在这里已经接收不到任何数据了
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Logger.i("ReplaySubject result_4 => " + s);
            }
        }/*, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("ReplaySubject error4 => " + throwable);
            }
        }*/);
    }

    /**
     * 操作符：SerializedSubject 类<br/>
     * 说明：在并发情况下，不推荐使用通常的Subject对象，而是推荐使用 SerializedSubject，并发时只允许一个线程调用onNext等方法.<br/>
     * 将一个普通的 Subject 变换为 SerializedSubject 只需要调用 toSerialized() 方法即可。
     */
    public static void serializedSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        Subject<Integer> serialized = publishSubject.toSerialized();

        // 注意这里为什么先订阅，因为 SerializedSubject 包裹的 Subject 是 PublishSubject
        serialized.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                Logger.i("SerializedSubject result => " + integer);
            }
        });

        serialized.onNext(0);
        serialized.onNext(1);
        serialized.onNext(2);
    }
}
