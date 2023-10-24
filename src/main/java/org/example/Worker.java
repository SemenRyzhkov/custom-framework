package org.example;

import org.example.annotation.InjectByType;
import org.example.factory.ObjectFactory;
import org.example.service.announcer.Announcer;
import org.example.service.preparator.Preparator;

public class Worker {
    //todo добавить возможность кэшировать синглтоны, т.к синглтон это хорошо, но создавать его руками - плохо
    //поэтому нужно создать еще один уровень абстракции - Context, чтобы он взял эту responsibility на себя

    //пока это плохо, потому что это не инверсия контроля, это lookup
    //наш сервис завязан на инфраструктурный код, на его апи, это не даст возможность подменить этот апи завтра,
    //поэтому нужна инверсия контроля: "не вы будете дергать наши методы, чтобы мы настроили ваши объекты,
    //а мы сами создавая ваши объекты будем понимать, как их настроить и будем их настраивать

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
