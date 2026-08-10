package com.skd.vehiclery.neoforge.network;

import com.skd.vehiclery.util.network.VehicleryPacketPayload;
import com.skd.vehiclery.util.network.ClientPackets;
import com.skd.vehiclery.util.network.CommonPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeNetworking {
    public static final String PROTOCOL_VERSION = "0.5";
    public static final DirectionalPayloadHandler<VehicleryPacketPayload> HANDLER = new DirectionalPayloadHandler<>(
            NeoForgeNetworking::receiveClient, NeoForgeNetworking::receiveServer
    );

    public static void receiveClient(VehicleryPacketPayload payload, IPayloadContext ctx) {
        ClientPackets.CLIENTBOUND_HANDLERS.get(payload.id()).accept(Minecraft.getInstance(), payload.buf());
    }

    public static void receiveServer(VehicleryPacketPayload payload, IPayloadContext ctx) {
        var player = ctx.player();
        if (player instanceof ServerPlayer sPlayer) {
            CommonPackets.SERVERBOUND_HANDLERS.get(payload.id()).accept(player.getServer(), sPlayer, payload.buf());
        }
    }
}
