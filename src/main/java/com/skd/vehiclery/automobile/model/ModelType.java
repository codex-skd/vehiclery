package com.skd.vehiclery.automobile.model;

import com.mojang.serialization.Codec;
import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.render.BaseModel;
import com.skd.vehiclery.automobile.render.attachment.front.AutopilotFrontAttachmentModel;
import com.skd.vehiclery.automobile.render.attachment.front.FrontAttachmentRenderModel;
import com.skd.vehiclery.automobile.render.attachment.front.HarvesterFrontAttachmentModel;
import com.skd.vehiclery.automobile.render.attachment.rear.BannerPostRearAttachmentModel;
import com.skd.vehiclery.automobile.render.attachment.rear.ChestRearAttachmentModel;
import com.skd.vehiclery.automobile.render.attachment.rear.GrindstoneRearAttachmentModel;
import com.skd.vehiclery.automobile.render.attachment.rear.PlowRearAttachmentModel;
import com.skd.vehiclery.automobile.render.attachment.rear.RearAttachmentRenderModel;
import com.skd.vehiclery.automobile.render.attachment.rear.StonecutterRearAttachmentModel;
import com.skd.vehiclery.automobile.render.obj.ObjModel;
import com.skd.vehiclery.util.SimpleMapContentRegistry;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;

public record ModelType(Identifier id,
                        ModelInstanceProvider provider
) implements SimpleMapContentRegistry.Identifiable {
    public static final SimpleMapContentRegistry<ModelType> REGISTRY = new SimpleMapContentRegistry<>();
    public static final Codec<ModelType> CODEC = REGISTRY.codec();

    public static final ModelType BASIC = REGISTRY.register(new ModelType(Vehiclery.rl("basic"), BaseModel::new));
    public static final ModelType FRONT_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("front_attachment"), FrontAttachmentRenderModel::new));
    public static final ModelType AUTOPILOT_FRONT_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("autopilot_front_attachment"), AutopilotFrontAttachmentModel::new));
    public static final ModelType HARVESTER_FRONT_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("harvester_front_attachment"), HarvesterFrontAttachmentModel::new));
    public static final ModelType REAR_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("rear_attachment"), RearAttachmentRenderModel::new));
    public static final ModelType CHEST_REAR_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("chest_rear_attachment"), ChestRearAttachmentModel::new));
    public static final ModelType GRINDSTONE_REAR_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("grindstone_rear_attachment"), GrindstoneRearAttachmentModel::new));
    public static final ModelType STONECUTTER_REAR_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("stonecutter_rear_attachment"), StonecutterRearAttachmentModel::new));
    public static final ModelType PLOW_REAR_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("plow_rear_attachment"), PlowRearAttachmentModel::new));
    public static final ModelType BANNER_REAR_ATTACHMENT = REGISTRY.register(new ModelType(Vehiclery.rl("banner_rear_attachment"), BannerPostRearAttachmentModel::new));
    public static final ModelType OBJ = REGISTRY.register(new ModelType(Vehiclery.rl("obj"), ObjModel::new));

    @Override
    public Identifier getId() {
        return id();
    }

    public interface ModelInstanceProvider {
        Model create(EntityRendererProvider.Context ctx,
                     ModelDefinition.RenderMaterial material,
                     ModelLayerLocation modelLayer,
                     Vector3f translation, Vector3f rotation, Vector3f scale);
    }
}
