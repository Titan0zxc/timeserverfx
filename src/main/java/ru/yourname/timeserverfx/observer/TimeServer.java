package ru.yourname.timeserverfx.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer implements Subject {

    private int timeState = 0;
    private final Timer timer;
    private final List<IObserver> observers = new ArrayList<>();

    public TimeServer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 1000, 1000);
    }

    private void tick() {
        timeState++;
        notifyAllObservers();
    }

    @Override
    public int getState() {
        return timeState;
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }
}
