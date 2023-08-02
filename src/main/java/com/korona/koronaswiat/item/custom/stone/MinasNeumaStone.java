package com.korona.koronaswiat.item.custom.stone;

import com.korona.koronaswiat.util.ModSoundEvent;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MinasNeumaStone extends Item implements IMagicStoneItem {
    public Integer cooldownTime;
    public MinasNeumaStone(Properties properties, Integer cooldownTime) {
        super(properties);
        this.cooldownTime = cooldownTime;
    }

    public void executeStoneEffect(World world, PlayerEntity player, Hand hand) {
        List<LivingEntity> entities = world.getNearbyEntities(LivingEntity.class, (new EntityPredicate()).range(20.0D), player, new AxisAlignedBB(new BlockPos(player.position())).inflate(20.0D, 20.0D, 20.0D));
        for (LivingEntity entity : entities) {
            entity.addEffect(new EffectInstance(Effects.GLOWING, 200, 1));
        }
        player.level.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), ModSoundEvent.ITEM_STONE_MINASNEUMACRYSTAL.get(), SoundCategory.PLAYERS, 18, 1);
        // show horizontal circle wave of particles moving away from player
        createParticles(world, player, hand);
    }

    public void createParticles(World world, PlayerEntity player, Hand hand) {
        // show horizontal circle wave of particles moving away from player
        for (int i = 0; i < 360; i += 5) {
            double x = Math.cos(Math.toRadians(i));
            double z = Math.sin(Math.toRadians(i));
            player.level.addParticle(ParticleTypes.END_ROD, player.getX(), player.getY(), player.getZ(), 1.5 * x, 0, 1.5 * z);
        }
    }

    @Override
    public Integer getCoolDownTime() {
        return cooldownTime;
    }
}
