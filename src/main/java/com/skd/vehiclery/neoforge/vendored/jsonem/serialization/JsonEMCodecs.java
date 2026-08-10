package com.skd.vehiclery.neoforge.vendored.jsonem.serialization;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.vehiclery.neoforge.mixin.jsonem.CubeDefinitionAccess;
import com.skd.vehiclery.neoforge.mixin.jsonem.CubeDeformationAccess;
import com.skd.vehiclery.neoforge.mixin.jsonem.LayerDefinitionAccess;
import com.skd.vehiclery.neoforge.mixin.jsonem.MaterialDefinitionAccess;
import com.skd.vehiclery.neoforge.mixin.jsonem.PartDefinitionAccess;
import com.skd.vehiclery.neoforge.vendored.jsonem.util.UVPairComparable;
import net.minecraft.Util;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class JsonEMCodecs {
    private static final Set<Direction> ALL_DIRECTIONS = EnumSet.allOf(Direction.class);

    public static final Codec<Vector3f> VECTOR3F = Codec.FLOAT.listOf().comapFlatMap((vec) ->
                    Util.fixedSize(vec, 3).map(coords -> new Vector3f(coords.get(0), coords.get(1), coords.get(2))),
            (vec) -> ImmutableList.of(vec.x, vec.y, vec.z)
    );

    public static final Codec<MaterialDefinition> MATERIAL_DEFINITION = RecordCodecBuilder.create((instance) ->
        instance.group(
                Codec.INT.fieldOf("width").forGetter(obj -> ((MaterialDefinitionAccess) obj).vehiclery$width()),
                Codec.INT.fieldOf("height").forGetter(obj -> ((MaterialDefinitionAccess) obj).vehiclery$height())
        ).apply(instance, MaterialDefinition::new)
    );

    public static final Codec<PartPose> PART_POSE = RecordCodecBuilder.create((instance) ->
            instance.group(
                    VECTOR3F.optionalFieldOf("origin", new Vector3f()).forGetter(obj -> new Vector3f(obj.x, obj.y, obj.z)),
                    VECTOR3F.optionalFieldOf("rotation", new Vector3f()).forGetter(obj -> new Vector3f(obj.xRot, obj.yRot, obj.zRot))
            ).apply(instance, (origin, rot) -> PartPose.offsetAndRotation(origin.x(), origin.y(), origin.z(), rot.x(), rot.y(), rot.z()))
    );

    public static final Codec<CubeDeformation> CUBE_DEFORMATION = VECTOR3F.xmap(
            vec -> new CubeDeformation(vec.x(), vec.y(), vec.z()),
            dil -> new Vector3f(
                    ((CubeDeformationAccess) dil).vehiclery$radiusX(),
                    ((CubeDeformationAccess) dil).vehiclery$radiusY(),
                    ((CubeDeformationAccess) dil).vehiclery$radiusZ())
    );

    public static final Codec<UVPair> UV_PAIR = Codec.FLOAT.listOf().comapFlatMap((vec) ->
            Util.fixedSize(vec, 2).map((arr) -> new UVPairComparable(arr.get(0), arr.get(1))),
            (vec) -> ImmutableList.of(vec.u(), vec.v())
    );

    private static CubeDefinition createCuboidData(Optional<String> name, Vector3f offset, Vector3f dimensions, CubeDeformation dilation, boolean mirror, UVPair uv, UVPair uvSize) {
        return CubeDefinitionAccess.vehiclery$create(name.orElse(null), uv.u(), uv.v(), offset.x(), offset.y(), offset.z(), dimensions.x(), dimensions.y(), dimensions.z(), dilation, mirror, uvSize.u(), uvSize.v(), ALL_DIRECTIONS);
    }

    private static final UVPair DEFAULT_UV_SCALE = new UVPairComparable(1.0f, 1.0f);

    public static final Codec<CubeDefinition> CUBE_DEFINITION = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Codec.STRING.optionalFieldOf("name").forGetter(obj -> Optional.ofNullable(((CubeDefinitionAccess)(Object)obj).vehiclery$name())),
                    VECTOR3F.fieldOf("offset").forGetter(obj -> ((CubeDefinitionAccess)(Object)obj).vehiclery$offset()),
                    VECTOR3F.fieldOf("dimensions").forGetter(obj -> ((CubeDefinitionAccess)(Object)obj).vehiclery$dimensions()),
                    CUBE_DEFORMATION.optionalFieldOf("dilation", CubeDeformation.NONE).forGetter(obj -> ((CubeDefinitionAccess)(Object)obj).vehiclery$dilation()),
                    Codec.BOOL.optionalFieldOf("mirror", false).forGetter(obj -> ((CubeDefinitionAccess)(Object)obj).vehiclery$mirror()),
                    UV_PAIR.fieldOf("uv").forGetter(obj -> ((CubeDefinitionAccess)(Object)obj).vehiclery$uv()),
                    UV_PAIR.optionalFieldOf("uv_scale", DEFAULT_UV_SCALE).forGetter(obj -> UVPairComparable.of(((CubeDefinitionAccess)(Object)obj).vehiclery$uvScale()))
            ).apply(instance, JsonEMCodecs::createCuboidData)
    );

    private static Codec<PartDefinition> createPartDefinitionCodec() {
        return RecordCodecBuilder.create((instance) ->
                instance.group(
                        PART_POSE.optionalFieldOf("transform", PartPose.ZERO).forGetter(obj -> ((PartDefinitionAccess)obj).vehiclery$transform()),
                        Codec.list(CUBE_DEFINITION).fieldOf("cuboids").forGetter(obj -> ((PartDefinitionAccess)obj).vehiclery$cuboids()),
                        LazyTypeUnboundedMapCodec.of(Codec.STRING, JsonEMCodecs::createPartDefinitionCodec).optionalFieldOf("children", new HashMap<>()).forGetter(obj -> ((PartDefinitionAccess)obj).vehiclery$children())
                ).apply(instance, (transform, cuboids, children) -> {
                    var data = PartDefinitionAccess.vehiclery$create(cuboids, transform);
                    ((PartDefinitionAccess) data).vehiclery$children().putAll(children);
                    return data;
                })
        );
    }

    public static final Codec<PartDefinition> PART_DEFINITION = createPartDefinitionCodec();

    public static final Codec<LayerDefinition> LAYER_DEFINITION = RecordCodecBuilder.create((instance) ->
            instance.group(
                    MATERIAL_DEFINITION.fieldOf("texture").forGetter(obj -> ((LayerDefinitionAccess) obj).vehiclery$texture()),
                    Codec.unboundedMap(Codec.STRING, PART_DEFINITION).fieldOf("bones").forGetter(obj -> ((PartDefinitionAccess) ((LayerDefinitionAccess) obj).vehiclery$root().getRoot()).vehiclery$children())
            ).apply(instance, (texture, bones) -> {
                var data = new MeshDefinition();
                ((PartDefinitionAccess) data.getRoot()).vehiclery$children().putAll(bones);
                return LayerDefinitionAccess.vehiclery$create(data, texture);
            })
    );
}
