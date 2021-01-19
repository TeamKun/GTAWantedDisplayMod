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
    private final double nowWanted;

    public PacketContainer(int maxWanted, double nowWanted)
    {
        this.maxWanted = maxWanted;
        this.nowWanted = nowWanted;
    }

    public static void encode(PacketContainer messages, PacketBuffer buffer)
    { //パケット規則に合わせて書き込む
        buffer.writeByte(0);
        buffer.writeInt(messages.maxWanted);
        buffer.writeString("|");
        buffer.writeDouble(messages.nowWanted);
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
            if (messageWorker.length != 2)
                throw new IllegalArgumentException("Malformed packet received.");

            int max = Integer.parseInt(messageWorker[0]);
            double now = Double.parseDouble(messageWorker[1]);
            return new PacketContainer(max, now);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new PacketContainer(0, 0);
        }
    }

    public static void handle(PacketContainer messager, Supplier<NetworkEvent.Context> ctx)
    {
        GTAWantedDisplayMod.instance.maxWanted = messager.maxWanted;
        GTAWantedDisplayMod.instance.nowWanted = messager.nowWanted;
        ctx.get().setPacketHandled(true);
    }

}
