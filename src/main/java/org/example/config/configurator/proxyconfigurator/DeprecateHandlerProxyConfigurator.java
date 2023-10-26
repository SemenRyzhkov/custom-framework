package org.example.config.configurator.proxyconfigurator;

import org.example.annotation.Deprecate;

import java.lang.reflect.Proxy;

public class DeprecateHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceOurObjectToProxy(Object o, Class implClass) {
        if (implClass.isAnnotationPresent(Deprecate.class)) {
            //сгенерит налету proxy класс и создаст сразу из него объект
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> {
                //вся магия по добавлению логики здесь
                System.out.println("Deprecated logic from PROXY class");
                return method.invoke(o);
            });
        } else return o;
    }
}
