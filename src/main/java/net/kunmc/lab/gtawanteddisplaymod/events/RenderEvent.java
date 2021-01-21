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
    private static final ResourceLocation[] stars = {
            new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star_black.png"),
            new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star_gray.png"),
            new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star_white.png"),
    };

    private static int[] genWantedStars()
    {
        ArrayList<Integer> list = new ArrayList<>();
        final int now = GTAWantedDisplayMod.instance.nowWanted;
        final int max = Math.max(now, GTAWantedDisplayMod.instance.maxWanted);

        for (int i = 0; i < max; i++) {
            if (i < now)
                if (GTAWantedDisplayMod.instance.blinkFlag)
                    list.add(0);
                else
                    list.add(2);
            else
                list.add(1);
        }

        return ArrayUtils.toPrimitive(list.toArray(new Integer[0]));
    }

    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        if (GTAWantedDisplayMod.instance.maxWanted == 0 || GTAWantedDisplayMod.instance.nowWanted == 0)
            return;
        draw(genWantedStars());

    }

    private void draw(int... stars)
    {
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        int line = 1;
        int count = 1;
        for (int star : stars)
        {
            if (count > 10)
            {
                count = 1;
                line++;
            }
            draw(width - ((count++ * 16) + 2), line * 10, star);
        }

    }

    private static void draw(int x, int y, int index)
    {
        final int size = 16;
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(stars[index]);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        AbstractGui.blit(x, y, 0, 0, size, size, size, size);
    }

}
