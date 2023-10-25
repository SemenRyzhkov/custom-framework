package org.example.service.preparator;

import org.example.annotation.InjectByType;
import org.example.annotation.PostConstruct;
import org.example.annotation.Singleton;
import org.example.service.announcer.checker.Checker;
@Singleton
public class PreparatorImpl implements Preparator {
    @InjectByType
    Checker checker;

    @PostConstruct
    public void init() {
        //problem нельзя настраивать объект, пока он не создался -> NPE
        //todo для настройки объекта реализовать second face constructor
        System.out.println(checker.getClass());
    }

    @Override
    public void prepareForBeginWork() {
        System.out.println("Идёт подготовка к проекту");
        checker.check();
    }
}
