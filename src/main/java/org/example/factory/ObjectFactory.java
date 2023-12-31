package org.example.factory;

import lombok.SneakyThrows;
import org.example.annotation.PostConstruct;
import org.example.config.configurator.objectconfigurator.ObjectConfigurator;
import org.example.config.configurator.proxyconfigurator.ProxyConfigurator;
import org.example.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private static ObjectFactory ourInstance;
    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner()
                .getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }

        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner()
                .getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        //по сути получили картину того, как объект создается в спринге
        T t = create(implClass);
        configure(t);
        runInitMethod(implClass, t);
        t = wrapWithProxyIfNeeded(implClass, t);
        return t;
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = (T) proxyConfigurator.replaceOurObjectToProxy(t, implClass);
        }
        return t;
    }

    private <T> void runInitMethod(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
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
