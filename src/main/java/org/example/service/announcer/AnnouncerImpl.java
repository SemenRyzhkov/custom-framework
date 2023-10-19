package org.example.service.announcer;

public class AnnouncerImpl implements Announcer {
    @Override
    public void announce(String message) {
        System.out.println(message);
    }
}
