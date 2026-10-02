package net.satisfy.bakery.core.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.bakery.core.registry.MobEffectRegistry;
import net.satisfy.bakery.platform.PlatformHelper;
import net.satisfy.foundation.food.EffectFoodItem;
import org.jetbrains.annotations.NotNull;

public class SugarRushEffectItem extends EffectFoodItem {
    private final RegistrySupplier<MobEffect> effect;
    private final int duration;

    public SugarRushEffectItem(Properties properties, RegistrySupplier<MobEffect> effect, int duration, boolean returnBowl) {
        super(properties, duration, returnBowl);
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Holder<MobEffect> effectHolder = MobEffectRegistry.holder(effect);
        MobEffectInstance currentEffect = entity.getEffect(effectHolder);

        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (level.isClientSide() || !(entity instanceof Player player)) {
            return result;
        }

        int newAmplifier = currentEffect == null ? 0 : Math.min(PlatformHelper.getSugarRushMaxStacks() - 1, currentEffect.getAmplifier() + 1);
        player.removeEffect(effectHolder);
        player.addEffect(new MobEffectInstance(effectHolder, duration, newAmplifier, false, true, true));

        return result;
    }
}
