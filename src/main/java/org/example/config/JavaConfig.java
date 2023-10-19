package org.example.config;

import org.reflections.Reflections;

import java.util.Set;

public class JavaConfig implements Config {
    private Reflections scanner;

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    //вначале предполагаем что в classpath может находиться одна имплементация
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
        if (classes.size()!=1){
            throw new RuntimeException(String.format("%s has 0 or more then 1 implementation", ifc));
        }
        return classes.iterator().next();
    }
}
