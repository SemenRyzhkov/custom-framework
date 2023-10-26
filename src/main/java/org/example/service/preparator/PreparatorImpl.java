package org.example.service.preparator;

import org.example.annotation.Deprecate;
import org.example.annotation.InjectByType;
import org.example.annotation.PostConstruct;
import org.example.annotation.Singleton;
import org.example.service.announcer.checker.Checker;
@Singleton
public class PreparatorImpl implements Preparator {
    @InjectByType
    Checker checker;

    @PostConstruct
    //second face constructor
    public void init() {
        System.out.println(checker.getClass());
    }

    @Override
    public void prepareForBeginWork() {
        System.out.println("Идёт подготовка к проекту");
        checker.check();
    }
}
