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
        Matcher matcher = pattern.matcher(completeStar);

        StringBuilder xe = new StringBuilder();

        while (matcher.find())
            xe.append(matcher.group()).append(",");

        StringBuilder xb = new StringBuilder();

        boolean isPassed = false;
        for (String a : xe.toString().split(""))
            if (a.equals(String.valueOf(wantedStar)))
            {
                if (!isPassed && GTAWantedDisplayMod.instance.accessFlag && String.valueOf(now).endsWith(".5"))
                {
                    xb.append(ChatFormatting.BLACK).append(a);
                    isPassed = true;
                    continue;
                }
                xb.append(ChatFormatting.WHITE).append(a);
            }
            else
                xb.append(ChatFormatting.DARK_GRAY).append(ChatFormatting.BOLD).append(a);

        return xb.toString();
    }

    private final ResourceLocation starLocation = new ResourceLocation(GTAWantedDisplayMod.MOD_ID, "textures/gui/star.png");

    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        if (GTAWantedDisplayMod.instance.maxWanted == 0 || GTAWantedDisplayMod.instance.nowWanted == 0)
            return;

        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(starLocation);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        AbstractGui.blit(0, 0, 0, 0, 0, 32, 32, 32, 32);

        FontRenderer fr = Minecraft.getInstance().fontRenderer;

        String display = genWantedStars();
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth() / 2;
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight() / 2;
        System.out.println("H:" + height);
        System.out.println("W:" + width);
        int PADDING = 10;

        float size = 1f;
        RenderSystem.pushMatrix();
        RenderSystem.scalef(size, size, size);
        int stringSize = fr.getStringWidth(StringUtils.split(display, ',')[0]);
        float mSize = (float) Math.pow(size, -1);
        AtomicInteger par = new AtomicInteger();
        Arrays.stream(StringUtils.split(display, ','))
                .forEachOrdered(s -> {
                    RenderUtil.drawText(s,
                            RenderUtil.Style.HORIZONTAL_RIGHT,
                            RenderUtil.Style.Y.setValue(20 + par.get()),
                            RenderUtil.Style.MARGIN.setValue(20),
                            RenderUtil.Style.COLOR.setValue(RenderUtil.getColor(255, 0, 0)),
                            RenderUtil.Style.WITH_SHADOW);
                    par.set(par.get() + 11);
                });
        RenderSystem.popMatrix();
    }
}
