package com.skd.vehiclery.neoforge.block.render;

import com.skd.vehiclery.block.model.GeometryBuilder;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

// TODO(port): rewritten for the new QuadCollection/BakedQuad model system. Unlike the old
// single-side-filtered builder, this now emits every quad into a QuadCollection.Builder
// (culled per rotated face, or unculled when face is null), since the new BlockStateModelPart
// system wants all faces at once rather than being re-invoked once per Direction.
public class NeoForgeGeometryBuilder implements GeometryBuilder {
    private final Matrix4f transform;
    private final QuadCollection.Builder collection;
    private final QuadBakingVertexConsumer quads;

    private int vidx = 0;
    private @Nullable Direction pendingFace;

    public NeoForgeGeometryBuilder(Matrix4f transform, QuadCollection.Builder collection) {
        this.transform = transform;
        this.collection = collection;

        this.quads = new QuadBakingVertexConsumer();
        this.quads.setShade(true);
        this.quads.setAmbientOcclusion(true);
    }

    @Override
    public GeometryBuilder vertex(float x, float y, float z, @Nullable Direction face, float nx, float ny, float nz, TextureAtlasSprite sprite, float u, float v) {
        return this.vertex(x, y, z, face, nx, ny, nz, sprite, u, v, 0xFFFFFFFF);
    }

    @Override
    public GeometryBuilder vertex(float x, float y, float z, @Nullable Direction face, float nx, float ny, float nz, TextureAtlasSprite sprite, float u, float v, int color) {
        if (face != null) {
            face = Direction.rotate(this.transform, face);
        }
        this.pendingFace = face;

        var pos = new Vector4f(x - 0.5f, y, z - 0.5f, 1);
        var tNormal = new Vector4f(nx, ny, nz, 1);
        pos.mul(this.transform);
        tNormal.mul(this.transform); // This is under the assumption that transform will always be a rotation

        var normal = new Vector3f(tNormal.x(), tNormal.y(), tNormal.z());
        normal.normalize();

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        quads.setDirection(face != null ? face : Direction.UP);
        quads.setSprite(sprite, ChunkSectionLayer.TRANSLUCENT, Sheets.translucentBlockItemSheet());
        quads.addVertex(pos.x() + 0.5f, pos.y(), pos.z() + 0.5f).setColor(color).setUv(u0 + ((u1 - u0) * u), v0 + ((v1 - v0) * v)).setNormal(normal.x(), normal.y(), normal.z());

        if (++vidx == 4) {
            var quad = quads.bakeQuad();
            if (this.pendingFace != null) {
                this.collection.addCulledFace(this.pendingFace, quad);
            } else {
                this.collection.addUnculledFace(quad);
            }
            vidx = 0;
        }

        return this;
    }
}
