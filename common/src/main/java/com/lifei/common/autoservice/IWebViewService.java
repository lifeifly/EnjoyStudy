package com.lifei.common.autoservice;

import android.content.Context;
import android.webkit.WebViewFragment;

import androidx.fragment.app.Fragment;

public interface IWebViewService {
    void startWebViewActivity(Context context, String url, String title,boolean isShowActionBar);
    Fragment getWebViewFragment(String url,boolean autoRefresh);
    void startDemoHtml(Context context);
}
