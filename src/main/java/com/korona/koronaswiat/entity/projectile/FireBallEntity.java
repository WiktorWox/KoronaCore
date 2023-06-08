package com.korona.koronaswiat.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireBallEntity extends ThrowableEntity {
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
        this.remove();
    }

    protected void onHitBlock(BlockRayTraceResult ray) {
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.Mode.BREAK);
        this.remove();
    }

    public boolean isInWater() {
        if (this.wasTouchingWater) {
            this.remove();
            this.level.setBlock(this.blockPosition(), Blocks.OBSIDIAN.defaultBlockState(), 3);
            this.level.addParticle(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
        return this.wasTouchingWater;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
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