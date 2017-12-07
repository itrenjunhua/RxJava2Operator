package com.renj.rxjavaoperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.renj.rxjavaoperator.operator.CombinationOperator;
import com.renj.rxjavaoperator.operator.CreateOperator;
import com.renj.rxjavaoperator.operator.ErrorHandlerOperator;
import com.renj.rxjavaoperator.operator.FilterOperator;
import com.renj.rxjavaoperator.operator.TransformOperator;
import com.renj.rxjavaoperator.operator.UtilityOperator;

/**
 * RxJava2 操作符
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
        rxUtilityOperator();
    }

    /**
     * 调用辅助操作符类中的方法
     */
    private void rxUtilityOperator() {
        // UtilityOperator.delauOperator();
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
