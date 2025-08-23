package net.satisfy.bakery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.bakery.core.block.entity.*;
import net.satisfy.bakery.core.util.BakeryIdentifier;
import net.satisfy.farm_and_charm.FarmAndCharm;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EntityTypeRegistry {
    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(FarmAndCharm.MOD_ID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();

    public static final RegistrySupplier<BlockEntityType<SmallCookingPotBlockEntity>> SMALL_COOKING_POT_BLOCK_ENTITY = registerBlockEntity("small_cooking_pot", () -> BlockEntityType.Builder.of(SmallCookingPotBlockEntity::new, ObjectRegistry.SMALL_COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> BAKERY_BANNER = registerBlockEntity("bakery_banner", () -> BlockEntityType.Builder.of(CompletionistBannerEntity::new, ObjectRegistry.BAKERY_BANNER.get(), ObjectRegistry.BAKERY_WALL_BANNER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_ENTITY = registerBlockEntity("storage", () -> BlockEntityType.Builder.of(StorageBlockEntity::new, StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", () -> BlockEntityType.Builder.of(CabinetBlockEntity::new, addCabinet(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<StreetSignBlockEntity>> STREET_SIGN_BLOCK_ENTITY = registerBlockEntity("street_sign", () -> BlockEntityType.Builder.of(StreetSignBlockEntity::new, ObjectRegistry.STREET_SIGN.get()).build(null));

    public static Set<Block> addCabinet(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CABINET.get());
        blocks.add(ObjectRegistry.DRAWER.get());
        blocks.add(ObjectRegistry.WALL_CABINET.get());
        return blocks;
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(String name, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(BakeryIdentifier.identifier(name), type);
    }

    public static void init() {
    }
}
