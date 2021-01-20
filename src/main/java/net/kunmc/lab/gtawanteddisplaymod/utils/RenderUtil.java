package net.kunmc.lab.gtawanteddisplaymod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class RenderUtil
{

    public static int getColor(int red, int green, int blue)
    {
        return getColor(255, red, green, blue);
    }

    public static int getColor(int alpha, int red, int green, int blue)
    {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static void drawText(String text, Style... styles)
    {
        FontRenderer fr = Minecraft.getInstance().fontRenderer;
        Rect client = new Rect(
                Minecraft.getInstance().getMainWindow().getWidth() / 2,
                Minecraft.getInstance().getMainWindow().getHeight() / 2);

        Rect rect = new Rect(0, 0, fr.getStringWidth(text), fr.getWordWrappedHeight(text, Integer.MAX_VALUE));
        int color = getColor(255, 255, 255);
        Margin margin = new Margin();
        boolean withShadow = false;

        Style horizontal = null;
        Style vertical = null;

        for (Style style : styles)
        {
            switch (style)
            {
                case X:
                    rect.x = style.getValue();
                    break;
                case Y:
                    rect.y = style.getValue();
                    break;

                case HORIZONTAL_LEFT:
                    horizontal = Style.HORIZONTAL_LEFT;
                    break;
                case HORIZONTAL_CENTER:
                    horizontal = Style.HORIZONTAL_CENTER;
                    break;
                case HORIZONTAL_RIGHT:
                    horizontal = Style.HORIZONTAL_RIGHT;
                    break;

                case VERTICAL_TOP:
                    vertical = Style.VERTICAL_TOP;
                    break;
                case VERTICAL_CENTER:
                    vertical = Style.VERTICAL_CENTER;
                    break;
                case VERTICAL_BOTTOM:
                    vertical = Style.VERTICAL_BOTTOM;
                    break;

                case COLOR:
                    color = style.getValue();
                    break;

                case MARGIN:
                    margin.setAll(style.getValue());
                    break;
                case MARGIN_TOP:
                    margin.setTop(style.getValue());
                    break;
                case MARGIN_RIGHT:
                    margin.setRight(style.getValue());
                    break;
                case MARGIN_BOTTOM:
                    margin.setBottom(style.getValue());
                    break;
                case MARGIN_LEFT:
                    margin.setLeft(style.getValue());
                    break;

                case WITH_SHADOW:
                    withShadow = true;
                    break;
            }
        }

        if (horizontal == Style.HORIZONTAL_LEFT) {
            rect.x = margin.left;
        } else if (horizontal == Style.HORIZONTAL_CENTER) {
            rect.x = (client.width - rect.width) / 2;
        } else if (horizontal == Style.HORIZONTAL_RIGHT) {
            rect.x = client.width - rect.width - margin.right;
        } else {
            rect.x -= Math.min(rect.x - margin.left, 0);
            rect.x -= Math.max(rect.x + rect.width + margin.right - client.width, 0);
        }

        if (vertical == Style.VERTICAL_TOP) {

        }

        if (withShadow)
            fr.drawStringWithShadow(text, rect.x, rect.y, color);
        else
            fr.drawString(text, rect.x, rect.y, color);

    }

    public enum Style
    {
        X,
        Y,

        HORIZONTAL_LEFT,
        HORIZONTAL_CENTER,
        HORIZONTAL_RIGHT,

        VERTICAL_TOP,
        VERTICAL_CENTER,
        VERTICAL_BOTTOM,

        COLOR,

        MARGIN,
        MARGIN_TOP,
        MARGIN_RIGHT,
        MARGIN_BOTTOM,
        MARGIN_LEFT,

        WITH_SHADOW;

        private int value;

        public int getValue()
        {
            return value;
        }

        public Style setValue(int value)
        {
            this.value = value;
            return this;
        }
    }

    private static class Rect
    {
        public int x;
        public int y;
        public int width;
        public int height;

        public Rect(int width, int height) {
            this(0, 0, width, height);
        }

        public Rect(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

    }

    private static class Margin
    {
        public int top;
        public int right;
        public int bottom;
        public int left;

        public Margin()
        {
            this(0);
        }

        public Margin(int value)
        {
            this(value, value, value, value);
        }

        public Margin(int top_bottom, int right_left)
        {
            this(top_bottom, right_left, top_bottom, right_left);
        }

        public Margin(int top, int right, int bottom, int left)
        {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }

        public void setTop(int top)
        {
            this.top = top;
        }

        public void setRight(int right)
        {
            this.right = right;
        }

        public void setBottom(int bottom)
        {
            this.bottom = bottom;
        }

        public void setLeft(int left)
        {
            this.left = left;
        }

        public void setAll(int value)
        {
            setAll(value, value, value, value);
        }

        public void setAll(int top, int right, int bottom, int left)
        {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }

    }

}
