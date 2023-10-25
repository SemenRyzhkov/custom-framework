package org.example.runner;

import org.example.config.JavaConfig;
import org.example.context.ApplicationContext;
import org.example.factory.ObjectFactory;

import java.util.Map;

public class ApplicationRunner {

    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2IImplClass) {
        JavaConfig javaConfig = new JavaConfig(packageToScan, ifc2IImplClass);
        ApplicationContext applicationContext = new ApplicationContext(javaConfig);
        ObjectFactory objectFactory = new ObjectFactory(applicationContext);
        applicationContext.setObjectFactory(objectFactory);
        return applicationContext;
    }
}
