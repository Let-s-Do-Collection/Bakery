package net.satisfy.bakery.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.satisfy.bakery.platform.PlatformHelper;

public class VitalityEffect extends MobEffect {

    public VitalityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFBFA34A);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide()) {
            return true;
        }

        if (!(entity instanceof Player player)) {
            return true;
        }

        if (!player.isAlive() || player.isSpectator()) {
            return true;
        }

        float baseReduction = PlatformHelper.getVitalityEffectExhaustionReduction();
        if (!Float.isFinite(baseReduction) || baseReduction <= 0.0F) {
            return true;
        }

        int safeAmplifier = Math.max(0, amplifier);
        float scaledReduction = baseReduction * (safeAmplifier + 1);
        if (!Float.isFinite(scaledReduction) || scaledReduction <= 0.0F) {
            return true;
        }

        FoodData foodData = player.getFoodData();
        float exhaustionLevel = foodData.getExhaustionLevel();
        if (!Float.isFinite(exhaustionLevel) || exhaustionLevel <= 0.0F) {
            return true;
        }

        float delta = Math.min(scaledReduction, exhaustionLevel);
        if (delta <= 0.0F) {
            return true;
        }

        foodData.addExhaustion(-delta);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int interval = Math.max(1, PlatformHelper.getVitalityEffectInterval());
        return duration % interval == 0;
    }
}
