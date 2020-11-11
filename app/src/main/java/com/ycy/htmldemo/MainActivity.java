package com.ycy.htmldemo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ycy.xweb.MyWebView;
import com.ycy.xweb.api.ResultInterface;
import com.ycy.xweb.constant.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyWebView myWebView;
    Button btJsonParam;
    TextView tvResult;
    TextView tvParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.web);
        btJsonParam = findViewById(R.id.btJsonParam);
        tvResult = findViewById(R.id.tvResult);
        tvParam = findViewById(R.id.tvParam);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onStart() {
        super.onStart();
        final TestBean testBean = new TestBean();

        testBean.setCode("123");
        testBean.setData("this is data");
        testBean.setId("007");
        testBean.setMsg("this is msg");
        List<String> strings =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("this is stringList" + (i+1));
        }
        testBean.setStringList(strings);
        tvParam.setText("向js传递测试json数据 ===>" + GsonUtil.toJson(testBean));


        myWebView.setJavaScriptEnabled(true);
        myWebView.setResultInterface(new ResultInterface() {
            @Override
            public void onResult(String data) {
                BaseBean baseBean = GsonUtil.fromJson(data,BaseBean.class);
                tvResult.setText("返回数据：code = " + baseBean.getCode()+" 此此段可以作为标志位用以区分调用不同方法 \ndata = " + baseBean.getData());
            }

            @Override
            public void onResult() {
                tvResult.setText("js调用Android无参方法");
            }
        });
        myWebView.openUrl("file:///android_asset/testHtml.html");


        myWebView.request("haha","1","2","2","2","2","2","2","2","2","2","2");

        btJsonParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.requestJson("javacalljswithParam",GsonUtil.toJson(testBean));
            }
        });

    }
}
