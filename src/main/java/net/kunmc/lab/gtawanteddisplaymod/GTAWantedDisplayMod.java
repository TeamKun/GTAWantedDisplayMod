package net.kunmc.lab.gtawanteddisplaymod;

import net.kunmc.lab.gtawanteddisplaymod.events.RenderEvent;
import net.kunmc.lab.gtawanteddisplaymod.packets.PacketDispatcher;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(value = GTAWantedDisplayMod.MOD_ID)
public class GTAWantedDisplayMod
{
    public static final String MOD_ID = "gtawanteddisplaymod";
    public static GTAWantedDisplayMod instance;

    public int maxWanted;
    public double nowWanted;

    private PacketDispatcher packetDispatcher;

    public GTAWantedDisplayMod()
    {
        instance = this;

        maxWanted = 0;
        nowWanted = 0.0;

        packetDispatcher = new PacketDispatcher("gta:changewanted");

        MinecraftForge.EVENT_BUS.register(new RenderEvent());
    }

}
