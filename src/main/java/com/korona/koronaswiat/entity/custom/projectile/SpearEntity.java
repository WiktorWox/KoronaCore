package com.korona.koronaswiat.entity.custom.projectile;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Predicate;

public class SpearEntity extends ThrowableEntity {
    public SpearEntity(EntityType<SpearEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpearEntity(EntityType<SpearEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    public SpearEntity(EntityType<SpearEntity> entityType, LivingEntity shooter, World world) {
        super(entityType, shooter, world);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult ray) {
        super.onHitEntity(ray);
        // this, x, y, z, explosionStrength, setsFires, breakMode
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.Mode.BREAK);
        this.remove();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        System.out.println("getAddEntityPacket called");
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}