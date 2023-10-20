package org.example.factory;

import lombok.SneakyThrows;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.config.configurator.ObjectConfigurator;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.util.HashMap;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;
    private ObjectConfigurator objectConfigurator;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        config = new JavaConfig("org.example", new HashMap<Class, Class>() {{
            put(Preparator.class, PreparatorImpl.class);
        }});
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();

        //Создали конфигуратор, но пока ничего не работает, т.к наш фрэймворк не знает какой именно конфигуратор испольовать
        //todo создать список всех конфигураторов, чтобы каждый из них мог настроить наш объект(chain of responsibility)
        objectConfigurator.configure(t);
        return t;
    }
}
