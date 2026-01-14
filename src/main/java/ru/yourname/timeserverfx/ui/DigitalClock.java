package ru.yourname.timeserverfx.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import ru.yourname.timeserverfx.observer.IObserver;
import ru.yourname.timeserverfx.observer.Subject;

public class DigitalClock extends Label implements IObserver {

    public DigitalClock(Subject subject) {
        setText("00:00");
        subject.attach(this);
    }

    @Override
    public void update(Subject subject) {
        Platform.runLater(() -> {
            int totalSeconds = subject.getState();
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            setText(String.format("%02d:%02d", minutes, seconds));
        });
    }
}