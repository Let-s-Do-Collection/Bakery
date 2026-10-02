package net.satisfy.bakery.core.effect;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.platform.PlatformHelper;

public class SugarRushEffect extends MobEffect {
    private static final ResourceLocation SPEED_ID = Bakery.identifier("sugar_rush_speed");
    private static final ResourceLocation ATTACK_SPEED_ID = Bakery.identifier("sugar_rush_attack_speed");

    public SugarRushEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFF06A6A);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof Player player) || !player.isAlive() || player.isSpectator()) {
            return true;
        }

        int speedLimit = PlatformHelper.getSugarRushAttackSpeedStacks();
        double bonus = PlatformHelper.getSugarRushBonusPerStack();
        int stacks = Math.min(PlatformHelper.getSugarRushMaxStacks(), Math.max(1, amplifier + 1));
        int speedStacks = Math.min(speedLimit, stacks);
        int attackSpeedStacks = PlatformHelper.isSugarRushAttackSpeedEnabled() ? Math.max(0, stacks - speedLimit) : 0;

        updateModifier(player, Attributes.MOVEMENT_SPEED, SPEED_ID, speedStacks * bonus);
        updateModifier(player, Attributes.ATTACK_SPEED, ATTACK_SPEED_ID, attackSpeedStacks * bonus);

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onMobRemoved(LivingEntity entity, int amplifier, Entity.RemovalReason removalReason) {
        removeModifier(entity, Attributes.MOVEMENT_SPEED, SPEED_ID);
        removeModifier(entity, Attributes.ATTACK_SPEED, ATTACK_SPEED_ID);
    }

    private static void updateModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount) {
        AttributeInstance instance = entity.getAttributes().getInstance(attribute);
        if (instance == null) {
            return;
        }

        instance.removeModifier(id);

        if (amount <= 0.0D) {
            return;
        }

        instance.addTransientModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    private static void removeModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id) {
        AttributeInstance instance = entity.getAttributes().getInstance(attribute);
        if (instance == null) {
            return;
        }

        instance.removeModifier(id);
    }
}
