package net.satisfy.bakery.core.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.farm_and_charm.core.item.food.EffectItem;
import org.jetbrains.annotations.NotNull;

public class SugarRushEffectItem extends EffectItem {

    private final Holder<MobEffect> effect;
    private final int duration;
    private final int maxStacks;

    public SugarRushEffectItem(Properties properties, Holder<MobEffect> effect, int duration, int maxStacks, boolean returnBowl) {
        super(properties, duration, returnBowl);
        this.effect = effect;
        this.duration = duration;
        this.maxStacks = maxStacks;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (level.isClientSide()) {
            return result;
        }

        if (!(entity instanceof Player player)) {
            return result;
        }

        MobEffectInstance currentEffect = player.getEffect(effect);

        int newAmplifier;
        if (currentEffect == null) {
            newAmplifier = 0;
        } else {
            newAmplifier = Math.min(maxStacks - 1, currentEffect.getAmplifier() + 1);
        }

        player.addEffect(new MobEffectInstance(effect, duration, newAmplifier, false, true, true));

        return result;
    }
}