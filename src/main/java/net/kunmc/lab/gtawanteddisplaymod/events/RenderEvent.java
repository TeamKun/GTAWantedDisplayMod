package net.kunmc.lab.gtawanteddisplaymod.events;

import net.kunmc.lab.gtawanteddisplaymod.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent
{

    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        FontRenderer fr = Minecraft.getInstance().fontRenderer;
        fr.drawStringWithShadow("Test!!!", 20, 20, RenderUtil.getColor(255, 0, 255, 0));
    }

}
