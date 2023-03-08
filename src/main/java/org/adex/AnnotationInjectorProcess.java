package org.adex;

import java.lang.reflect.Field;
import java.util.Arrays;

class AnnotationInjectorProcess extends AbstractBeanProcess {

    @Override
    protected void process() {
        injectDependencies();
    }

    private static void injectDependencies() {
        BeanContainer.getBeanContainer()
                .forEach((key, bean) ->
                Arrays.stream(bean.getClass().getDeclaredFields())
                        .filter(BeanContainer::isABeanAndInjectionIsRequested)
                        .forEach(field -> setDependency(bean, field)));
    }

    private static void setDependency(Object bean, Field field) {
        try {
            boolean accessibility = field.canAccess(bean);
            field.setAccessible(true);
            field.set(bean, field.getType().getDeclaredConstructor().newInstance());
            field.setAccessible(accessibility);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
