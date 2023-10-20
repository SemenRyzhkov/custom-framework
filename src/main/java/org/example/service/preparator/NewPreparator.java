package org.example.service.preparator;

public class NewPreparator implements Preparator {
    @Override
    public void prepareForBeginWork() {
        System.out.println("Новая улучшенная подготовка к проекту");
    }
}
