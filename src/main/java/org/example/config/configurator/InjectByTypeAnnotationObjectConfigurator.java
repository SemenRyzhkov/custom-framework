package org.example.config.configurator;

import lombok.SneakyThrows;
import org.example.annotation.InjectByType;
import org.example.annotation.InjectProperty;
import org.example.factory.ObjectFactory;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object o) {
        for (Field declaredField : o.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(InjectByType.class)) {
                //настраивыаем field при помощи фабрики
                declaredField.setAccessible(true);
                //здесь нет ничего страшно в обращении к фабрике, т.к это инфрачтруктурный класс
                //и отвечает за injection
                Object object = ObjectFactory.getInstance().createObject(declaredField.getType());
                declaredField.set(o, object);
            }
        }
    }
}
