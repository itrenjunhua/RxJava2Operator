package com.renj.rxjavaoperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.renj.rxjavaoperator.operator.CreateOperator;

/**
 * RxJava2 操作符
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 调用创建操作符类中的方法
        rxCreateOperator();
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
