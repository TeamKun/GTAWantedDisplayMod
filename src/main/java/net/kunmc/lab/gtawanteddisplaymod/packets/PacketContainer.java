package net.kunmc.lab.gtawanteddisplaymod.packets;

import net.kunmc.lab.gtawanteddisplaymod.GTAWantedDisplayMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class PacketContainer
{
    private final int maxWanted;
    private final int nowWanted;
    private final int flags;

    public PacketContainer(int nowWanted, int maxWanted, int flags)
    {
        this.nowWanted = nowWanted;
        this.maxWanted = maxWanted;
        this.flags = flags;
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
            if (messageWorker.length != 3)
                throw new IllegalArgumentException("Malformed packet received.");

            int now = Integer.parseInt(messageWorker[0]);
            int max = Integer.parseInt(messageWorker[1]);
            int flags = Integer.parseInt(messageWorker[2]);

            return new PacketContainer(now, max, flags);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new PacketContainer(0, 0, 0);
        }
    }

    public static void handle(PacketContainer message, Supplier<NetworkEvent.Context> ctx)
    {
        GTAWantedDisplayMod.instance.nowWanted = message.nowWanted;
        GTAWantedDisplayMod.instance.maxWanted = message.maxWanted;
        GTAWantedDisplayMod.instance.flags = message.flags;
        ctx.get().setPacketHandled(true);
    }

}
