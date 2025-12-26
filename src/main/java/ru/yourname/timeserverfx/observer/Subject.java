package ru.yourname.timeserverfx.observer;

public interface Subject {
    void attach(IObserver observer);
    void detach(IObserver observer);
    void notifyAllObservers();
    int getState();
}
