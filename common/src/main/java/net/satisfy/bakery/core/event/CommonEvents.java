package net.satisfy.bakery.core.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.bakery.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

public class CommonEvents {

    public static void init() {
        PlayerEvent.ATTACK_ENTITY.register(CommonEvents::attack);
    }

    public static EventResult attack(Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ObjectRegistry.SMALL_COOKING_POT_ITEM.get())) {
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEventRegistry.COOKING_POT_HIT.get(), SoundSource.PLAYERS, 0.5F, 0.75F);
            target.hurt(level.damageSources().generic(), 1.2F);
            itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            itemStack.applyComponents(DataComponentMap.builder().set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder().add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).build());
            if (target instanceof Mob mob) {
                mob.setTarget(player);
            }

            return EventResult.interruptTrue();
        } else if (itemStack.is(ObjectRegistry.ROLLING_PIN.get())) {
            if (!level.isClientSide) {
                level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 1.0F, 0.5F);
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.hurt(level.damageSources().generic(), 2.0F);
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1));

                    if (livingTarget instanceof Mob mob) {
                        mob.setTarget(player);
                    }
                }
                itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                itemStack.applyComponents(DataComponentMap.builder().set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder().add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).build());
                return EventResult.interruptTrue();
            }
        }
        return EventResult.pass();
    }
}
