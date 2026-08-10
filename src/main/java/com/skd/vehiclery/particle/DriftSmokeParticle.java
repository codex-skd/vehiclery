package com.skd.vehiclery.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class DriftSmokeParticle extends SingleQuadParticle {
    private static final float SCALE_FACTOR = 0.83f;
    private static final int MAX_AGE = (int)(Math.log(0.1) / Math.log(SCALE_FACTOR));

    protected DriftSmokeParticle(ClientLevel world, double x, double y, double z, TextureAtlasSprite sprite) {
        super(world, x, y, z, sprite);
        this.quadSize = 0.6f - (world.random.nextFloat() * 0.1f);
        this.alpha = this.quadSize;
        this.lifetime = MAX_AGE;
    }

    @Override
    public void tick() {
        super.tick();
        this.scale(SCALE_FACTOR);
        this.alpha = this.quadSize;
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double vx, double vy, double vz, RandomSource random) {
            return new DriftSmokeParticle(world, x, y, z, this.sprites.get(random));
        }
    }
}
