package net.kunmc.lab.gtawanteddisplaymod;

import net.kunmc.lab.gtawanteddisplaymod.events.RenderEvent;
import net.kunmc.lab.gtawanteddisplaymod.packets.PacketDispatcher;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(value = GTAWantedDisplayMod.MOD_ID)
public class GTAWantedDisplayMod
{
    public static final String MOD_ID = "gtawanteddisplaymod";
    public static GTAWantedDisplayMod instance;

    public int maxWanted;
    public int nowWanted;
    public int flags;
    public int timer;

    public boolean blinkFlag;

    private final PacketDispatcher packetDispatcher;

    public GTAWantedDisplayMod()
    {
        instance = this;

        maxWanted = 0;
        nowWanted = 0;
        flags = 0x00;

        timer = 0;

        packetDispatcher = new PacketDispatcher("gta:changewanted");


        MinecraftForge.EVENT_BUS.register(new RenderEvent());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent e)
    {
        if (!Flag.BLINK.check(flags) || timer == 0) {
            blinkFlag = false;
            return;
        }

        blinkFlag = Math.sin(System.currentTimeMillis() / timer) > 0;
    }

    public enum Flag {
        NONE(0x00),
        BLINK(0x01);

        private final int value;

        Flag(int value) {
            this.value = value;
        }

        public boolean check(int value) {
            return (this.value & value) == this.value;
        }

        public static int encode(Flag... flags)
        {
            int flag = 0;
            for(Flag f: flags)
                flag |= f.value;
            return flag;
        }
    }

}
