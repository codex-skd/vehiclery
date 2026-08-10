package com.skd.vehiclery.util.network;

import com.skd.vehiclery.Vehiclery;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record VehicleryPacketPayload(Identifier id, byte[] bytes) implements CustomPacketPayload {
    public static final Type<VehicleryPacketPayload> TYPE = new Type<>(Vehiclery.rl("message_packet"));
    public static final StreamCodec<FriendlyByteBuf, VehicleryPacketPayload> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, VehicleryPacketPayload::id,
            ByteBufCodecs.BYTE_ARRAY, VehicleryPacketPayload::bytes,
            VehicleryPacketPayload::new
    );

    public FriendlyByteBuf buf() {
        return new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
