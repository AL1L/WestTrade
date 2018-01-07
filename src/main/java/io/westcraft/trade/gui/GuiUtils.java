package io.westcraft.trade.gui;

import java.awt.*;

public class GuiUtils {

    /**
     * This will convert a Point to a slot number so that it can be used by bukkit
     *
     * @param point The point to be converted
     * @return a chest slot number
     */
    public static int pointToSlot(Point point) {
        return (point.y * 9) + point.x;
    }

    public static Point slotToPoint(int s) {
        int y = (int) Math.floor(s / 9);
        int x = s - (y * 9);
        return new Point(x, y);
    }
}
