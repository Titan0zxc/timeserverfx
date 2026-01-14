package ru.yourname.timeserverfx.decorator;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import ru.yourname.timeserverfx.observer.Subject;

public class ShadowDecorator extends BaseDecorator {

    private final java.util.function.Consumer<String> messageConsumer; // Для отправки сообщений в UI

    public ShadowDecorator(ClockDecorator decorator, java.util.function.Consumer<String> messageConsumer) {
        super(decorator);
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void draw(Node node) {
        super.draw(node);
        node.setEffect(new DropShadow());
    }

    // Вызывается externally (из observer/update), чтобы проверить минуту
    public void checkAndSendMessage(Subject subject) {
        int state = subject.getState();
        if (state % 60 == 0 && state > 0) {
            int minutesPassed = state / 60;
            int minutesToHour = 60 - (minutesPassed % 60); // Минут до следующего часа
            String message = "Decorator: Прошло " + minutesPassed + " минут. До часа осталось " + minutesToHour + " минут.";
            messageConsumer.accept(message);
        }
    }
}