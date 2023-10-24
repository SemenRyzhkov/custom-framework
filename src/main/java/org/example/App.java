package org.example;

import org.example.factory.ObjectFactory;

public class App {
    public static void main(String[] args) {
        //когда появилась инверсия контроля, не нужно больше создавать объекты через new
        //Worker worker = new Worker();
        //Здесь тоже нет ничего страшного в обращении к фабрике, т.к здесь просто стартуем приложение и нет никакой бизнес-логики
        Worker worker = ObjectFactory.getInstance().createObject(Worker.class);
        worker.start(new Project());
    }
}
