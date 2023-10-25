package org.example.factory;

import lombok.Setter;
import lombok.SneakyThrows;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.config.configurator.ObjectConfigurator;
import org.example.context.ApplicationContext;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectFactory {
    private static ObjectFactory ourInstance;
    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner()
                .getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        System.out.println(configurators);
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        T t = implClass.getDeclaredConstructor().newInstance();
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
        return t;
    }
}
