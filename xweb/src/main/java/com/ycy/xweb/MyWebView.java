package com.ycy.xweb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ycy.xweb.api.ResultInterface;
import com.ycy.xweb.constant.Constants;
import com.ycy.xweb.utils.HTMLParamUtils;


/**
 * WebView
 * 2020/11/11
 * ycy
 */
public class MyWebView extends WebView {

    private ResultInterface resultInterface;
    public MyWebView(Context context) {
        super(context);
    }

    public void setResultInterface(ResultInterface resultInterface) {
        this.resultInterface = resultInterface;
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @SuppressLint("AddJavascriptInterface")
    public void setJavaScriptEnabled(boolean javaScriptEnabled) {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(javaScriptEnabled);
        addJavascriptInterface(this, Constants.INTERFACE_NAME);
    }


    public void openUrl(final String url){
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("openUrl method param 'url' is null");
        }
        loadUrl(url);
    }

    /**
     * 请求无参js方法 适用于单参数请求
     * @param methodName 方法名
     */
    public void request(String methodName) {
        if (TextUtils.isEmpty(methodName)) {
            throw new NullPointerException("request method param 'methodName' is null");
        }
        request(methodName, Constants.EMPTY);
    }

    /**
     * 请求带参js方法
     * 适用于多参数请求
     * @param methodName 方法名
     * @param params     参数列表
     */
    public void request(String methodName, String... params) {
        if (TextUtils.isEmpty(methodName)) {
            throw new NullPointerException("request method param 'methodName' is null");
        }
        String param = Constants.EMPTY;
        if (null != params) {
            param = HTMLParamUtils.assembleParams(params);
        }
        loadUrl(Constants.JS_PREFIX + methodName + Constants.LEFT_BRACKET + param + Constants.RIGHT_BRACKET);
    }

    public void requestJson(String methodName, String json) {
        if (TextUtils.isEmpty(methodName)) {
            throw new NullPointerException("request method param 'methodName' is null");
        }
        loadUrl(Constants.JS_PREFIX + methodName + Constants.LEFT_BRACKET + json + Constants.RIGHT_BRACKET);
    }

    @JavascriptInterface
    public void onResult(String content){
        if (null != resultInterface) {
            if (TextUtils.isEmpty(content) || content.equals("undefined")) {
                resultInterface.onResult();
            }else {
                resultInterface.onResult(content);
            }

        }

    }
}
