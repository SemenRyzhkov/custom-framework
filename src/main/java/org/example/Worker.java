package org.example;

import org.example.service.announcer.Announcer;
import org.example.service.announcer.AnnouncerImpl;
import org.example.service.preparator.Preparator;
import org.example.service.preparator.PreparatorImpl;

public class Worker {
    //Problem: создание, выбор правильной имплементации и настройка сервисов - не наша responsibility
    //получается наш класс работает, как фабрика. Это плохо
    //todo кто будет решать какая имплементация?
    private Announcer announcer = new AnnouncerImpl();
    private Preparator preparator = new PreparatorImpl();

    public void start(Project project) {
        //для соблюдения single responsibility - поручаем работу об оповещении и подготовке другим сервисам
        announcer.announce("Начинается работа над новым проектом");
        preparator.prepareForBeginWork();
        //наша single responsibility
        doWork(project);
        announcer.announce("Закончилась работа над проектом");
    }

    private void doWork(Project project) {
        System.out.println("I am do work and build project");
    }
}
