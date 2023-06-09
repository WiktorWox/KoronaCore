package com.korona.koronaswiat.item.custom.stone;

import com.korona.koronaswiat.entity.ModEntityTypes;
import com.korona.koronaswiat.entity.projectile.FireBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Kiuaskivi extends Item implements IMagicStoneItem {
    public Integer cooldownTime;
    public Kiuaskivi(Properties properties, Integer cooldownTime) {
        super(properties);
        this.cooldownTime = cooldownTime;
    }

    public void executeStoneEffect(World world, PlayerEntity player, Hand hand) {
        if (!player.level.isClientSide()){
            FireBallEntity fireBall = new FireBallEntity(ModEntityTypes.FIRE_BALL.get(), player, player.level);
            if (player.hasEffect(Effects.DAMAGE_BOOST)) {
                fireBall.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 0.7F * (player.getEffect(Effects.DAMAGE_BOOST).getAmplifier() + 1), 1.0F);
            } else {
                fireBall.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 0.7F, 1.0F);
            }
            player.level.addFreshEntity(fireBall);
        }
    }

    public void createParticles(World world, PlayerEntity player, Hand hand) {
    }

    @Override
    public Integer getCoolDownTime() {
        return cooldownTime;
    }
}
