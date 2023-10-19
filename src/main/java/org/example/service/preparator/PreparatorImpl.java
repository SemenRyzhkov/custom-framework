package org.example.service.preparator;

public class PreparatorImpl implements Preparator {
    @Override
    public void prepareForBeginWork() {
        System.out.println("Идёт подготовка к проекту");
    }
}
