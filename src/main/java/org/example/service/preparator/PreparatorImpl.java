package org.example.service.preparator;

import org.example.annotation.Singlton;
import org.example.factory.ObjectFactory;
import org.example.service.announcer.checker.Checker;

@Singlton
public class PreparatorImpl implements Preparator {
    Checker checker = ObjectFactory.getInstance().createObject(Checker.class);

    @Override
    public void prepareForBeginWork() {
        System.out.println("Идёт подготовка к проекту");
        checker.check();
    }
}
