package org.example.factory;

import lombok.Setter;
import lombok.SneakyThrows;
import org.example.annotation.PostConstruct;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.config.configurator.ObjectConfigurator;
import org.example.context.ApplicationContext;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        //где должны находиться инвокерв для инит методов?
        //ответ: оставим фабрике, т.к ее респонсибилити настраивать метод(вызывая конструктор)
        //инит метод - это по сути вторичный конструктор
        //если же выносить в один из конфигураторов(как в спринге), то нужно будет, чтобы он вызывался последним -
        //приходим к ситуации, когда становится важным ордеринг, а мы этого не хотим
        invokeInit(implClass, t);
        //todo добавить возможность клиентам менять поведение методов
        //add proxy pattern
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
