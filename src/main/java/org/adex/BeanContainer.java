package org.adex;

import org.adex.ioc.BeanContainerException.BeanIoCExceptionReason;
import org.adex.ioc.annotations.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

final class BeanContainer {

    private static final Map<String, Object> BEANS;

    static {
        BEANS = new HashMap<>();
    }

    static Object getBeanByFullPath(String fullPath) {
        return Optional.ofNullable(BEANS.get(fullPath))
                .orElseThrow(() -> new BeanContainerException(BeanIoCExceptionReason.NOT_A_BEAN));
    }

    static Collection<String> getBeans() {
        return BEANS.keySet();
    }

    static void registerBean(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BEANS.put(clazz.getName(), clazz.getDeclaredConstructor().newInstance());
    }

    static Map<String, Object> getBeanContainer() {
        return Collections.unmodifiableMap(BEANS);
    }

    static boolean isABeanAndInjectionIsRequiested(Field field) {
        return BEANS.containsKey(field.getType().getName()) && field.isAnnotationPresent(Inject.class);
    }

}
