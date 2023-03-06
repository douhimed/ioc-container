package org.adex;

import java.util.Collection;

public interface ApplicationContext {

    Object getBean(Class clazz);

    Collection<String> getBeans();

}
