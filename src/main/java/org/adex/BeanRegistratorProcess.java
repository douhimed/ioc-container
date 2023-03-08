package org.adex;

import org.adex.annotations.Bean;

import java.io.File;
import java.util.Objects;

class BeanRegistratorProcess extends AbstractBeanProcess {

    public static final String JAVA_CLASS_PATH = "java.class.path";
    public static final String SEMI_COLON = ";";
    public static final String CLASS_PATH_PROPERTY;
    public static final String POINT = ".";

    static {
        CLASS_PATH_PROPERTY = System.getProperty(JAVA_CLASS_PATH).split(SEMI_COLON)[0];
    }

    @Override
    protected void process() {
        initBeanRegistration();
    }

    static void initBeanRegistration() {
        registerBeans(new File(CLASS_PATH_PROPERTY));
    }

    static void registerBeans(File folder) {
        if (Objects.nonNull(folder.listFiles())) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    registerBeans(fileEntry);
                } else {
                    registerBeanIfEligible(fileEntry);
                }
            }
        }
    }

    private static void registerBeanIfEligible(File classFile) {
        try {
            final String classPthWithoutExt = getClassPathFormatted(classFile);
            final Class<?> clazz = Class.forName(classPthWithoutExt);
            if (clazz.isAnnotationPresent(Bean.class))
                BeanContainer.registerBean(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getClassPathFormatted(File classFile) {
        final String classPthWithClassExt = classFile
                .getPath()
                .substring(CLASS_PATH_PROPERTY.length() + 1)
                .replace(File.separator, POINT);
        return classPthWithClassExt.substring(0, classPthWithClassExt.length() - 6);
    }

}
