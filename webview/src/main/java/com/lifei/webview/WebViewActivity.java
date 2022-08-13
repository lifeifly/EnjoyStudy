package com.lifei.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.lifei.webview.databinding.ActivityWebViewBinding;
import com.lifei.webview.utils.Constants;

public class WebViewActivity extends AppCompatActivity {
    private ActivityWebViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        String url = getIntent().getStringExtra(Constants.URL);
        String title = getIntent().getStringExtra(Constants.TITLE);
        boolean showActionBar = getIntent().getBooleanExtra(Constants.SHOW_ACTIONBAR, false);

        mBinding.toolBar.setSubtitle(title);
        setSupportActionBar(mBinding.toolBar);
        mBinding.toolBar.setVisibility(showActionBar ? View.VISIBLE : View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = WebViewFragment.newInstance(url, true);
        transaction.add(R.id.webview_fragment_container, fragment);
        transaction.commit();
    }

    public void updateTitle(String title) {
        mBinding.tvTitle.setText(title);
    }
}