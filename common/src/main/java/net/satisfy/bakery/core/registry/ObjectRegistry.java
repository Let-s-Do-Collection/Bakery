package net.satisfy.bakery.core.registry;

import net.satisfy.foundation.block.CabinetWallBlock;
import net.minecraft.world.effect.MobEffects;
import net.satisfy.foundation.banner.CompletionistWallBannerBlock;
import net.satisfy.foundation.banner.CompletionistBannerBlock;
import net.satisfy.foundation.banner.BannerSettings;
import net.satisfy.foundation.block.CabinetBlock;
import net.satisfy.foundation.block.BenchBlock;
import net.satisfy.foundation.block.ChairBlock;
import net.satisfy.foundation.block.LineConnectingBlock;
import net.satisfy.foundation.block.SinkBlock;
import net.satisfy.foundation.block.StackableEatableBlock;
import net.satisfy.foundation.util.RegistryUtil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.satisfy.bakery.Bakery;
import net.satisfy.bakery.core.block.*;
import net.satisfy.bakery.core.item.BakedSweetDoughItem;
import net.satisfy.bakery.core.item.SmallCookingPotItem;
import net.satisfy.bakery.core.item.SugarRushEffectItem;
import net.satisfy.bakery.platform.PlatformHelper;
import net.satisfy.foundation.food.PlaceableEffectFoodItem;
import net.satisfy.foundation.food.EffectFoodItem;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Bakery.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Bakery.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    private static final BannerSettings BANNER_SETTINGS = new BannerSettings(
            () -> EntityTypeRegistry.BAKERY_BANNER.get(),
            () -> ObjectRegistry.BAKERY_WALL_BANNER.get(),
            Bakery.identifier("textures/banner/bakery_banner.png"),
            "tooltip.bakery.banner",
            MobEffects.DAMAGE_RESISTANCE,
            PlatformHelper::getBannerEffectRadius,
            PlatformHelper::getBannerEffectAmplifier);
    public static final RegistrySupplier<Block> BAKERY_BANNER = registerWithItem("bakery_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD), BANNER_SETTINGS));
    public static final RegistrySupplier<Block> BAKERY_WALL_BANNER = registerWithoutItem("bakery_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD), BANNER_SETTINGS));
    public static final RegistrySupplier<Block> KITCHEN_SINK = registerWithItem("kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).noOcclusion()));
    public static final RegistrySupplier<Block> BAKER_STATION = registerWithItem("baker_station", () -> new BakerStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final RegistrySupplier<Block> BRICK_COUNTER = registerWithItem("brick_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final RegistrySupplier<Block> CABINET = registerWithItem("cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), EntityTypeRegistry.CABINET_BLOCK_ENTITY, SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> DRAWER = registerWithItem("drawer", () -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), EntityTypeRegistry.CABINET_BLOCK_ENTITY, SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> WALL_CABINET = registerWithItem("wall_cabinet", () -> new CabinetWallBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), EntityTypeRegistry.CABINET_BLOCK_ENTITY, SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> IRON_BENCH = registerWithItem("iron_bench", () -> new BenchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Block> IRON_CHAIR = registerWithItem("iron_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Block> IRON_TABLE = registerWithItem("iron_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Block> STREET_SIGN = registerWithItem("street_sign", () -> new StreetSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CAKE_STAND = registerWithItem("cake_stand", () -> new CakeStandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).sound(SoundType.GLASS)));
    public static final RegistrySupplier<Block> CAKE_DISPLAY = registerWithItem("cake_display", () -> new CakeDisplayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).sound(SoundType.GLASS)));
    public static final RegistrySupplier<Block> CUPCAKE_DISPLAY = registerWithItem("cupcake_display", () -> new CupcakeDisplayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BREADBOX = registerWithItem("breadbox", () -> new BreadBox(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> TRAY = registerWithItem("tray", () -> new TrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> BREAD_CRATE = registerWithItem("bread_crate", () -> new BreadBasketBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WALL_DISPLAY = registerWithItem("wall_display", () -> new WallDisplayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CHOCOLATE_BOX = registerWithItem("chocolate_box", () -> new ChocolateBoxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final RegistrySupplier<Item> ROLLING_PIN = registerItem("rolling_pin", () -> new SwordItem(Tiers.WOOD, getSettings().rarity(Rarity.COMMON).attributes(SwordItem.createAttributes(Tiers.WOOD, -1, PlatformHelper.getRollingPinAttackSpeed() - 4.0F))));
    public static final RegistrySupplier<Item> BREAD_KNIFE = registerItem("bread_knife", () -> new SwordItem(Tiers.IRON, getSettings().rarity(Rarity.COMMON).attributes(SwordItem.createAttributes(Tiers.IRON, -1, PlatformHelper.getKnifeAttackSpeed() - 4.0F))));
    public static final RegistrySupplier<Block> SMALL_COOKING_POT = registerWithoutItem("small_cooking_pot", () -> new SmallCookingPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Item> SMALL_COOKING_POT_ITEM = registerItem("small_cooking_pot", () -> new SmallCookingPotItem(SMALL_COOKING_POT.get(), getSettings().attributes(SmallCookingPotItem.createAttributes())));
    public static final RegistrySupplier<Block> JAR = registerWithItem("jar", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS), 4, createJarShapes()));
    public static final RegistrySupplier<Block> STRAWBERRY_JAM = registerWithItem("strawberry_jam", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS), 4, createJarShapes()), () -> JAR.get().asItem());
    public static final RegistrySupplier<Block> GLOWBERRY_JAM = registerWithItem("glowberry_jam", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS), 4, createJarShapes()), () -> JAR.get().asItem());
    public static final RegistrySupplier<Block> SWEETBERRY_JAM = registerWithItem("sweetberry_jam", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS), 4, createJarShapes()), () -> JAR.get().asItem());
    public static final RegistrySupplier<Block> CHOCOLATE_JAM = registerWithItem("chocolate_jam", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS), 4, createJarShapes()), () -> JAR.get().asItem());
    public static final RegistrySupplier<Block> APPLE_JAM = registerWithItem("apple_jam", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).instabreak().sound(SoundType.GLASS).noOcclusion(), 4, createJarShapes()), () -> JAR.get().asItem());
    public static final RegistrySupplier<Block> CRUSTY_BREAD_BLOCK = registerWithoutItem("crusty_bread_block", () -> new StackableEatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 3, createCrustyBreadShapes()));
    public static final RegistrySupplier<Block> BREAD_BLOCK = registerWithoutItem("bread_block", () -> new StackableEatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 3, createBreadShapes()));
    public static final RegistrySupplier<Block> BAGUETTE_BLOCK = registerWithoutItem("baguette_block", () -> new StackableEatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 4, createBaguetteShapes()));
    public static final RegistrySupplier<Block> TOAST_BLOCK = registerWithoutItem("toast_block", () -> new StackableEatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 3, createToastShapes()));
    public static final RegistrySupplier<Block> BRAIDED_BREAD_BLOCK = registerWithoutItem("braided_bread_block", () -> new StackableEatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 3, createBraidedBreadShapes()));
    public static final RegistrySupplier<Block> BUN_BLOCK = registerWithoutItem("bun_block", () -> new ShapedStackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 4, createBunShapes()));
    public static final RegistrySupplier<Block> WAFFLE_BLOCK = registerWithoutItem("waffle_block", () -> new StackableEatableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 8, createWaffleShapes()));
    public static final RegistrySupplier<Item> CAKE_DOUGH = registerItem("cake_dough", () -> new Item(getSettings().food(Foods.SWEET_BERRIES)));
    public static final RegistrySupplier<Item> CORNET_SHELL = registerItem("cornet_shell", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> SPONGE_SHEET = registerItem("sponge_sheet", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> SWEET_DOUGH = registerItem("sweet_dough", () -> new Item(getSettings().food(Foods.SWEET_BERRIES)));
    public static final RegistrySupplier<Item> BAKED_SWEET_DOUGH = registerItem("baked_sweet_dough", () -> new BakedSweetDoughItem(getSettings().food(Foods.COOKIE)));
    public static final RegistrySupplier<Item> CROISSANT = registerItem("croissant", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getCroissantNutrition(), PlatformHelper.getCroissantSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getCroissantEffectDuration()), 400, false));
    public static final RegistrySupplier<Item> CRUSTY_BREAD = registerItem("crusty_bread", () -> new PlaceableEffectFoodItem(CRUSTY_BREAD_BLOCK.get(), getFoodItemSettings(PlatformHelper.getCrustyBreadNutrition(), PlatformHelper.getCrustyBreadSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getCrustyBreadEffectDuration())));
    public static final RegistrySupplier<Item> BREAD = registerItem("bread", () -> new PlaceableEffectFoodItem(BREAD_BLOCK.get(), getFoodItemSettings(PlatformHelper.getBreadNutrition(), PlatformHelper.getBreadSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getBreadEffectDuration())));
    public static final RegistrySupplier<Item> BAGUETTE = registerItem("baguette", () -> new PlaceableEffectFoodItem(BAGUETTE_BLOCK.get(), getFoodItemSettings(PlatformHelper.getBaguetteNutrition(), PlatformHelper.getBaguetteSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getBaguetteEffectDuration())));
    public static final RegistrySupplier<Item> TOAST = registerItem("toast", () -> new PlaceableEffectFoodItem(TOAST_BLOCK.get(), getFoodItemSettings(PlatformHelper.getToastNutrition(), PlatformHelper.getToastSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getToastEffectDuration())));
    public static final RegistrySupplier<Item> BRAIDED_BREAD = registerItem("braided_bread", () -> new PlaceableEffectFoodItem(BRAIDED_BREAD_BLOCK.get(), getFoodItemSettings(PlatformHelper.getBraidedBreadNutrition(), PlatformHelper.getBraidedBreadSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getBraidedBreadEffectDuration())));
    public static final RegistrySupplier<Item> SANDWICH = registerItem("sandwich", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getSandwichNutrition(), PlatformHelper.getSandwichSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getSandwichEffectDuration()), 4800, false));
    public static final RegistrySupplier<Item> VEGETABLE_SANDWICH = registerItem("vegetable_sandwich", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getVegetableSandwichNutrition(), PlatformHelper.getVegetableSandwichSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getVegetableSandwichEffectDuration()), 4800, false));
    public static final RegistrySupplier<Item> GRILLED_SALMON_SANDWICH = registerItem("grilled_salmon_sandwich", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getGrilledSalmonSandwichNutrition(), PlatformHelper.getGrilledSalmonSandwichSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getGrilledSalmonSandwichEffectDuration()), 4800, false));
    public static final RegistrySupplier<Item> GRILLED_BACON_SANDWICH = registerItem("grilled_bacon_sandwich", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getGrilledBaconSandwichNutrition(), PlatformHelper.getGrilledBaconSandwichSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getGrilledBaconSandwichEffectDuration()), 6000, false));
    public static final RegistrySupplier<Item> BREAD_WITH_JAM = registerItem("bread_with_jam", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getBreadWithJamNutrition(), PlatformHelper.getBreadWithJamSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getBreadWithJamEffectDuration()), 2500, false));
    public static final RegistrySupplier<Item> STRAWBERRY_CAKE_SLICE = registerItem("strawberry_cake_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getStrawberryCakeSliceNutrition(), PlatformHelper.getStrawberryCakeSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getStrawberryCakeSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getStrawberryCakeSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> SWEETBERRY_CAKE_SLICE = registerItem("sweetberry_cake_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getSweetberryCakeSliceNutrition(), PlatformHelper.getSweetberryCakeSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getSweetberryCakeSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getSweetberryCakeSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> CHOCOLATE_CAKE_SLICE = registerItem("chocolate_cake_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getChocolateCakeSliceNutrition(), PlatformHelper.getChocolateCakeSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateCakeSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateCakeSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> CHOCOLATE_GATEAU_SLICE = registerItem("chocolate_gateau_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getChocolateGateauSliceNutrition(), PlatformHelper.getChocolateGateauSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateGateauSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateGateauSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> BUNDT_CAKE_SLICE = registerItem("bundt_cake_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getBundtCakeSliceNutrition(), PlatformHelper.getBundtCakeSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getBundtCakeSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getBundtCakeSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> LINZER_TART_SLICE = registerItem("linzer_tart_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getLinzerTartSliceNutrition(), PlatformHelper.getLinzerTartSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getLinzerTartSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getLinzerTartSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> APPLE_PIE_SLICE = registerItem("apple_pie_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getApplePieSliceNutrition(), PlatformHelper.getApplePieSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getApplePieSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getApplePieSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> GLOWBERRY_PIE_SLICE = registerItem("glowberry_pie_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getGlowberryPieSliceNutrition(), PlatformHelper.getGlowberryPieSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getGlowberryPieSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getGlowberryPieSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> CHOCOLATE_TART_SLICE = registerItem("chocolate_tart_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getChocolateTartSliceNutrition(), PlatformHelper.getChocolateTartSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateTartSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateTartSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> PUDDING_SLICE = registerItem("pudding_slice", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getPuddingSliceNutrition(), PlatformHelper.getPuddingSliceSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getPuddingSliceEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getPuddingSliceEffectDuration(), false));
    public static final RegistrySupplier<Item> STRAWBERRY_GLAZED_COOKIE = registerItem("strawberry_glazed_cookie", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getStrawberryGlazedCookieNutrition(), PlatformHelper.getStrawberryGlazedCookieSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getStrawberryGlazedCookieEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getStrawberryGlazedCookieEffectDuration(), false));
    public static final RegistrySupplier<Item> SWEETBERRY_GLAZED_COOKIE = registerItem("sweetberry_glazed_cookie", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getSweetberryGlazedCookieNutrition(), PlatformHelper.getSweetberryGlazedCookieSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getSweetberryGlazedCookieEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getSweetberryGlazedCookieEffectDuration(), false));
    public static final RegistrySupplier<Item> CHOCOLATE_GLAZED_COOKIE = registerItem("chocolate_glazed_cookie", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getChocolateGlazedCookieNutrition(), PlatformHelper.getChocolateGlazedCookieSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateGlazedCookieEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateGlazedCookieEffectDuration(), false));
    public static final RegistrySupplier<Item> STRAWBERRY_CUPCAKE = registerItem("strawberry_cupcake", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getStrawberryCupcakeNutrition(), PlatformHelper.getStrawberryCupcakeSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getStrawberryCupcakeEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getStrawberryCupcakeEffectDuration(), false));
    public static final RegistrySupplier<Item> SWEETBERRY_CUPCAKE = registerItem("sweetberry_cupcake", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getSweetberryCupcakeNutrition(), PlatformHelper.getSweetberryCupcakeSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getSweetberryCupcakeEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getSweetberryCupcakeEffectDuration(), false));
    public static final RegistrySupplier<Item> APPLE_CUPCAKE = registerItem("apple_cupcake", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getAppleCupcakeNutrition(), PlatformHelper.getAppleCupcakeSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getAppleCupcakeEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getAppleCupcakeEffectDuration(), false));
    public static final RegistrySupplier<Item> CORNET = registerItem("cornet", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getCornetNutrition(), PlatformHelper.getCornetSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getCornetEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getCornetEffectDuration(), false));
    public static final RegistrySupplier<Item> JAM_ROLL = registerItem("jam_roll", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getJamRollNutrition(), PlatformHelper.getJamRollSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getJamRollEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getJamRollEffectDuration(), false));
    public static final RegistrySupplier<Item> CHOCOLATE_TRUFFLE = registerItem("chocolate_truffle", () -> new SugarRushEffectItem(getFoodItemSettings(PlatformHelper.getChocolateTruffleNutrition(), PlatformHelper.getChocolateTruffleSaturation(), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateTruffleEffectDuration()), MobEffectRegistry.SUGAR_RUSH, PlatformHelper.getChocolateTruffleEffectDuration(), false));
    public static final RegistrySupplier<Item> MISSLILITU_BISCUIT = registerItem("misslilitu_biscuit", () -> new EffectFoodItem(getFoodItemSettings(PlatformHelper.getMisslilituBiscuitNutrition(), PlatformHelper.getMisslilituBiscuitSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getMisslilituBiscuitEffectDuration()), 4200, false));
    public static final RegistrySupplier<Item> WAFFLE = registerItem("waffle", () -> new PlaceableEffectFoodItem(WAFFLE_BLOCK.get(), getFoodItemSettings(PlatformHelper.getWaffleNutrition(), PlatformHelper.getWaffleSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getWaffleEffectDuration())));
    public static final RegistrySupplier<Item> BUN = registerItem("bun", () -> new PlaceableEffectFoodItem(BUN_BLOCK.get(), getFoodItemSettings(PlatformHelper.getBunNutrition(), PlatformHelper.getBunSaturation(), MobEffectRegistry.VITALITY, PlatformHelper.getBunEffectDuration())));
    public static final RegistrySupplier<Block> CHOCOLATE_GATEAU = registerWithItem("chocolate_gateau", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight), PieType.CHOCOLATE_GATEAU, CHOCOLATE_GATEAU_SLICE));
    public static final RegistrySupplier<Block> CHOCOLATE_TART = registerWithItem("chocolate_tart", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight), PieType.CHOCOLATE_TART, CHOCOLATE_TART_SLICE));
    public static final RegistrySupplier<Block> BLANK_CAKE = registerWithoutItem("blank_cake", () -> new BlankCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).forceSolidOn().dynamicShape()));
    public static final RegistrySupplier<Block> APPLE_CUPCAKE_BLOCK = registerWithoutItem("apple_cupcake_block", () -> new CupcakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> SWEETBERRY_CUPCAKE_BLOCK = registerWithoutItem("sweetberry_cupcake_block", () -> new CupcakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> STRAWBERRY_CUPCAKE_BLOCK = registerWithoutItem("strawberry_cupcake_block", () -> new CupcakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> CHOCOLATE_COOKIE_BLOCK = registerWithoutItem("chocolate_cookie_block", () -> new CookieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> SWEETBERRY_COOKIE_BLOCK = registerWithoutItem("sweetberry_cookie_block", () -> new CookieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> STRAWBERRY_COOKIE_BLOCK = registerWithoutItem("strawberry_cookie_block", () -> new CookieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> STRAWBERRY_CAKE = registerWithItem("strawberry_cake", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight), PieType.CAKE, ObjectRegistry.STRAWBERRY_CAKE_SLICE));
    public static final RegistrySupplier<Block> SWEETBERRY_CAKE = registerWithItem("sweetberry_cake", () -> new PieBlock((BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight)), PieType.CAKE, ObjectRegistry.SWEETBERRY_CAKE_SLICE));
    public static final RegistrySupplier<Block> CHOCOLATE_CAKE = registerWithItem("chocolate_cake", () -> new PieBlock((BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight)), PieType.CAKE, ObjectRegistry.CHOCOLATE_CAKE_SLICE));
    public static final RegistrySupplier<Block> BUNDT_CAKE = registerWithItem("bundt_cake", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight), PieType.BUNDT_CAKE, ObjectRegistry.BUNDT_CAKE_SLICE));
    public static final RegistrySupplier<Block> LINZER_TART = registerWithItem("linzer_tart", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight), PieType.LINZER_TART, ObjectRegistry.LINZER_TART_SLICE));
    public static final RegistrySupplier<Block> APPLE_PIE = registerWithItem("apple_pie", () -> new PieBlock((BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight)), PieType.APPLE_PIE, ObjectRegistry.APPLE_PIE_SLICE));
    public static final RegistrySupplier<Block> GLOWBERRY_TART = registerWithItem("glowberry_tart", () -> new PieBlock((BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight)), PieType.GLOWBERRY_TART, ObjectRegistry.GLOWBERRY_PIE_SLICE));
    public static final RegistrySupplier<Block> PUDDING = registerWithItem("pudding", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).lightLevel(PieBlock::candleLight), PieType.PUDDING, ObjectRegistry.PUDDING_SLICE));

    public static void init() {
        BLOCKS.register();
        ITEMS.register();
    }

    private static VoxelShape[] createWaffleShapes() {
        VoxelShape base = Block.box(3, 0, 3, 13, 2, 13);
        VoxelShape second = Shapes.or(base, Block.box(2, 2, 4, 12, 4, 14));
        VoxelShape layered = Shapes.or(base, Block.box(1, 2, 4, 11, 4, 14), Block.box(2, 4, 3, 12, 6, 13), Block.box(3, 6, 2, 13, 8, 12));
        return new VoxelShape[]{
                base,
                second,
                Shapes.or(second, Block.box(3, 4, 3, 13, 6, 13)),
                layered,
                layered,
                Shapes.or(layered, Block.box(2, 8, 4, 12, 10, 14), Block.box(1, 10, 3, 11, 12, 13)),
                Shapes.or(layered, Block.box(2, 8, 4, 12, 10, 14), Block.box(1, 10, 3, 11, 12, 13), Block.box(2, 12, 3, 12, 14, 13)),
                Shapes.or(layered, Block.box(2, 8, 4, 12, 10, 14), Block.box(1, 10, 3, 11, 12, 13), Block.box(2, 12, 3, 12, 14, 13), Block.box(3, 14, 4, 13, 16, 14))
        };
    }

    private static VoxelShape[] createBaguetteShapes() {
        VoxelShape row = Shapes.or(Block.box(2, 0, 1, 5, 3, 15), Block.box(7, 0, 1, 10, 3, 15), Block.box(12, 0, 1, 15, 3, 15));
        return new VoxelShape[]{
                Block.box(6.5, 0, 1, 9.5, 3, 15),
                Shapes.or(Block.box(3, 0, 1, 6, 3, 15), Block.box(10, 0, 1, 13, 3, 15)),
                row,
                Shapes.or(row, Block.box(1, 3, 7, 15, 6, 10))
        };
    }

    private static VoxelShape jar(double x, double z) {
        return Shapes.or(Block.box(x, 0, z, x + 6, 8, z + 6), Block.box(x + 1, 8, z + 1, x + 5, 9, z + 5));
    }

    private static VoxelShape[] createJarShapes() {
        return new VoxelShape[]{
                jar(5, 5),
                Shapes.or(jar(5, 1), jar(5, 9)),
                Shapes.or(jar(8, 1), jar(8, 9), jar(1, 5)),
                Shapes.or(jar(8, 1), jar(8, 9), jar(1, 9), jar(1, 1))
        };
    }

    private static VoxelShape[] createBunShapes() {
        VoxelShape three = Shapes.or(Block.box(7, 0, 2.5, 13, 4, 7.5), Block.box(8, 0, 9.5, 14, 4, 14.5), Block.box(2, 0, 5.5, 7, 4, 11.5));
        return new VoxelShape[]{
                Block.box(5, 0, 5.5, 11, 4, 10.5),
                Shapes.or(Block.box(6, 0, 2.5, 12, 4, 7.5), Block.box(5, 0, 9.5, 11, 4, 14.5)),
                three,
                Shapes.or(three, Block.box(5, 4, 5.5, 11, 8, 10.5))
        };
    }

    private static VoxelShape[] createCrustyBreadShapes() {
        return new VoxelShape[]{
                Block.box(4, 0, 4, 12, 5, 12),
                Shapes.or(Block.box(7, 0, 4, 15, 5, 12), Block.box(5, 5, 6, 13, 10, 14)),
                Shapes.or(Block.box(7, 0, 4, 15, 5, 12), Block.box(7, 5, 6, 15, 10, 14), Block.box(2, 0, 8, 7, 8, 16))
        };
    }

    private static VoxelShape[] createBreadShapes() {
        return new VoxelShape[]{
                Block.box(5.5, 0, 3, 10.5, 5, 13),
                Shapes.or(Block.box(2.5, 0, 3, 7.5, 5, 13), Block.box(8.5, 0, 3, 13.5, 5, 13)),
                Shapes.or(Block.box(2, 0, 3, 7, 5, 13), Block.box(9, 0, 3, 14, 5, 13), Block.box(3, 5, 5, 13, 10, 10))
        };
    }

    private static VoxelShape[] createBraidedBreadShapes() {
        VoxelShape pair = Shapes.or(Block.box(2, 0, 3, 7, 4, 13), Block.box(9, 0, 3, 14, 4, 13));
        return new VoxelShape[]{
                Block.box(5.5, 0, 3, 10.5, 4, 13),
                pair,
                Shapes.or(pair, Block.box(5, 4, 3, 10, 8, 13))
        };
    }

    private static VoxelShape toast(double x, double y) {
        return Shapes.or(Block.box(x, y, 3, x + 4, y + 4, 13), Block.box(x - 1, y + 4, 3, x + 5, y + 6, 13));
    }

    private static VoxelShape[] createToastShapes() {
        VoxelShape pair = Shapes.or(toast(10, 0), toast(2, 0));
        return new VoxelShape[]{
                toast(6, 0),
                pair,
                Shapes.or(pair, Block.box(3, 6, 6, 13, 10, 10), Block.box(3, 10, 5, 13, 12, 11))
        };
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return RegistryUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, Bakery.identifier(name), block);
    }

    private static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> blockSupplier, Supplier<Item> craftingRemainderSupplier) {
        RegistrySupplier<T> registrySupplier = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(registrySupplier.get(), getSettings().craftRemainder(craftingRemainderSupplier.get())));
        return registrySupplier;
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return RegistryUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, Bakery.identifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return RegistryUtil.registerItem(ITEMS, ITEM_REGISTRAR, Bakery.identifier(path), itemSupplier);
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, RegistrySupplier<MobEffect> effect, int duration) {
        return getFoodItemSettings(nutrition, saturationMod, effect, duration, false, false);
    }

    @SuppressWarnings("all")
    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, RegistrySupplier<MobEffect> effect, int duration, boolean alwaysEat, boolean fast) {
        return getSettings().food(createFood(nutrition, saturationMod, BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect.get()), duration, alwaysEat, fast));
    }

    private static FoodProperties createFood(int nutrition, float saturationMod, Holder<MobEffect> effect, int duration, boolean alwaysEat, boolean fast) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationMod);
        if (alwaysEat) food.alwaysEdible();
        if (fast) food.fast();
        if (effect != null) food.effect(new MobEffectInstance(effect, duration), 1.0f);
        return food.build();
    }
}
