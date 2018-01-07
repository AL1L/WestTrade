package io.westcraft.trade.gui;

import java.awt.*;

public class GuiUtils {

    public static int pointToSlot(Point point) {
        return (point.y * 9) + point.x;
    }

    public static Point slotToPoint(int s) {
        int y = (int) Math.floor(s / 9);
        int x = s - (y * 9);
        return new Point(x, y);
    }
}
