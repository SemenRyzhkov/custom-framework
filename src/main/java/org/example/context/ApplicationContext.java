package org.example.context;

import lombok.Getter;
import lombok.Setter;
import org.example.annotation.Singleton;
import org.example.config.Config;
import org.example.factory.ObjectFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    @Setter
    private ObjectFactory objectFactory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
        //чтобы не писать такой код, нам нужно создать раннер(аналог спринового ApplicationRunner)
        ObjectFactory objectFactory1 = new ObjectFactory(this);
    }

    public <T> T getObject(Class<T> type) {
        //получение, если синглтон есть
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        //создание
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = objectFactory.createObject(implClass);

        //кэширование
        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
        return t;
    }
}
