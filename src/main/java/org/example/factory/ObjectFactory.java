package org.example.factory;

import lombok.SneakyThrows;
import org.example.config.Config;
import org.example.config.JavaConfig;
import org.example.service.announcer.annotation.InjectProperty;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;

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

        //Добавили аннотацию InjectProperty(аналог @Value спринга) и написали настройку для нее
        //сломан open-closed принцип, т.к если мы захоти добавить новую настройку, придется писать новый if
        //это произошло потому что мы добавили еще один responsibility нашей фабрике - настройка объекта
        //todo создать ObjectConfigurator
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
            Map<String, String> propertiesMap = lines.map(line -> line.split("="))
                    .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
            if (Objects.nonNull(annotation)) {
                String propValue = annotation.value().isEmpty()
                        ? propertiesMap.get(field.getName())
                        : propertiesMap.get(annotation.value());
                field.setAccessible(true);
                field.set(t, propValue);
            }
        }

        return t;
    }
}
