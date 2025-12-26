package ru.yourname.timeserverfx.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import ru.yourname.timeserverfx.observer.IObserver;
import ru.yourname.timeserverfx.observer.Subject;

public class TextClock extends Label implements IObserver {

    public TextClock(Subject subject) {
        setText("Прошло: 0 сек");
        subject.attach(this);
    }

    @Override
    public void update(Subject subject) {
        Platform.runLater(() ->
                setText("Прошло: " + subject.getState() + " сек")
        );
    }
}
