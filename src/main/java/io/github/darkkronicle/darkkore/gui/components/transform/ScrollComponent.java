package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

public class ScrollComponent extends OffsetComponent {

    private double scrollStart = 0;
    private double scrollEnd = 0;
    private int scrollVal = 0;

    private long lastScroll = 0;
    private int scrollDuration = 200;

    private final boolean vertical;

    public ScrollComponent(Component component, int width, int height, boolean vertical) {
        super(component, width, height);
        this.vertical = vertical;
        this.selectable = true;
    }

    @Override
    public int getXOffset() {
        if (!vertical) {
            return -scrollVal;
        }
        return 0;
    }

    @Override
    public int getYOffset() {
        if (vertical) {
            return -scrollVal;
        }
        return 0;
    }

    @Override
    public void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        renderBounds = new PositionedRectangle(x, y, width, height);
        updateScroll();
        ScissorsStack.getInstance().push(renderBounds);
        ScissorsStack.getInstance().applyStack();
        super.render(matrices, renderBounds, x, y, mouseX, mouseY);
        ScissorsStack.getInstance().pop();
        ScissorsStack.getInstance().applyStack();
    }

    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (super.mouseScrolled(x, y, mouseX, mouseY, amount)) {
            return true;
        }
        scroll(amount * 30);
        return true;
    }

    public void updateScroll() {
        long time = Util.getMeasuringTimeMs();
        scrollDuration = 300;
        scrollVal = (int) (scrollStart + (
                (scrollEnd - scrollStart) * (1 - (EasingMethod.Method.QUART.apply(
                        1 - ((float) (time - lastScroll)) / scrollDuration
                ))
        )));
        int total = component.getBoundingBox().height() - height;
        if (scrollVal > total) {
            scrollStart = total;
            scrollEnd = total;
            lastScroll = 0;
            scrollVal = total;
        }

        if (scrollVal <= 0) {
            scrollStart = 0;
            scrollEnd = 0;
            lastScroll = 0;
            scrollVal = 0;
        }
    }

    public void scroll(double amount) {
        scrollStart = scrollVal;
        scrollEnd = amount * 3 + scrollEnd;
        lastScroll = Util.getMeasuringTimeMs();
    }

}
