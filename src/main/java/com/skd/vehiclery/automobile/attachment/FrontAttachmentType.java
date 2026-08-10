package com.skd.vehiclery.automobile.attachment;

import com.mojang.serialization.Codec;
import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.AutomobileComponent;
import com.skd.vehiclery.automobile.DisplayStat;
import com.skd.vehiclery.automobile.attachment.front.AutopilotFrontAttachment;
import com.skd.vehiclery.automobile.attachment.front.CropHarvesterFrontAttachment;
import com.skd.vehiclery.automobile.attachment.front.EmptyFrontAttachment;
import com.skd.vehiclery.automobile.attachment.front.FrontAttachment;
import com.skd.vehiclery.automobile.attachment.front.GrassCutterFrontAttachment;
import com.skd.vehiclery.automobile.attachment.front.MobControllerFrontAttachment;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.util.SimpleMapContentRegistry;
import net.minecraft.resources.Identifier;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public record FrontAttachmentType<T extends FrontAttachment>(
        Identifier id, BiFunction<FrontAttachmentType<T>, AutomobileEntity, T> constructor, FrontAttachmentModel model
) implements AutomobileComponent<FrontAttachmentType<?>> {
    public static final Identifier ID = Vehiclery.rl("front_attachment");
    public static final SimpleMapContentRegistry<FrontAttachmentType<?>> REGISTRY = new SimpleMapContentRegistry<>();
    public static final Codec<FrontAttachmentType<?>> CODEC = REGISTRY.codec();

    public static final FrontAttachmentType<EmptyFrontAttachment> EMPTY = register(new FrontAttachmentType<>(
            Vehiclery.rl("empty"), EmptyFrontAttachment::new, new FrontAttachmentModel(Identifier.parse("empty"), Vehiclery.rl("empty"), 1)
    ));

    public static final FrontAttachmentType<MobControllerFrontAttachment> MOB_CONTROLLER = register(new FrontAttachmentType<>(
            Vehiclery.rl("mob_controller"), MobControllerFrontAttachment::new,
            new FrontAttachmentModel(Vehiclery.rl("textures/entity/automobile/front_attachment/mob_controller.png"), Vehiclery.rl("front_attachment/mob_controller"), 1.7f)
    ));

    public static final FrontAttachmentType<AutopilotFrontAttachment> AUTOPILOT = register(new FrontAttachmentType<>(
            Vehiclery.rl("autopilot"), AutopilotFrontAttachment::new,
            new FrontAttachmentModel(Vehiclery.rl("textures/entity/automobile/front_attachment/autopilot.png"), Vehiclery.rl("front_attachment/autopilot"), 1.7f)
    ));

    public static final FrontAttachmentType<CropHarvesterFrontAttachment> CROP_HARVESTER = register(new FrontAttachmentType<>(
            Vehiclery.rl("crop_harvester"), CropHarvesterFrontAttachment::new,
            new FrontAttachmentModel(Vehiclery.rl("textures/entity/automobile/front_attachment/crop_harvester.png"), Vehiclery.rl("front_attachment/harvester"), 0.83f)
    ));

    public static final FrontAttachmentType<GrassCutterFrontAttachment> GRASS_CUTTER = register(new FrontAttachmentType<>(
            Vehiclery.rl("grass_cutter"), GrassCutterFrontAttachment::new,
            new FrontAttachmentModel(Vehiclery.rl("textures/entity/automobile/front_attachment/grass_cutter.png"), Vehiclery.rl("front_attachment/harvester"), 0.83f)
    ));

    @Override
    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override
    public Identifier containerId() {
        return ID;
    }

    @Override
    public void forEachStat(Consumer<DisplayStat<FrontAttachmentType<?>>> action) {
    }

    @Override
    public Identifier getId() {
        return this.id();
    }

    private static <T extends FrontAttachment> FrontAttachmentType<T> register(FrontAttachmentType<T> entry) {
        REGISTRY.register(entry);
        return entry;
    }

    public record FrontAttachmentModel(Identifier texture, Identifier modelId, float scale) {}
}
