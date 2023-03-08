package org.adex;

import org.adex.annotations.Inject;

import java.lang.reflect.Field;
import java.util.*;

final class BeanContainer {

    /**
     * org.douhi.services.ProductServices : new Instance ProductServices
     */
    private static final Map<String, Object> BEANS;

    static {
        BEANS = new HashMap<>();
    }

    static Object getBeanByFullPath(String fullPath) {
        return Optional.ofNullable(BEANS.get(fullPath))
                .orElseThrow(() -> new BeanContainerException(BeanContainerException.BeanIoCExceptionReason.NOT_A_BEAN));
    }

    static Collection<String> getBeans() {
        return BEANS.keySet();
    }

    static void registerBean(Class<?> clazz) throws Exception {
        BEANS.put(clazz.getName(), clazz.getDeclaredConstructor().newInstance());
    }

    static Map<String, Object> getBeanContainer() {
        return Collections.unmodifiableMap(BEANS);
    }

    static boolean isABeanAndInjectionIsRequested(Field field) {
        return BEANS.containsKey(field.getType().getName()) && field.isAnnotationPresent(Inject.class);
    }

}