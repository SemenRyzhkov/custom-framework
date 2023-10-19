package org.example;

import org.example.factory.ObjectFactory;
import org.example.service.announcer.Announcer;
import org.example.service.preparator.Preparator;

public class Worker {
    //Solution: create ObjectFactory
    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
    private Preparator preparator = ObjectFactory.getInstance().createObject(Preparator.class);

    public void start(Project project) {
        announcer.announce("Начинается работа над новым проектом");
        preparator.prepareForBeginWork();
        doWork(project);
        announcer.announce("Закончилась работа над проектом");
    }

    private void doWork(Project project) {
        System.out.println("I am do work and build project");
    }
}
