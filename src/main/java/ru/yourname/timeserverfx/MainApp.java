package ru.yourname.timeserverfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.yourname.timeserverfx.decorator.ClockDecorator;
import ru.yourname.timeserverfx.decorator.ShadowDecorator;
import ru.yourname.timeserverfx.observer.TimeServer;
import ru.yourname.timeserverfx.ui.CircleClock;
import ru.yourname.timeserverfx.ui.TextClock;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        TimeServer server = new TimeServer();

        TextClock textClock = new TextClock(server);
        CircleClock circleClock = new CircleClock(server);

        ClockDecorator decorator = new ShadowDecorator(null);
        decorator.draw(circleClock);

        VBox root = new VBox(20, textClock, circleClock);
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Time Server (Observer + Decorator)");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
