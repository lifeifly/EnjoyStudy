package com.lifei.compiler;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 允许此注解处理器处理的注解，指定该注解处理器可以处理哪些注解
 * @return
 */
@SupportedAnnotationTypes({"com.lifei.annotation.MyClass"})
public class MyClassProcesser extends AbstractProcessor {
    /**
     * javac调用该方法
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager=processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE,"=====================sa");
        return false;
    }

    /**
     * 允许此注解处理器处理的注解，指定该注解处理器可以处理哪些注解
     * @return
     */
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
//    }
}