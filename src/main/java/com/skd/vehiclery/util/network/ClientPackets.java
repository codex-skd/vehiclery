package com.skd.vehiclery.util.network;

import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.attachment.FrontAttachmentType;
import com.skd.vehiclery.automobile.attachment.RearAttachmentType;
import com.skd.vehiclery.automobile.attachment.rear.BannerPostRearAttachment;
import com.skd.vehiclery.automobile.attachment.rear.ExtendableRearAttachment;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.platform.Platform;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public enum ClientPackets {;
    public static final Map<Identifier, BiConsumer<Minecraft, FriendlyByteBuf>> CLIENTBOUND_HANDLERS = new HashMap<>();

    public static void registerReceiver(Identifier rl, BiConsumer<Minecraft, FriendlyByteBuf> run) {
        CLIENTBOUND_HANDLERS.put(rl, run);
    }

    public static void sendServerboundAutomobileSyncPacket(AutomobileEntity entity) {
        var buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(entity.getId());
        entity.writeSyncStateData(buf);
        Platform.get().clientSendPacket(Vehiclery.rl("sync_automobile_data"), buf);
    }

    public static void requestSyncAutomobileComponentsPacket(AutomobileEntity entity) {
        var buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(entity.getId());
        Platform.get().clientSendPacket(Vehiclery.rl("request_sync_automobile_components"), buf);
    }

    public static void initClient() {
        ClientPackets.registerReceiver(Vehiclery.rl("sync_automobile_data"), (client, buf) -> {
            var dup = new FriendlyByteBuf(buf.copy());
            int entityId = dup.readInt();
            client.execute(() -> {
                if (client.player.level().getEntity(entityId) instanceof AutomobileEntity automobile) {
                    automobile.readSyncStateData(dup);
                }
            });
        });
        ClientPackets.registerReceiver(Vehiclery.rl("sync_automobile_attachments"), (client, buf) -> {
            int entityId = buf.readInt();
            var rearAtt = RearAttachmentType.REGISTRY.getOrDefault(Identifier.tryParse(buf.readUtf()));
            var frontAtt = FrontAttachmentType.REGISTRY.getOrDefault(Identifier.tryParse(buf.readUtf()));
            client.execute(() -> {
                if (client.player.level().getEntity(entityId) instanceof AutomobileEntity automobile) {
                    automobile.setRearAttachment(rearAtt);
                    automobile.setFrontAttachment(frontAtt);
                }
            });
        });
        ClientPackets.registerReceiver(Vehiclery.rl("update_banner_post"), (client, buf) -> {
            var rbuf = new RegistryFriendlyByteBuf(buf, client.level.registryAccess());

            int entityId = buf.readInt();
            final DyeColor baseColor;
            final BannerPatternLayers layers;
            if (buf.readBoolean()) {
                baseColor = DyeColor.STREAM_CODEC.decode(buf);
                layers = BannerPatternLayers.STREAM_CODEC.decode(rbuf);
            } else {
                baseColor = null;
                layers = null;
            }
            client.execute(() -> {
                if (client.player.level().getEntity(entityId) instanceof AutomobileEntity automobile &&
                        automobile.getRearAttachment() instanceof BannerPostRearAttachment bannerPost) {
                    bannerPost.setBanner(baseColor, layers);
                }
            });
        });
        ClientPackets.registerReceiver(Vehiclery.rl("update_extendable_attachment"), (client, buf) -> {
            int entityId = buf.readInt();
            boolean extended = buf.readBoolean();
            client.execute(() -> {
                if (client.player.level().getEntity(entityId) instanceof AutomobileEntity automobile &&
                        automobile.getRearAttachment() instanceof ExtendableRearAttachment att) {
                    att.setExtended(extended);
                }
            });
        });
    }
}
