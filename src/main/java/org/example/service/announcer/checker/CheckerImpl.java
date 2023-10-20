package org.example.service.announcer.checker;

import org.example.service.announcer.annotation.InjectProperty;

public class CheckerImpl implements Checker {
@InjectProperty
    private String check;

    @Override
    public void check() {
        System.out.println(String.format("Выполняется проверка проекта на %s", check));
    }
}
