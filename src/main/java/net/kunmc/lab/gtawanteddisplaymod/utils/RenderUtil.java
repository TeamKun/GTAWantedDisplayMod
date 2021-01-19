package net.kunmc.lab.gtawanteddisplaymod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderUtil
{

    enum Style
    {
        X,
        Y,

        HORIZONTAL_TOP,
        HORIZONTAL_CENTER,
        HORIZONTAL_BOTTOM,

        VERTICAL_TOP,
        VERTICAL_CENTER,
        VERTICAL_BOTTOM,

        MARGIN,
        MARGIN_TOP,
        MARGIN_BOTTOM,
        MARGIN_LEFT,
        MARGIN_RIGHT,

        WITH_SHADOW;

        private int value;
        public Style setValue(int value) {
            this.value = value;
            return this;
        }

        public int getValue() {
            return value;
        }
    }

    private static class Margin {
        public int top;
        public int right;
        public int bottom;
        public int left;

        public Margin() {
            this(0);
        }

        public Margin(int all) {
            this(all, all, all, all);
        }

        public Margin(int top_bottom, int right_left) {
            this(top_bottom, right_left, top_bottom, right_left);
        }

        public Margin(int top, int right_left, int bottom) {
            this(top, right_left, bottom, right_left);
        }

        public Margin(int top, int right, int bottom, int left) {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }

        public void setAll(int all) {

        }

    }

    public static final int NO_FLAG = 0x00;
    public static final int VERTICAL_CENTER = 0x01;
    public static final int VERTICAL_TOP = 0x02;
    public static final int VERTICAL_BOTTOM = 0x04;
    public static final int HORIZONTAL_CENTER = 0xf0;

    public static int getColor(int red, int green, int blue)
    {
        return getColor(255, red, green, blue);
    }

    public static int getColor(int alpha, int red, int green, int blue)
    {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static void drawText(String text, int x, int y) {
        FontRenderer fr = Minecraft.getInstance().fontRenderer;

        fr.drawStringWithShadow(text, x, y, getColor(255, 255, 255));
    }

    public static void drawText(String text, Style... styles) {
        FontRenderer fr = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().func_228018_at_().getWidth() / 2;
        int height = Minecraft.getInstance().func_228018_at_().getHeight() / 2;

        int x = 0, y = 0;
        Margin margin = new Margin();
        boolean withShadow = false;

        for (Style style : styles) {
            switch (style) {
                case X:
                    x = style.getValue();
                    break;
                case Y:
                    y = style.getValue();
                    break;

                case HORIZONTAL_TOP:
                    x = 0;
                    break;
                case HORIZONTAL_CENTER:
                    x = width / 2;
                    break;
                case HORIZONTAL_BOTTOM:
                    x = width;
                    break;

                case VERTICAL_TOP:
                    y = 0;
                    break;
                case VERTICAL_CENTER:
                    y = height / 2;
                    break;
                case VERTICAL_BOTTOM:
                    y = height;
                    break;

                case MARGIN:

                case WITH_SHADOW:
                    withShadow = true;
                    break;
            }
        }

    }

}
