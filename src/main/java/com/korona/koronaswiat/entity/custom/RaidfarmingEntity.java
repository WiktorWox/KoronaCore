package com.korona.koronaswiat.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.UUID;

public class RaidfarmingEntity extends CreatureEntity implements IAngerable {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private UUID persistentAngerTarget;
    private int remainingPersistentAngerTime;
    private int ticksUntilNextAlert;
    private int playFirstAngerSoundIn;
    private static final RangedInteger FIRST_ANGER_SOUND_DELAY = TickRangeConverter.rangeOfSeconds(0, 1);
    private static final RangedInteger ALERT_INTERVAL = TickRangeConverter.rangeOfSeconds(4, 6);


    public RaidfarmingEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    public void setRemainingPersistentAngerTime(int p_230260_1_) {
        this.remainingPersistentAngerTime = p_230260_1_;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    protected void customServerAiStep() {
        ModifiableAttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (this.isAngry()) {
            if (!this.isBaby() && !modifiableattributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }

            this.maybePlayFirstAngerSound();
        } else if (modifiableattributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
            modifiableattributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        }

        this.updatePersistentAnger((ServerWorld)this.level, true);
        if (this.getTarget() != null) {
            this.maybeAlertOthers();
        }

        if (this.isAngry()) {
            this.lastHurtByPlayerTime = this.tickCount;
        }

        super.customServerAiStep();
    }

    private void maybePlayFirstAngerSound() {
        if (this.playFirstAngerSoundIn > 0) {
            --this.playFirstAngerSoundIn;
            if (this.playFirstAngerSoundIn == 0) {
                this.playAngerSound();
            }
        }

    }

    private void playAngerSound() {
        this.playSound(SoundEvents.PILLAGER_AMBIENT, this.getSoundVolume() * 2.0F, this.getVoicePitch() * 1.8F);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return CreatureEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.MOVEMENT_SPEED, (double)0.45F)
                .add(Attributes.ATTACK_DAMAGE, 5.0f);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(0, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(0, new RandomWalkingGoal(this, 0.5D));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));

    }

    private void maybeAlertOthers() {
        if (this.ticksUntilNextAlert > 0) {
            --this.ticksUntilNextAlert;
        } else {
            if (this.getSensing().canSee(this.getTarget())) {
                this.alertOthers();
            }

            this.ticksUntilNextAlert = ALERT_INTERVAL.randomValue(this.random);
        }
    }

    private void alertOthers() {
        double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(this.position()).inflate(d0, 10.0D, d0);
        this.level.getLoadedEntitiesOfClass(RaidfarmingEntity.class, axisalignedbb).stream().filter((p_241408_1_) -> {
            return p_241408_1_ != this;
        }).filter((p_241407_0_) -> {
            return p_241407_0_.getTarget() == null;
        }).filter((p_241406_1_) -> {
            return !p_241406_1_.isAlliedTo(this.getTarget());
        }).forEach((p_241405_1_) -> {
            p_241405_1_.setTarget(this.getTarget());
        });
    }

    public void setTarget(@Nullable LivingEntity p_70624_1_) {
        if (this.getTarget() == null && p_70624_1_ != null) {
            this.playFirstAngerSoundIn = FIRST_ANGER_SOUND_DELAY.randomValue(this.random);
            this.ticksUntilNextAlert = ALERT_INTERVAL.randomValue(this.random);
        }

        if (p_70624_1_ instanceof PlayerEntity) {
            this.setLastHurtByPlayer((PlayerEntity)p_70624_1_);
        }

        super.setTarget(p_70624_1_);
    }


    @Override
    protected int getExperienceReward(PlayerEntity player)
    {
        return 5;
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.VILLAGER_DEATH;
    }
}
