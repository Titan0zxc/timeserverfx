package ru.yourname.timeserverfx.decorator;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;

public class ShadowDecorator extends BaseDecorator {

    public ShadowDecorator(ClockDecorator decorator) {
        super(decorator);
    }

    @Override
    public void draw(Node node) {
        super.draw(node);
        node.setEffect(new DropShadow());
    }
}
