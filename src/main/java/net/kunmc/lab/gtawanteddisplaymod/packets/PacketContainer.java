package net.kunmc.lab.gtawanteddisplaymod.packets;

import net.kunmc.lab.gtawanteddisplaymod.GTAWantedDisplayMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.Supplier;

public class PacketContainer
{
    private final int maxWanted;
    private final int nowWanted;
    private final int flags;
    private final int[] flagvalues;

    public PacketContainer(int nowWanted, int maxWanted, int flags, int[] flagvalues)
    {
        this.nowWanted = nowWanted;
        this.maxWanted = maxWanted;
        this.flags = flags;
        this.flagvalues = flagvalues;
    }

    public static void encode(PacketContainer message, PacketBuffer buffer)
    {
        buffer.writeByte(0);
        buffer.writeInt(message.nowWanted);
        buffer.writeString("|");
        buffer.writeInt(message.maxWanted);
        buffer.writeString("|");
        buffer.writeInt(message.flags);
        buffer.writeByte(0);
    }

    public static PacketContainer decode(PacketBuffer buffer)
    {
        try
        {
            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.getBytes(0, bytes);
            bytes = ArrayUtils.remove(bytes, 0);
            String messageString = StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);
            String[] messageWorker = StringUtils.split(messageString, "|");
            if (messageWorker.length < 3)
                throw new IllegalArgumentException("Malformed packet received.");

            int now = Integer.parseInt(messageWorker[0]);
            int max = Integer.parseInt(messageWorker[1]);
            int flags = Integer.parseInt(messageWorker[2]);
            ArrayList<Integer> v = new ArrayList<>();
            if (GTAWantedDisplayMod.Flag.BLINK.check(flags))
                v.add(Integer.parseInt(messageWorker[3]));

            return new PacketContainer(now, max, flags, ArrayUtils.toPrimitive(v.toArray(new Integer[0])));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new PacketContainer(0, 0, 0, new int[]{0});
        }
    }

    public static void handle(PacketContainer message, Supplier<NetworkEvent.Context> ctx)
    {
        GTAWantedDisplayMod.instance.nowWanted = message.nowWanted;
        GTAWantedDisplayMod.instance.maxWanted = message.maxWanted;
        GTAWantedDisplayMod.instance.flags = message.flags;
        if (GTAWantedDisplayMod.Flag.BLINK.check(message.flags))
            GTAWantedDisplayMod.instance.timer = message.flagvalues[0] * 5;
        ctx.get().setPacketHandled(true);
    }

}
