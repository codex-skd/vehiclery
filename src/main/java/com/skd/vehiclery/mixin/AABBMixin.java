package com.skd.vehiclery.mixin;

import com.skd.vehiclery.util.duck.CollisionArea;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AABB.class)
public class AABBMixin implements CollisionArea {
    @Override
    public boolean isPointInside(double x, double y, double z) {
        var self = (AABB)(Object)this;
        return (x >= self.minX && x <= self.maxX) &&
                (y >= self.minY && y <= self.maxY) &&
                (z >= self.minZ && z <= self.maxZ);
    }

    @Override
    public boolean boxIntersects(AABB box) {
        return ((AABB)(Object)this).intersects(box);
    }

    @Override
    public double highestY(double x, double y, double z) {
        return ((AABB)(Object)this).maxY;
    }
}
