package org.example.factory;

import lombok.SneakyThrows;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.service.preparator.NewPreparator;
import org.example.service.preparator.Preparator;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    //предполагается, что map строится как вариант из внешнего конфигурационного файла
    private Config config;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        config = new JavaConfig("org.example", new HashMap<Class, Class>() {{
            put(Preparator.class, NewPreparator.class);
        }});
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();
        //фабрика, прежде чем отдать объект, может настроить объект согласно нашим конвенциям
        //todo выполнить полезную настройку объекта
        return t;
    }
}
