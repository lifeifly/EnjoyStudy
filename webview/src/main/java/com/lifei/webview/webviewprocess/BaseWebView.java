package com.lifei.webview.webviewprocess;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.lifei.webview.bean.JsParam;
import com.lifei.webview.webviewprocess.webchromeclient.MyWebChromeClient;
import com.lifei.webview.webviewprocess.webchromeclient.WebChromeClientCallBack;
import com.lifei.webview.webviewprocess.websetting.MyWebSetting;
import com.lifei.webview.webviewprocess.webviewclient.MyWebViewClient;
import com.lifei.webview.webviewprocess.webviewclient.WebViewClientCallback;

import java.util.HashMap;

public class BaseWebView extends WebView {
    private static final String TAG = "BaseWebView";

    public BaseWebView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        MyWebSetting.getInstance().setSettings(this);
        addJavascriptInterface(this, "xiangxuewebview");
    }

    public void registerWebChromeClientCallBack(WebChromeClientCallBack webChromeClientCallBack) {
        setWebChromeClient(new MyWebChromeClient(webChromeClientCallBack));
    }

    public void registerWebClientCallBack(WebViewClientCallback webViewClientCallback) {
        setWebViewClient(new MyWebViewClient(webViewClientCallback));
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParam) {
        Log.d(TAG, jsParam);
        if (!TextUtils.isEmpty(jsParam)) {
            JsParam jsParam1 = new Gson().fromJson(jsParam, JsParam.class);
            if (jsParam1 != null) {
                if ("showToast".equalsIgnoreCase(jsParam1.name)) {
                    Toast.makeText(getContext(), String.valueOf(new Gson().fromJson(jsParam1.param, HashMap.class).get("message")), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

