package ru.yourname.timeserverfx.ui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import ru.yourname.timeserverfx.observer.IObserver;
import ru.yourname.timeserverfx.observer.Subject;

public class AnalogClock extends Pane implements IObserver {

    private static final double CENTER_X = 100;
    private static final double CENTER_Y = 100;
    private static final double RADIUS = 90;

    private final Line hourHand;
    private final Line minuteHand;
    private final Line secondHand;

    private final Rotate hourRotate = new Rotate(0, CENTER_X, CENTER_Y);
    private final Rotate minuteRotate = new Rotate(0, CENTER_X, CENTER_Y);
    private final Rotate secondRotate = new Rotate(0, CENTER_X, CENTER_Y);

    public AnalogClock(Subject subject) {
        super();

        // Циферблат
        Circle face = new Circle(CENTER_X, CENTER_Y, RADIUS, Color.WHITE);
        face.setStroke(Color.BLACK);
        face.setStrokeWidth(4);

        // Центральная точка
        Circle center = new Circle(CENTER_X, CENTER_Y, 5, Color.BLACK);

        // Метки часов (12, 3, 6, 9 + маленькие тики)
        Group ticks = new Group();
        for (int i = 0; i < 60; i++) {
            double angle = i * 6;
            double start = (i % 5 == 0) ? RADIUS - 15 : RADIUS - 8;
            double end = RADIUS;
            Line tick = new Line(
                    CENTER_X + Math.cos(Math.toRadians(angle - 90)) * start,
                    CENTER_Y + Math.sin(Math.toRadians(angle - 90)) * start,
                    CENTER_X + Math.cos(Math.toRadians(angle - 90)) * end,
                    CENTER_Y + Math.sin(Math.toRadians(angle - 90)) * end
            );
            tick.setStroke(Color.BLACK);
            tick.setStrokeWidth(i % 5 == 0 ? 3 : 1);
            ticks.getChildren().add(tick);
        }

        // Стрелки
        hourHand = createHand(50, Color.BLACK, 6);
        minuteHand = createHand(70, Color.DARKGRAY, 4);
        secondHand = createHand(80, Color.RED, 2);

        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);

        // Эффект свечения на секундную стрелку
        secondHand.setEffect(new Glow(0.7));

        // Сборка
        getChildren().addAll(face, ticks, center, hourHand, minuteHand, secondHand);

        setPrefSize(200, 200);
        setMaxSize(200, 200);

        subject.attach(this);

    }

    private Line createHand(double length, Color color, double width) {
        Line hand = new Line(CENTER_X, CENTER_Y, CENTER_X, CENTER_Y - length);
        hand.setStroke(color);
        hand.setStrokeWidth(width);
        hand.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        return hand;
    }

    @Override
    public void update(Subject subject) {
        Platform.runLater(() -> {
            int totalSeconds = subject.getState();

            // Секундная стрелка: 6° за секунду
            double secondAngle = (totalSeconds % 60) * 6.0;

            // Минутная стрелка: 6° за минуту + 0.1° за секунду
            double minuteAngle = (totalSeconds / 60.0) * 6.0;

            // Часовая стрелка: 30° за час + 0.5° за минуту
            double hourAngle = (totalSeconds / 3600.0) * 360.0 + (totalSeconds / 60.0) * 0.5;

            // В конструкторе AnalogClock, после ticks
            Text label12 = new Text(CENTER_X - 8, CENTER_Y - RADIUS + 25, "12");
            label12.setFont(Font.font(18));
            Text label3  = new Text(CENTER_X + RADIUS - 30, CENTER_Y + 8,  "3");
            label3.setFont(Font.font(18));
            Text label6  = new Text(CENTER_X - 8, CENTER_Y + RADIUS - 10, "6");
            label6.setFont(Font.font(18));
            Text label9  = new Text(CENTER_X - RADIUS + 10, CENTER_Y + 8,  "9");
            label9.setFont(Font.font(18));

            getChildren().addAll(label12, label3, label6, label9);

            secondRotate.setAngle(secondAngle);
            minuteRotate.setAngle(minuteAngle);
            hourRotate.setAngle(hourAngle);
        });
    }
}