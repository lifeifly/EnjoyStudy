package com.lifei.enjoystudy.proxy;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class FButtonKnife {
    /**
     * 创建Activity动态代理对象
     * @param activity
     */
    public static void bind(Activity activity){
        //找到activity文件中的onClick注解、onLongClick注解
        findAnnotations(activity);
    }

    private static void findAnnotations(Activity activity) {
        Class c=activity.getClass();
        Method[] methods=c.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations=method.getAnnotations();
            for (Annotation annotation : annotations) {
                //获取当前注解类
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //获取注解类上的EventType注解
                if (annotationType.isAnnotationPresent(EventType.class)){
                    EventType eventType = annotationType.getAnnotation(EventType.class);
                    String listenerSetter = eventType.listenerSetter();
                    Class listenerType = eventType.listenerType();

                    try {
                        //获取参数方法
                        Method value=annotationType.getDeclaredMethod("value");
                        int[] ids= (int[]) value.invoke(annotation);
                        //创建动态代理对象
                        Object proxy=Proxy.newProxyInstance(activity.getClassLoader(), new Class[]{listenerType}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
                                return method.invoke(activity,args);
                            }
                        });
                        for (int id : ids) {
                            View view=activity.findViewById(id);
                            Method setMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setMethod.invoke(view,proxy);
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
