package org.example.service.announcer;

import org.example.factory.ObjectFactory;
import org.example.service.announcer.checker.Checker;

public class AnnouncerImpl implements Announcer {
    @Override
    public void announce(String message) {
        System.out.println(message);
    }
}
