package org.example.config.configurator;

import lombok.SneakyThrows;
import org.example.annotation.InjectByType;
import org.example.annotation.InjectProperty;
import org.example.context.ApplicationContext;
import org.example.factory.ObjectFactory;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object o, ApplicationContext context) {
        for (Field declaredField : o.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(InjectByType.class)) {
                //настраивыаем field при помощи фабрики
                declaredField.setAccessible(true);

                //обращенеие к фабрике уже неправильно в этом месте, т.к. создается объект, который я вляется синглетоном
                //нафига его второй раз создавать, можно же взять из контекста
//                Object object = ObjectFactory.getInstance().createObject(declaredField.getType());
                Object object = context.getObject(declaredField.getType());
                declaredField.set(o, object);
            }
        }
    }
}
