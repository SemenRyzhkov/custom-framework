package org.example;

import org.example.context.ApplicationContext;
import org.example.runner.ApplicationRunner;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        //теперь практически аналог спринга - получили контекст и из него достали наш бин
        ApplicationContext context = ApplicationRunner.run("org.example", new HashMap<Class, Class>() {{
            put(Preparator.class, PreparatorImpl.class);
        }});
        Worker worker = context.getObject(Worker.class);
        worker.start(new Project());
    }
}
