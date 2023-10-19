package org.example;

public class Worker {

    public void start(Project project) {
        //todo сообщить о начале работы над проектом
        //todo выполнить подготовку
        doWork(project);
        //todo сообщить об окончании работы над проектом
    }

    private void doWork(Project project) {
        System.out.println("I am do work and build project");
    }
}
