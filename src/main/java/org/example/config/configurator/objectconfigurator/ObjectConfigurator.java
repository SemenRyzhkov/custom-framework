package org.example.config.configurator.objectconfigurator;

import org.example.context.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object o, ApplicationContext context);
}
