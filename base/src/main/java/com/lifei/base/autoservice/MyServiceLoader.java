package com.lifei.base.autoservice;

import java.util.ServiceLoader;

public class MyServiceLoader {
    private MyServiceLoader() {
    }

    public static <S> S load(Class<S> service) {
        try {
            return ServiceLoader.load(service).iterator().next();
        } catch (Exception e) {
            return null;
        }
    }
}
