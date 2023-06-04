package com.korona.koronaswiat.item.custom.stone;

import com.korona.koronaswiat.KoronaSwiat;
import com.korona.koronaswiat.entity.ModEntityTypes;
import com.korona.koronaswiat.entity.projectile.FireBallEntity;
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

public class SoulWaveStone extends Item implements IMagicStoneItem {
    public Integer cooldownTime;
    public SoulWaveStone(Properties properties, Integer cooldownTime) {
        super(properties);
        this.cooldownTime = cooldownTime;
    }

    public void executeStoneEffect(World world, PlayerEntity player, Hand hand) {
        if (!player.level.isClientSide()){
            FireBallEntity arrow = new FireBallEntity(ModEntityTypes.FIRE_BALL.get(), player, player.level);
//            arrow.setDeltaMovement(0, 1, 0); // directly up
//            player.level.addFreshEntity(arrow);
            arrow.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 5 * 3.0F, 1.0F);
        }
    }

    public void createParticles(World world, PlayerEntity player, Hand hand) {
//        //Make a particle spiral starting from the player and moving away depend on the direction the player is looking
//        double x = player.getX(); // get player position x
//        double y = player.getY(); // get player position y
//        double z = player.getZ(); // get player position z
//        double angle = Math.toRadians(player.yRot); // get player rotation angle in radians (horizontal) (0 = east, 90 = south, 180 = west, 270 = north)
//        double angle2 = Math.toRadians(player.xRot); // get player rotation angle in radians (vertical) (0 = straight, 90 = down, -90 = up)
//        // calculate the z, y and z speed of the particle depending on the angle
//        double speedX = Math.cos(angle) * Math.cos(angle2);
//        double speedZ = Math.sin(angle) * Math.cos(angle2);
//        double speedY = Math.sin(angle2);
//        // show a big ball of particles moving away from player depending on the direction the player is looking
//        player.level.addParticle(ParticleTypes.END_ROD, x, y, z, 1.5 * speedX, 1.5 * speedY, 1.5 * speedZ);
    }

    @Override
    public Integer getCoolDownTime() {
        return cooldownTime;
    }
}
