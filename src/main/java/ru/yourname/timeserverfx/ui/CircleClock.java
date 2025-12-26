package ru.yourname.timeserverfx.ui;

import javafx.application.Platform;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.yourname.timeserverfx.observer.IObserver;
import ru.yourname.timeserverfx.observer.Subject;

public class CircleClock extends Circle implements IObserver {

    public CircleClock(Subject subject) {
        super(30, Color.CORNFLOWERBLUE);
        setEffect(new Glow(0.8)); // FX-эффект
        subject.attach(this);
    }

    @Override
    public void update(Subject subject) {
        Platform.runLater(() ->
                setRotate(subject.getState() * 6)
        );
    }
}
