package com.korona.koronaswiat.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireBallEntity extends AbstractArrowEntity {
    public FireBallEntity(EntityType<FireBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public FireBallEntity(EntityType<FireBallEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    public FireBallEntity(EntityType<FireBallEntity> entityType, LivingEntity shooter, World world) {
        super(entityType, shooter, world);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult ray) {
        super.onHitEntity(ray);
        // this, x, y, z, explosionStrength, setsFires, breakMode
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.Mode.BREAK);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        System.out.println("getAddEntityPacket called");
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}