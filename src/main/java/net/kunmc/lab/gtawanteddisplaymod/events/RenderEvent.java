package net.kunmc.lab.gtawanteddisplaymod.events;

import com.mojang.blaze3d.platform.GlStateManager;
import net.kunmc.lab.gtawanteddisplaymod.GTAWantedDisplayMod;
import net.kunmc.lab.gtawanteddisplaymod.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenderEvent
{
    public static Pattern pattern = Pattern.compile(".{1,5}");

    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        if (GTAWantedDisplayMod.instance.maxWanted == 0)
            return;

        FontRenderer fr = Minecraft.getInstance().fontRenderer;

        String display = genStars();
        int stringSize = fr.getStringWidth(StringUtils.split(display, ',')[0]);
        int width = Minecraft.getInstance().func_228018_at_().getWidth() / 2;
        int height = Minecraft.getInstance().func_228018_at_().getHeight() / 2;
        System.out.println("H:" + height);
        System.out.println("W:" + width);
        int PADDING = 10;

        GlStateManager.func_227740_m_();
        GlStateManager.func_227672_b_(30, 30, 0);
        AtomicInteger par = new AtomicInteger();
        Arrays.stream(StringUtils.split(display, ','))
                .forEachOrdered(s -> {
                    fr.drawStringWithShadow(s,
                            (width - stringSize - PADDING) ,
                            PADDING + par.get(),
                            RenderUtil.getColor(255, 255, 255, 0));
                    par.set(par.get() + 11);
                });
        GlStateManager.func_227672_b_(1, 1, 0);
    }

    private static String genStars()
    {
        final char wantedStar = '\u272c';
        final char noWantedStar = '\u2729';

        final double now = GTAWantedDisplayMod.instance.nowWanted;
        final int max = GTAWantedDisplayMod.instance.maxWanted;

        String nowS = StringUtils.repeat(wantedStar, (int) now);
        String maxS = StringUtils.repeat(noWantedStar, (int) (max - now));
        String completeStar = maxS + nowS;
        Matcher matcher = pattern.matcher(completeStar);

        StringBuilder xe = new StringBuilder();

        while(matcher.find())
            xe.append(matcher.group()).append(",");

        return xe.toString();
    }
}
