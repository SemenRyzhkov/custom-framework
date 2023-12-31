package org.example.service.announcer.checker;

import org.example.annotation.Deprecate;
import org.example.annotation.InjectProperty;

@Deprecate
public class CheckerImpl implements Checker {
    @InjectProperty("linter")
    private String check;

    @Override
    public void check() {
        System.out.println(String.format("Выполняется проверка проекта на %s", check));
    }
}
