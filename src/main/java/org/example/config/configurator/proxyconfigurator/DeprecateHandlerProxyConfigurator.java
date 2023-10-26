package org.example.config.configurator.proxyconfigurator;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.example.annotation.Deprecate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecateHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceOurObjectToProxy(Object o, Class implClass) {


        if (implClass.isAnnotationPresent(Deprecate.class)) {
            //возможность создавать прокси для классов без интерфейсов
            //при помощи CGLIB
            if (implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) ->
                        getInvocationLogic(o, args, method));
            }
            //сгенерит налету proxy класс и создаст сразу из него объект
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) ->
                    getInvocationLogic(o, args, method)
            );
        } else return o;
    }

    //вся магия по добавлению логики здесь
    private Object getInvocationLogic(Object o, Object[] args, Method method) throws IllegalAccessException, InvocationTargetException {
        System.out.println("Deprecated logic from PROXY class");
        return method.invoke(o, args);
    }
}
