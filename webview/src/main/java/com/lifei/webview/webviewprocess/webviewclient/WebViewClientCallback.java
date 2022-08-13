package com.lifei.webview.webviewprocess.webviewclient;

public interface WebViewClientCallback {
    void onPageStarted(String url);
    void onPageFinished(String url);
    void onReceiveError();
}
