# HTMLDemo
对WebView进行简单封装

使用
layout



<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">


    <com.ycy.xweb.MyWebView
        android:id="@+id/web"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <View
        android:layout_width="match_parent"
        android:layout_height="1px"
        android:background="#D6D6D6"/>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1.5"
        android:orientation="vertical">
        <TextView
            android:id="@+id/tvResult"
            android:layout_margin="10dp"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:gravity="center"
            android:layout_weight="3"
            android:text="result"/>

        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="#D6D6D6"/>
        <TextView
            android:id="@+id/tvParam"
            android:layout_margin="10dp"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:gravity="center"
            android:layout_weight="2"
            android:background="#fff"/>

        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="#D6D6D6"/>
        <Button
            android:id="@+id/btJsonParam"
            android:layout_margin="10dp"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:gravity="center"
            android:layout_weight="1"
            android:text="开始调用js"/>


    </LinearLayout>


</LinearLayout>

  

activity




    MyWebView myWebView;
    Button btJsonParam;
    TextView tvResult;
    TextView tvParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyWebView myWebView = findViewById(R.id.web);
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



