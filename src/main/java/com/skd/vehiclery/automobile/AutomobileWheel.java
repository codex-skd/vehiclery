package com.skd.vehiclery.automobile;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.util.DefaultRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

public record AutomobileWheel(
        boolean empty,
        float size,
        float grip,
        WheelModel model
) implements AutomobileComponent<AutomobileWheel> {
    public static final Identifier ID = Vehiclery.rl("wheel");

    public static final ResourceKey<Registry<AutomobileWheel>> REGISTRY = ResourceKey.createRegistryKey(Vehiclery.rl("automobile_wheel"));
    public static final DefaultRegistrar<AutomobileWheel> BOOTSTRAP = new DefaultRegistrar<>(REGISTRY);

    public static final Codec<AutomobileWheel> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.BOOL.optionalFieldOf("_empty", false).forGetter(AutomobileWheel::empty),
            Codec.FLOAT.fieldOf("size").forGetter(AutomobileWheel::size),
            Codec.FLOAT.fieldOf("grip").forGetter(AutomobileWheel::grip),
            WheelModel.CODEC.fieldOf("display").forGetter(AutomobileWheel::model)
    ).apply(inst, AutomobileWheel::new));
    public static final Codec<ResourceKey<AutomobileWheel>> CODEC = ResourceKey.codec(REGISTRY);

    public static final StreamCodec<RegistryFriendlyByteBuf, AutomobileWheel> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, AutomobileWheel::empty,
            ByteBufCodecs.FLOAT, AutomobileWheel::size,
            ByteBufCodecs.FLOAT, AutomobileWheel::grip,
            WheelModel.STREAM_CODEC, AutomobileWheel::model,
            AutomobileWheel::new
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<AutomobileWheel>> STREAM_CODEC = ByteBufCodecs.holder(REGISTRY, DIRECT_STREAM_CODEC);
    public static final EntityDataSerializer<Holder<AutomobileWheel>> SERIALIZER = EntityDataSerializer.forValueType(STREAM_CODEC);

    public static final AutomobileWheel EMPTY = new AutomobileWheel(true, 0.01f, 0.01f, new WheelModel(1, 1, Identifier.parse("empty"), Vehiclery.rl("empty")));

    public static final ResourceKey<AutomobileWheel> EMPTY_KEY = BOOTSTRAP.register(Vehiclery.rl("empty"), EMPTY);

    public static AutomobileWheel of(float size, float grip, WheelModel model) {
        return new AutomobileWheel(false, size, grip, model);
    }

    public static final ResourceKey<AutomobileWheel> STANDARD = BOOTSTRAP.register(Vehiclery.rl("standard"),
            of(0.6f, 0.5f, new WheelModel(3, 3, Vehiclery.rl("textures/entity/automobile/wheel/standard.png"), Vehiclery.rl("wheel/standard")))
    );

    public static final ResourceKey<AutomobileWheel> OFF_ROAD = BOOTSTRAP.register(Vehiclery.rl("off_road"),
            of(1.1f, 0.8f, new WheelModel(8.4f, 5, Vehiclery.rl("textures/entity/automobile/wheel/off_road.png"), Vehiclery.rl("wheel/off_road")))
    );

    public static final ResourceKey<AutomobileWheel> STEEL = BOOTSTRAP.register(Vehiclery.rl("steel"),
            of(0.69f, 0.4f, new WheelModel(3.625f, 3, Vehiclery.rl("textures/entity/automobile/wheel/steel.png"), Vehiclery.rl("wheel/steel")))
    );

    public static final ResourceKey<AutomobileWheel> TRACTOR = BOOTSTRAP.register(Vehiclery.rl("tractor"),
            of(1.05f, 0.69f, new WheelModel(3.625f, 3, Vehiclery.rl("textures/entity/automobile/wheel/tractor.png"), Vehiclery.rl("wheel/tractor")))
    );

    public static final ResourceKey<AutomobileWheel> CARRIAGE = carriage("carriage", 0.2f);
    public static final ResourceKey<AutomobileWheel> PLATED = carriage("plated", 0.33f);
    public static final ResourceKey<AutomobileWheel> STREET = carriage("street", 0.5f);
    public static final ResourceKey<AutomobileWheel> GILDED = carriage("gilded", 0.45f);
    public static final ResourceKey<AutomobileWheel> BEJEWELED = carriage("bejeweled", 0.475f);

    private static ResourceKey<AutomobileWheel> carriage(String name, float grip) {
        return BOOTSTRAP.register(Vehiclery.rl(name),
                of(1.05f, grip, new WheelModel(5, 2, Vehiclery.rl("textures/entity/automobile/wheel/"+name+".png"), Vehiclery.rl("wheel/carriage"))));
    }

    public static final DisplayStat<AutomobileWheel> STAT_SIZE = new DisplayStat<>("size", AutomobileWheel::size);
    public static final DisplayStat<AutomobileWheel> STAT_GRIP = new DisplayStat<>("grip", AutomobileWheel::grip);

    @Override
    public boolean isEmpty() {
        return empty();
    }

    @Override
    public Identifier containerId() {
        return ID;
    }

    @Override
    public void forEachStat(Consumer<DisplayStat<AutomobileWheel>> action) {
        action.accept(STAT_SIZE);
        action.accept(STAT_GRIP);
    }

    @Override
    public Identifier getId() {
        return Vehiclery.rl("invalid");
    }

    public static String getTranslationKey(Identifier id) {
        return "wheel."+id.getNamespace()+"."+id.getPath();
    }

    public record WheelModel(
        float radius,
        float width,
        Identifier texture,
        Identifier modelId
    ) {
        public static final Codec<WheelModel> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.fieldOf("radius").forGetter(WheelModel::radius),
                Codec.FLOAT.fieldOf("width").forGetter(WheelModel::width),
                Identifier.CODEC.fieldOf("texture").forGetter(WheelModel::texture),
                Identifier.CODEC.fieldOf("model").forGetter(WheelModel::modelId)
        ).apply(inst, WheelModel::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WheelModel> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.FLOAT, WheelModel::radius,
                ByteBufCodecs.FLOAT, WheelModel::width,
                Identifier.STREAM_CODEC, WheelModel::texture,
                Identifier.STREAM_CODEC, WheelModel::modelId,
                WheelModel::new
        );
    }
}
