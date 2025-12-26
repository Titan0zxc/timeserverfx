package ru.yourname.timeserverfx.decorator;

import javafx.scene.Node;

public abstract class BaseDecorator implements ClockDecorator {

    protected ClockDecorator decorator;

    public BaseDecorator(ClockDecorator decorator) {
        this.decorator = decorator;
    }

    @Override
    public void draw(Node node) {
        if (decorator != null) {
            decorator.draw(node);
        }
    }
}
