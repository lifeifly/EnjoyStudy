package com.lifei.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.lifei.base.loadsir.ErrorCallback;
import com.lifei.base.loadsir.LoadingCallback;
import com.lifei.webview.databinding.WebviewFragmentBinding;
import com.lifei.webview.utils.Constants;
import com.lifei.webview.webviewprocess.webchromeclient.MyWebChromeClient;
import com.lifei.webview.webviewprocess.webchromeclient.WebChromeClientCallBack;
import com.lifei.webview.webviewprocess.websetting.MyWebSetting;
import com.lifei.webview.webviewprocess.webviewclient.MyWebViewClient;
import com.lifei.webview.webviewprocess.webviewclient.WebViewClientCallback;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

public class WebViewFragment extends Fragment
        implements WebViewClientCallback,
        OnRefreshListener,
        WebChromeClientCallBack {
    private static final String TAG = "WebViewFragment";
    private String mUrl;
    private boolean autoRefresh;
    private WebviewFragmentBinding mBinding;
    private LoadService mLoadService;
    private boolean isError = false;

    public static WebViewFragment newInstance(String url, boolean autoRefresh) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        bundle.putBoolean(Constants.AUTO_REFRESH, autoRefresh);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUrl = arguments.getString(Constants.URL);
            autoRefresh = arguments.getBoolean(Constants.AUTO_REFRESH, false);
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.webview_fragment, container, false);
        mBinding.webview.loadUrl(mUrl);
        mBinding.webview.registerWebClientCallBack(this);
        mBinding.webview.registerWebChromeClientCallBack(this);
        mLoadService = LoadSir.getDefault().register(mBinding.srl, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showCallback(LoadingCallback.class);
                mBinding.webview.reload();
            }
        });

        mBinding.srl.setOnRefreshListener(this);
        mBinding.srl.setEnableRefresh(autoRefresh);
        mBinding.srl.setEnableLoadMore(false);
        return mLoadService.getLoadLayout();
    }

    @Override
    public void onPageStarted(String url) {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void onPageFinished(String url) {
        if (isError) {
            //错误后也会回调finish方法,启用刷新
            mBinding.srl.setEnableRefresh(true);
            if (mLoadService != null) {
                mLoadService.showCallback(ErrorCallback.class);
            }
        } else {
            mBinding.srl.setEnableRefresh(autoRefresh);
            if (mLoadService != null) {
                mLoadService.showSuccess();
            }
        }

        mBinding.srl.finishRefresh();
        isError = false;
    }

    @Override
    public void onReceiveError() {
        Log.e(TAG, "onReceiveError");
        isError = true;
    }

    @Override
    public void onRefresh(@NotNull RefreshLayout refreshLayout) {
        mBinding.webview.reload();
    }

    @Override
    public void onGetTitle(String title) {
        if (getActivity() instanceof WebViewActivity) {
            ((WebViewActivity) getActivity()).updateTitle(title);
        }
    }
}
