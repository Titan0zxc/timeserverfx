package ru.yourname.timeserverfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.yourname.timeserverfx.decorator.ClockDecorator;
import ru.yourname.timeserverfx.decorator.GlowDecorator;
import ru.yourname.timeserverfx.decorator.ShadowDecorator;
import ru.yourname.timeserverfx.observer.IObserver;
import ru.yourname.timeserverfx.observer.Subject;
import ru.yourname.timeserverfx.observer.TimeServer;
import ru.yourname.timeserverfx.ui.AnalogClock;
import ru.yourname.timeserverfx.ui.CircleClock;
import ru.yourname.timeserverfx.ui.DigitalClock;
import ru.yourname.timeserverfx.ui.TextClock;

public class MainApp extends Application {

    private TextArea messageLog; // Блок для сообщений
    private ShadowDecorator shadowDecorator; // Для сообщений

    @Override
    public void start(Stage stage) {

        TimeServer server = new TimeServer();

        TextClock textClock = new TextClock(server);
        AnalogClock analogClock = new AnalogClock(server);
        DigitalClock digitalClock = new DigitalClock(server);

        // Цепочка декораторов для CircleClock (Shadow + Glow)
        messageLog = new TextArea(); // Блок сообщений
        messageLog.setEditable(false);
        messageLog.setPrefHeight(100);

        shadowDecorator = new ShadowDecorator(null, message -> {
            javafx.application.Platform.runLater(() -> messageLog.appendText(message + "\n"));
        });
        ClockDecorator decorator = new GlowDecorator(shadowDecorator);
        decorator.draw(analogClock);

        // Добавляем логи от observers (пример: лог при обновлении)
        wrapObserverWithLogging(textClock, server, "TextClock обновлён: ");
        wrapObserverWithLogging(analogClock, server, "CircleClock обновлён: ");
        wrapObserverWithLogging(digitalClock, server, "DigitalClock обновлён: ");

        // Панель управления
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button resetButton = new Button("Reset");
        Label statusLabel = new Label("Состояние: Неактивен");

        startButton.setOnAction(e -> {
            server.startServer();
            statusLabel.setText("Состояние: Активен");
        });
        stopButton.setOnAction(e -> {
            server.stopServer();
            statusLabel.setText("Состояние: Неактивен");
        });
        resetButton.setOnAction(e -> server.resetState());

        HBox controlPanel = new HBox(10, startButton, stopButton, resetButton, statusLabel);
        controlPanel.setAlignment(Pos.CENTER);

        // Корень
        VBox root = new VBox(20, controlPanel, textClock, analogClock, digitalClock, new Label("Сообщения:"), messageLog);
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 400, 500));
        stage.setTitle("Time Server (Observer + Decorator)");
        stage.show();
    }

    // Обертка для логирования обновлений observers
    private void wrapObserverWithLogging(IObserver observer, Subject subject, String prefix) {
        observer.update(subject); // Инит
        // Переопределяем update для лога (но сохраняем оригинал)
        // Поскольку interface, используем lambda-обертку
        subject.detach(observer);
        subject.attach(new IObserver() {
            @Override
            public void update(Subject sub) {
                observer.update(sub);
                javafx.application.Platform.runLater(() -> messageLog.appendText(prefix + sub.getState() + " сек\n"));
                // Проверяем для decorator сообщений
                if (shadowDecorator != null) {
                    shadowDecorator.checkAndSendMessage(sub);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}