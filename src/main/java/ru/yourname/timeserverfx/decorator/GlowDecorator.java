package ru.yourname.timeserverfx.decorator;

import javafx.scene.Node;
import javafx.scene.effect.Glow;

public class GlowDecorator extends BaseDecorator {

    public GlowDecorator(ClockDecorator decorator) {
        super(decorator);
    }

    @Override
    public void draw(Node node) {
        super.draw(node);
        node.setEffect(new Glow(0.8));
    }
}