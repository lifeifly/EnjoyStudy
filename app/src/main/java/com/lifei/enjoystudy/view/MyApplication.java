package com.lifei.enjoystudy.view;

import com.kingja.loadsir.core.LoadSir;
import com.lifei.base.BaseApplication;
import com.lifei.base.loadsir.CustomCallback;
import com.lifei.base.loadsir.EmptyCallback;
import com.lifei.base.loadsir.ErrorCallback;
import com.lifei.base.loadsir.LoadingCallback;
import com.lifei.base.loadsir.TimeoutCallback;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }
}
