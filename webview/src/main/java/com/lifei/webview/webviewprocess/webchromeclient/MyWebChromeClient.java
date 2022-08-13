package com.lifei.webview.webviewprocess.webchromeclient;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {
    private static final String TAG = "MyWebChromeClient";
    private WebChromeClientCallBack chromeClientCallBack;

    public MyWebChromeClient(WebChromeClientCallBack chromeClientCallBack) {
        this.chromeClientCallBack = chromeClientCallBack;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (chromeClientCallBack != null) {
            chromeClientCallBack.onGetTitle(title);
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(TAG,consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }
}
