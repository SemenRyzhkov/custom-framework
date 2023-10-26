package org.example.factory;

import lombok.Setter;
import lombok.SneakyThrows;
import org.example.annotation.Deprecate;
import org.example.annotation.PostConstruct;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.config.configurator.ObjectConfigurator;
import org.example.context.ApplicationContext;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
        T t = create(implClass);
        configure(t);
        invokeInit(implClass, t);
        //todo create new configurator typr for this logic
        if (implClass.isAnnotationPresent(Deprecate.class)) {
            //сгенерит налету proxy класс и создаст сразу из него объект
            return (T) Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> {
                //вся магия по добавлению логики здесь
                System.out.println("Deprecated logic from PROXY class");
                return method.invoke(t);
            });
        }
        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        T t = implClass.getDeclaredConstructor().newInstance();
        return t;
    }
}
