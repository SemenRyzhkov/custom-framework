package org.example.config;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> ifc);
}
