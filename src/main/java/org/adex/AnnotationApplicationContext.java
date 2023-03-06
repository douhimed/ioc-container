package org.adex;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class AnnotationApplicationContext implements ApplicationContext {

    private static AnnotationApplicationContext annotationApplicationContext;

    static {
        final AbstractBeanProcess registrator = new BeanRegistratorProcess();
        registrator.setNextProcessor(new BeanInjectorProcess());
        registrator.initProcessing();
    }

    @Override
    public Object getBean(Class clazz) {
        return BeanContainer.getBeanByFullPath(clazz.getName());
    }

    @Override
    public Collection<String> getBeans() {
        return Collections.unmodifiableCollection(BeanContainer.getBeans());
    }

    public static ApplicationContext getContext() {
        if (Objects.isNull(annotationApplicationContext))
            annotationApplicationContext = new AnnotationApplicationContext();
        return annotationApplicationContext;
    }

}
