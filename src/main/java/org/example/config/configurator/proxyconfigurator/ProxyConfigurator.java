package org.example.config.configurator.proxyconfigurator;

public interface ProxyConfigurator {
    Object replaceOurObjectToProxy(Object o, Class implClass);
}
