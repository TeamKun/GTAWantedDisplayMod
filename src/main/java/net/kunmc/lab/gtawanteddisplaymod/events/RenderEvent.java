package net.kunmc.lab.gtawanteddisplaymod.events;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kunmc.lab.gtawanteddisplaymod.GTAWantedDisplayMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RenderEvent
{
    public static Pattern pattern = Pattern.compile(".{1,5}");

    private static final ResourceLocation[] stars = {
            new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star_black.png"),
            new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star_gray.png"),
            new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star_white.png"),
    };
    private static final ResourceLocation star = new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star.png");

    private static int[] genWantedStars()
    {
        ArrayList<Integer> list = new ArrayList<>();
        final double now = GTAWantedDisplayMod.instance.nowWanted;
        final int max = Math.max((int) now, GTAWantedDisplayMod.instance.maxWanted);

        for (int i = 0; i < max; i++) {
            if (i < now)
                list.add(0);
            else if (now - i < 0.6)
                list.add(1);
            else
                list.add(2);
        }
        return ArrayUtils.toPrimitive(list.toArray(new Integer[0]));

        /*
        // final char noWantedStar = '\u2729';
        // final char wantedStar = '\u272c';

        String nowS = StringUtils.repeat(wantedStar, (int) Math.floor(now));
        String maxS = StringUtils.repeat(noWantedStar, (int) (max - Math.floor(now)));
        String completeStar = maxS + nowS;

        StringBuilder xb = new StringBuilder();

        boolean isPassed = false;
        for (String a: completeStar.split(""))
            if (a.equals(String.valueOf(wantedStar)))
            {
                if (!isPassed && GTAWantedDisplayMod.instance.accessFlag && String.valueOf(now).endsWith(".5"))
                {
                    xb.append("B");
                    isPassed = true;
                    continue;
                }
                xb.append("W");
            }
            else
                xb.append("D");

        return xb.toString();
        */
    }


    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        if (GTAWantedDisplayMod.instance.maxWanted == 0 || GTAWantedDisplayMod.instance.nowWanted == 0)
            return;
        draw(genWantedStars());

        //draw(GTAWantedDisplayMod.instance.maxWanted, (int) GTAWantedDisplayMod.instance.nowWanted, 0);

    }

    private void draw(int... stars)
    {
        int width = Minecraft.getInstance().getMainWindow().getWidth() / 2;

        int line = 1;
        int count = 1;
        int buffer = 0;
        for(int star: stars)
                draw(line * 10, width - ((count++ * 16) + 2), star);
    }

    private static void draw(int x, int y, int index)
    {
        final int size = 16;
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(stars[index]);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        //AbstractGui.blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
        // AbstractGui.blit(x, y, 0, 0, size, size, size * 3, size);
        AbstractGui.blit(x, y, 0, 0, size, size, size, size);
        //AbstractGui.blit(0, 0, 0, 0, 0, 32, 32, 32, 32);
    }

    //Some blit param namings , thank you Mekanism
    //blit(int x, int y, int textureX, int textureY, int width, int height);
    //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
    //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
    //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
    //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
    //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);

}
