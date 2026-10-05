package net.satisfy.bakery.core.registry;

import net.satisfy.foundation.banner.CompletionistBannerEntity;
import net.satisfy.foundation.block.CabinetBlockEntity;
import net.satisfy.foundation.storage.StorageBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.block.entity.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EntityTypeRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Bakery.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<SmallCookingPotBlockEntity>> SMALL_COOKING_POT_BLOCK_ENTITY = registerBlockEntity("small_cooking_pot", () -> BlockEntityType.Builder.of(SmallCookingPotBlockEntity::new, ObjectRegistry.SMALL_COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> BAKERY_BANNER = registerBlockEntity("bakery_banner", () -> BlockEntityType.Builder.of(CompletionistBannerEntity::new, ObjectRegistry.BAKERY_BANNER.get(), ObjectRegistry.BAKERY_WALL_BANNER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_ENTITY = registerBlockEntity("storage", () -> BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(EntityTypeRegistry.STORAGE_ENTITY.get(), pos, state), StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", () -> BlockEntityType.Builder.of((pos, state) -> new CabinetBlockEntity(EntityTypeRegistry.CABINET_BLOCK_ENTITY.get(), pos, state), addCabinet(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<BakerStationBlockEntity>> BAKER_STATION_BLOCK_ENTITY = registerBlockEntity("baker_station", () -> BlockEntityType.Builder.of(BakerStationBlockEntity::new, ObjectRegistry.BAKER_STATION.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlankCakeBlockEntity>> BLANK_CAKE_BLOCK_ENTITY = registerBlockEntity("blank_cake", () -> BlockEntityType.Builder.of(BlankCakeBlockEntity::new, ObjectRegistry.BLANK_CAKE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CakeCandleBlockEntity>> CAKE_CANDLE_BLOCK_ENTITY = registerBlockEntity("cake_candle", () -> BlockEntityType.Builder.of(CakeCandleBlockEntity::new, ObjectRegistry.CHOCOLATE_GATEAU.get(), ObjectRegistry.CHOCOLATE_TART.get(), ObjectRegistry.STRAWBERRY_CAKE.get(), ObjectRegistry.SWEETBERRY_CAKE.get(), ObjectRegistry.CHOCOLATE_CAKE.get(), ObjectRegistry.BUNDT_CAKE.get(), ObjectRegistry.LINZER_TART.get(), ObjectRegistry.APPLE_PIE.get(), ObjectRegistry.GLOWBERRY_TART.get(), ObjectRegistry.PUDDING.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StreetSignBlockEntity>> STREET_SIGN_BLOCK_ENTITY = registerBlockEntity("street_sign", () -> BlockEntityType.Builder.of(StreetSignBlockEntity::new, ObjectRegistry.STREET_SIGN.get()).build(null));

    public static Set<Block> addCabinet(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CABINET.get());
        blocks.add(ObjectRegistry.DRAWER.get());
        blocks.add(ObjectRegistry.WALL_CABINET.get());
        return blocks;
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(String name, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(name, type);
    }

    public static void init() {
        BLOCK_ENTITY_TYPES.register();
    }
}
