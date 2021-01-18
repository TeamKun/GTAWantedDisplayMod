package net.kunmc.lab.gtawanteddisplaymod.utils;

public class RenderUtils
{
    public static int getColor(int alpha, int red, int green, int blue)
    {
        return alpha << 24 | red     << 16 | green << 8 | blue;
    }
}
