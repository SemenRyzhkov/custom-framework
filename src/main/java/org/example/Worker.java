package org.example;

import org.example.annotation.Deprecate;
import org.example.annotation.InjectByType;
import org.example.factory.ObjectFactory;
import org.example.service.announcer.Announcer;
import org.example.service.preparator.Preparator;

//Todo пока это работать не будет, т.к в нашем прокси-конфигураторе нет возможности обрабатывать классы, не импл-ие интерфейс
@Deprecate
public class Worker {
    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Preparator preparator;

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
