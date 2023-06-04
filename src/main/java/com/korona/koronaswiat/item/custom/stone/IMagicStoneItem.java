package com.korona.koronaswiat.item.custom.stone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface IMagicStoneItem {

    // Effect that will be executed when player right clicks with the Wand in hand, when the stone is equipped
    void executeStoneEffect(World world, PlayerEntity player, Hand hand);

    // Cooldown that will be applied to the player after the stone is used
    Integer getCoolDownTime();

    // Particles that will be created when the stone is used
    void createParticles(World world, PlayerEntity player, Hand hand);
}
