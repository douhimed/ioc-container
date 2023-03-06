package org.adex;

import java.lang.reflect.Field;
import java.util.Map;

class BeanInjectorProcess extends AbstractBeanProcess {

    @Override
    protected void process() {
        injectDependencies();
    }

    private static void injectDependencies() {
        final Map<String, Object> beans = BeanContainer.getBeanContainer();
        beans.forEach((key, bean) -> {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if(BeanContainer.isABeanAndInjectionIsRequiested(field)) {
                    setDependency(bean, field, beans);
                }
            }
        });
    }

    private static void setDependency(Object bean, Field field, Map<String, Object> beans) {
        try {
            boolean accessibility = field.canAccess(bean);
            field.setAccessible(true);
            field.set(bean, BeanContainer.getBeanByFullPath(field.getType().getName()));
            field.setAccessible(accessibility);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
