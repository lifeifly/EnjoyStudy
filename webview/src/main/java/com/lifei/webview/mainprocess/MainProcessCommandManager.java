package com.lifei.webview.mainprocess;

public class MainProcessCommandManager {
    public static volatile MainProcessCommandManager sInstance;

    public static MainProcessCommandManager getInstance() {
        if (sInstance == null) {
            synchronized (MainProcessCommandManager.class) {
                if (sInstance == null) {
                    sInstance = new MainProcessCommandManager();
                }
            }
        }
        return sInstance;
    }

    private MainProcessCommandManager(){}
}
