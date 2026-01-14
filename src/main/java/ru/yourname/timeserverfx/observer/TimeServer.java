package ru.yourname.timeserverfx.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer implements Subject {

    private int timeState = 0;
    private Timer timer;
    private final List<IObserver> observers = new ArrayList<>();
    private boolean isRunning = false;

    public TimeServer() {
        timer = new Timer();
    }

    public void startServer() {
        if (!isRunning) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            }, 1000, 1000);
            isRunning = true;
        }
    }

    public void stopServer() {
        if (isRunning) {
            timer.cancel();
            timer = new Timer(); // Пересоздаём для повторного запуска
            isRunning = false;
        }
    }

    public void resetState() {
        timeState = 0;
        notifyAllObservers();
    }

    private void tick() {
        timeState++;
        notifyAllObservers();
    }

    @Override
    public int getState() {
        return timeState;
    }

    public void setState(int state) {
        this.timeState = state;
        notifyAllObservers();
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

    public boolean isRunning() {
        return isRunning;
    }
}