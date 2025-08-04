package net.satisfy.bakery.core.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class ZombieMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void addCookingItems(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag compound, CallbackInfoReturnable<SpawnGroupData> cir) {
        Zombie zombie = (Zombie)(Object)this;
        if (!zombie.isBaby() && Math.random() < 0.03) {
            ItemStack item = Math.random() < 0.33 ? new ItemStack(ObjectRegistry.ROLLING_PIN.get()) : (Math.random() < 0.5 ? new ItemStack(ObjectRegistry.BREAD_KNIFE.get()) : new ItemStack(ObjectRegistry.SMALL_COOKING_POT_ITEM.get()));
            zombie.setItemSlot(EquipmentSlot.MAINHAND, item);
        }
    }
}
