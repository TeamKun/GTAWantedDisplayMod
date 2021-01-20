package net.kunmc.lab.gtawanteddisplaymod.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.kunmc.lab.gtawanteddisplaymod.GTAWantedDisplayMod;
import net.kunmc.lab.gtawanteddisplaymod.utils.RenderUtil;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.RenderedImage;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenderEvent
{
    public static Pattern pattern = Pattern.compile(".{1,5}");

    private static String genWantedStars()
    {
        final char noWantedStar = '\u2729';
        final char wantedStar = '\u272c';

        final double now = GTAWantedDisplayMod.instance.nowWanted;
        final int max = GTAWantedDisplayMod.instance.maxWanted;

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

    private void draw(String a)
    {


        FontRenderer fr = Minecraft.getInstance().fontRenderer;

        int width = Minecraft.getInstance().getMainWindow().getScaledWidth() / 2;
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight() / 2;
        System.out.println(GTAWantedDisplayMod.instance.maxWanted);
        System.out.println(GTAWantedDisplayMod.instance.nowWanted);
    }

    private static void draw(int x, int y, int index)
    {

        ResourceLocation starLocation = new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star.png");
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(starLocation);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        //AbstractGui.blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
        AbstractGui.blit(x, y, 0, index * 150, 32, 32, 150, 150);

    }

    //Some blit param namings , thank you Mekanism
    //blit(int x, int y, int textureX, int textureY, int width, int height);
    //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
    //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
    //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
    //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
    //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);
}
