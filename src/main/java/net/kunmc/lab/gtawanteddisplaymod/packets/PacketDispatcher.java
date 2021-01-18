package net.kunmc.lab.gtawanteddisplaymod.packets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.commons.lang3.StringUtils;

public class PacketDispatcher
{
    private final SimpleChannel channel;

    public PacketDispatcher(String name)
    {
        String[] channelAndPath = StringUtils.split(name, ":");
        if (channelAndPath.length != 2)
            throw new IllegalArgumentException("Malformed channel or path: " + name);

        this.channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(channelAndPath[0], channelAndPath[1]))
                .clientAcceptedVersions(NetworkRegistry.ACCEPTVANILLA::equals)
                .serverAcceptedVersions(NetworkRegistry.ACCEPTVANILLA::equals)
                .networkProtocolVersion(() -> "1")
                .simpleChannel();

        channel.registerMessage(0, PacketContainer.class,
                PacketContainer::encode,
                PacketContainer::decode,
                PacketContainer::handle);
    }
}
