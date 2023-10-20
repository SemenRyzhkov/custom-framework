package org.example.config;

import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    private Reflections scanner;
    //Map - можно сказать сторонний конфиг файл, который будет считан в runtime
    //это дает:
    //централизованное место для создания всех объектов -
    //если надо менять имплементацию - не надо лезть в код
    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        this.scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    //
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(String.format("%s has 0 or more then 1 implementation, please update your config", ifc));
            }
            return classes.iterator().next();
        });
    }
}
