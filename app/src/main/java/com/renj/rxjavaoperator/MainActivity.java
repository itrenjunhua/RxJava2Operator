package com.renj.rxjavaoperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.renj.rxjavaoperator.operator.CombinationOperator;
import com.renj.rxjavaoperator.operator.ConditionalAndBooleanOperator;
import com.renj.rxjavaoperator.operator.ConnectableOperator;
import com.renj.rxjavaoperator.operator.ConvertOperator;
import com.renj.rxjavaoperator.operator.CreateOperator;
import com.renj.rxjavaoperator.operator.ErrorHandlerOperator;
import com.renj.rxjavaoperator.operator.FilterOperator;
import com.renj.rxjavaoperator.operator.MathematicalAndAggregateOperator;
import com.renj.rxjavaoperator.operator.SubjectTest;
import com.renj.rxjavaoperator.operator.TransformOperator;
import com.renj.rxjavaoperator.operator.UtilityOperator;

/**
 * RxJava2 常用操作符<br/>
 * ReactiveX 系列所有操作符以及RxJava2与RxJava1的操作符变化可查看 <a href="http://reactivex.io/documentation/operators.html">ReactiveX 操作符</a>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 调用创建操作符类中的方法
        // rxCreateOperator();

        // 调用变换操作符类中的方法
        // rxTransformOperator();

        // 调用过滤操作符类中的方法
        // rxFilterOperator();

        // 调用组合操作符类中的方法
        // rxCombinationOperator();

        // 调用错误处理操作符类中的方法
        // rxErrorHandlerOperator();

        // 调用辅助操作符类中的方法
        // rxUtilityOperator();

        // 调用条件和布尔操作符类中的方法
        // rxConditionalAndBooleanOperator();

        // 调用算术和聚合操作符类中的方法
        // rxMathematicalAndAggregateOperator();

        // 调用连接操作符类中的方法
        // rxConnectableOperator();

        // 调用转换操作符类中的方法
        // rxConvertOperator();

        // Subject示例
        rxSubjectTest();
    }

    /**
     * Subject示例
     */
    private void rxSubjectTest() {
        // SubjectTest.asyncSubject();
        // SubjectTest.behaviorSubject();
        // SubjectTest.publishSubject();
        SubjectTest.replaySubject();
    }

    /**
     * 调用转换操作符类中的方法
     */
    private void rxConvertOperator() {
        ConvertOperator.toXxxOperator();
    }

    /**
     * 调用连接操作符类中的方法
     */
    private void rxConnectableOperator() {
        // ConnectableOperator.publishOperator();
        // ConnectableOperator.connectOperator();
        // ConnectableOperator.refCountOperator();
        ConnectableOperator.replayOperator();
    }

    /**
     * 调用算术和聚合操作符类中的方法
     */
    private void rxMathematicalAndAggregateOperator() {
        // MathematicalAndAggregateOperator.countOperator();
        MathematicalAndAggregateOperator.collectOperator();
    }

    /**
     * 调用条件和布尔操作符类中的方法
     */
    private void rxConditionalAndBooleanOperator() {
        // ConditionalAndBooleanOperator.allOperator();
        // ConditionalAndBooleanOperator.ambOperator();
        // ConditionalAndBooleanOperator.containsOperator();
        // ConditionalAndBooleanOperator.sequenceEqualOperator();
        // ConditionalAndBooleanOperator.emptyOperator();
        // ConditionalAndBooleanOperator.skipOperator();
        ConditionalAndBooleanOperator.takeOperator();
    }

    /**
     * 调用辅助操作符类中的方法
     */
    private void rxUtilityOperator() {
        // UtilityOperator.delayOperator();
        // UtilityOperator.doOperator();
        // UtilityOperator.threadSchedulerOperator();
        // UtilityOperator.notifycationOperator();
        // UtilityOperator.timeIntervalOperator();
        // UtilityOperator.timeStampOperator();
        // UtilityOperator.timeoutOperator();
        UtilityOperator.usingOperator();
    }

    /**
     * 调用错误处理操作符类中的方法
     */
    private void rxErrorHandlerOperator() {
        // ErrorHandlerOperator.onErrorReturnOperator();
        // ErrorHandlerOperator.onErrorResumeNextOperator1();
        // ErrorHandlerOperator.onErrorResumeNextOperator2();
        // ErrorHandlerOperator.onErrorReturnItemOperator();
        // ErrorHandlerOperator.onExceptionResumeNextOperator();
        // ErrorHandlerOperator.retryOperator();
        ErrorHandlerOperator.retryWhenOperator();
    }

    /**
     * 调用组合操作符类中的方法
     */
    private void rxCombinationOperator() {
        // CombinationOperator.mergeOperator();
        // CombinationOperator.concatOperator();
        // CombinationOperator.zipOperator();
        // CombinationOperator.joinOperator();
        // CombinationOperator.combineLatestOperator();
        // CombinationOperator.switchOperator();
        // CombinationOperator.startWithOperator();
        CombinationOperator.andThenWhenOperator();
    }

    /**
     * 调用过滤操作符类中的方法
     */
    private void rxFilterOperator() {
        // FilterOperator.filterOperator();
        // FilterOperator.distinctOperator();
        // FilterOperator.elementAtOperator();
        // FilterOperator.debounceOperator();
        // FilterOperator.ignoreElementsOperator();
        // FilterOperator.sampleOperator();
        // FilterOperator.firstOperator();
        // FilterOperator.lastOperator();
        // FilterOperator.skipOperator();
        FilterOperator.takeOperator();
    }

    /**
     * 调用变换操作符类中的方法
     */
    private void rxTransformOperator() {
        // TransformOperator.mapOpertor();
        // TransformOperator.flatMapOperator();
        // TransformOperator.reduceOperator();
        // TransformOperator.scanOperator();
        // TransformOperator.bufferOperator();
        // TransformOperator.groupByOperator();
        TransformOperator.windowOperator();
    }

    /**
     * 调用创建操作符类中的方法
     */
    private void rxCreateOperator() {
        // CreateOperator.createOperator();
        // CreateOperator.justOperator(1);
        // CreateOperator.justOperator("A", "B", "C");
        // CreateOperator.specialOperator();
        // CreateOperator.fromOperator();
        // CreateOperator.rangeOperator();
        // CreateOperator.deferOperator();
        // CreateOperator.repatOperator();
        // CreateOperator.timerOperator();
        // CreateOperator.intervalOperator();
        CreateOperator.intervalRangeOperator();
    }
}
