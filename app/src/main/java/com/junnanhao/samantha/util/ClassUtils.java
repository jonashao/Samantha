package com.junnanhao.samantha.util;

public final class ClassUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> castClass(Class<?> aClass) {
        return (Class<T>) aClass;
    }

}
