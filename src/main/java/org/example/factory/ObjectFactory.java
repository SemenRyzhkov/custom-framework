package org.example.factory;

import lombok.SneakyThrows;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.config.configurator.ObjectConfigurator;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    @SneakyThrows
    private ObjectFactory() {
        config = new JavaConfig("org.example", new HashMap<Class, Class>() {{
            put(Preparator.class, PreparatorImpl.class);
        }});
        //заполним список конфигураторов с помощью сканера, полученного из конфига
        for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        System.out.println(configurators);
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();

        //выполняем настройку с помощью всех конфигураторов
        //нет проблемы с тем, что объект проходит через цепочку конфигураторов даже если ему не нужна настройка, т.к
        //настройка проходит на этапе бутстрапа. да и поперфомансу она сильно не жрет
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t));
        return t;
    }
}
