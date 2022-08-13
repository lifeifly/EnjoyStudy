package com.lifei.webview.webviewprocess.webviewclient;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    private static final String TAG="MyWebViewClient";
    private WebViewClientCallback webViewClientCallback;

    public MyWebViewClient(WebViewClientCallback webViewClientCallback) {
        this.webViewClientCallback = webViewClientCallback;
    }


    /**
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (webViewClientCallback!=null){
            webViewClientCallback.onPageStarted(url);
        }else {
            Log.e(TAG,"webviewCallBack is null");
        }
    }

    /**
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (webViewClientCallback!=null){
            webViewClientCallback.onPageFinished(url);
        }else {
            Log.e(TAG,"webviewCallBack is null");
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (webViewClientCallback!=null){
            webViewClientCallback.onReceiveError();
        }else {
            Log.e(TAG,"webviewCallBack is null");
        }
    }
}
