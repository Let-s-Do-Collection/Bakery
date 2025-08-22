package net.satisfy.bakery.forge.core.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class BakeryForgeConfig {
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.BooleanValue GIVE_EFFECT;
    public static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIP;

    public static final ForgeConfigSpec.IntValue CAKE_DOUGH_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CAKE_DOUGH_SATURATION;
    public static final ForgeConfigSpec.IntValue SWEET_DOUGH_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue SWEET_DOUGH_SATURATION;
    public static final ForgeConfigSpec.IntValue CROISSANT_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CROISSANT_SATURATION;
    public static final ForgeConfigSpec.IntValue CRUSTY_BREAD_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CRUSTY_BREAD_SATURATION;
    public static final ForgeConfigSpec.IntValue BREAD_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue BREAD_SATURATION;
    public static final ForgeConfigSpec.IntValue BAGUETTE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue BAGUETTE_SATURATION;
    public static final ForgeConfigSpec.IntValue TOAST_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue TOAST_SATURATION;
    public static final ForgeConfigSpec.IntValue BRAIDED_BREAD_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue BRAIDED_BREAD_SATURATION;
    public static final ForgeConfigSpec.IntValue SANDWICH_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue SANDWICH_SATURATION;
    public static final ForgeConfigSpec.IntValue VEGETABLE_SANDWICH_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue VEGETABLE_SANDWICH_SATURATION;
    public static final ForgeConfigSpec.IntValue GRILLED_SALMON_SANDWICH_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue GRILLED_SALMON_SANDWICH_SATURATION;
    public static final ForgeConfigSpec.IntValue GRILLED_BACON_SANDWICH_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue GRILLED_BACON_SANDWICH_SATURATION;
    public static final ForgeConfigSpec.IntValue BREAD_WITH_JAM_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue BREAD_WITH_JAM_SATURATION;
    public static final ForgeConfigSpec.IntValue STRAWBERRY_CAKE_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue STRAWBERRY_CAKE_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue SWEETBERRY_CAKE_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue SWEETBERRY_CAKE_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue CHOCOLATE_CAKE_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CHOCOLATE_CAKE_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue CHOCOLATE_GATEAU_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CHOCOLATE_GATEAU_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue BUNDT_CAKE_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue BUNDT_CAKE_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue LINZER_TART_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue LINZER_TART_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue APPLE_PIE_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue APPLE_PIE_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue GLOWBERRY_PIE_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue GLOWBERRY_PIE_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue CHOCOLATE_TART_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CHOCOLATE_TART_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue PUDDING_SLICE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue PUDDING_SLICE_SATURATION;
    public static final ForgeConfigSpec.IntValue STRAWBERRY_GLAZED_COOKIE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue STRAWBERRY_GLAZED_COOKIE_SATURATION;
    public static final ForgeConfigSpec.IntValue SWEETBERRY_GLAZED_COOKIE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue SWEETBERRY_GLAZED_COOKIE_SATURATION;
    public static final ForgeConfigSpec.IntValue CHOCOLATE_GLAZED_COOKIE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CHOCOLATE_GLAZED_COOKIE_SATURATION;
    public static final ForgeConfigSpec.IntValue STRAWBERRY_CUPCAKE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue STRAWBERRY_CUPCAKE_SATURATION;
    public static final ForgeConfigSpec.IntValue SWEETBERRY_CUPCAKE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue SWEETBERRY_CUPCAKE_SATURATION;
    public static final ForgeConfigSpec.IntValue APPLE_CUPCAKE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue APPLE_CUPCAKE_SATURATION;
    public static final ForgeConfigSpec.IntValue CORNET_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CORNET_SATURATION;
    public static final ForgeConfigSpec.IntValue JAM_ROLL_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue JAM_ROLL_SATURATION;
    public static final ForgeConfigSpec.IntValue BUN_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue BUN_SATURATION;
    public static final ForgeConfigSpec.IntValue WAFFLE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue WAFFLE_SATURATION;
    public static final ForgeConfigSpec.IntValue CHOCOLATE_TRUFFLE_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue CHOCOLATE_TRUFFLE_SATURATION;
    public static final ForgeConfigSpec.IntValue MISSLILITU_BISCUIT_NUTRITION;
    public static final ForgeConfigSpec.DoubleValue MISSLILITU_BISCUIT_SATURATION;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Banner");
        GIVE_EFFECT = COMMON_BUILDER
                .comment("Set to false to disable the banner's effect.")
                .define("giveEffect", true);

        SHOW_TOOLTIP = COMMON_BUILDER
                .comment("Set to false to hide the banner's tooltip. If giveEffect is false, showTooltip is automatically false.")
                .define("showTooltip", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Nutrition");

        CAKE_DOUGH_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Cake Dough")
                .defineInRange("cakeDoughNutrition", 5, 0, 20);
        CAKE_DOUGH_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Cake Dough")
                .defineInRange("cakeDoughSaturation", 0.6, 0.0, 10.0);

        SWEET_DOUGH_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Sweet Dough")
                .defineInRange("sweetDoughNutrition", 5, 0, 20);
        SWEET_DOUGH_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Sweet Dough")
                .defineInRange("sweetDoughSaturation", 0.6, 0.0, 10.0);

        CROISSANT_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Croissant")
                .defineInRange("croissantNutrition", 5, 0, 20);
        CROISSANT_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Croissant")
                .defineInRange("croissantSaturation", 0.6, 0.0, 10.0);

        CRUSTY_BREAD_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Crusty Bread")
                .defineInRange("crustyBreadNutrition", 5, 0, 20);
        CRUSTY_BREAD_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Crusty Bread")
                .defineInRange("crustyBreadSaturation", 1.2, 0.0, 10.0);

        BREAD_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Bread")
                .defineInRange("breadNutrition", 5, 0, 20);
        BREAD_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Bread")
                .defineInRange("breadSaturation", 1.2, 0.0, 10.0);

        BAGUETTE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Baguette")
                .defineInRange("baguetteNutrition", 5, 0, 20);
        BAGUETTE_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Baguette")
                .defineInRange("baguetteSaturation", 1.2, 0.0, 10.0);

        TOAST_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Toast")
                .defineInRange("toastNutrition", 3, 0, 20);
        TOAST_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Toast")
                .defineInRange("toastSaturation", 0.8, 0.0, 10.0);

        BRAIDED_BREAD_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Braided Bread")
                .defineInRange("braidedBreadNutrition", 5, 0, 20);
        BRAIDED_BREAD_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Braided Bread")
                .defineInRange("braidedBreadSaturation", 1.2, 0.0, 10.0);

        SANDWICH_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Sandwich")
                .defineInRange("sandwichNutrition", 7, 0, 20);
        SANDWICH_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Sandwich")
                .defineInRange("sandwichSaturation", 0.7, 0.0, 10.0);

        VEGETABLE_SANDWICH_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Vegetable Sandwich")
                .defineInRange("vegetableSandwichNutrition", 8, 0, 20);
        VEGETABLE_SANDWICH_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Vegetable Sandwich")
                .defineInRange("vegetableSandwichSaturation", 0.6, 0.0, 10.0);

        GRILLED_SALMON_SANDWICH_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Grilled Salmon Sandwich")
                .defineInRange("grilledSalmonSandwichNutrition", 6, 0, 20);
        GRILLED_SALMON_SANDWICH_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Grilled Salmon Sandwich")
                .defineInRange("grilledSalmonSandwichSaturation", 0.8, 0.0, 10.0);

        GRILLED_BACON_SANDWICH_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Grilled Bacon Sandwich")
                .defineInRange("grilledBaconSandwichNutrition", 7, 0, 20);
        GRILLED_BACON_SANDWICH_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Grilled Bacon Sandwich")
                .defineInRange("grilledBaconSandwichSaturation", 0.7, 0.0, 10.0);

        BREAD_WITH_JAM_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Bread with Jam")
                .defineInRange("breadWithJamNutrition", 5, 0, 20);
        BREAD_WITH_JAM_SATURATION = COMMON_BUILDER
                .comment("Saturation modifier for Bread with Jam")
                .defineInRange("breadWithJamSaturation", 0.5, 0.0, 10.0);

        STRAWBERRY_CAKE_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Strawberry Cake Slice")
                .defineInRange("strawberryCakeSliceNutrition", 5, 0, 20);
        STRAWBERRY_CAKE_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("strawberryCakeSliceSaturation", 0.7, 0.0, 10.0);

        SWEETBERRY_CAKE_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Sweetberry Cake Slice")
                .defineInRange("sweetberryCakeSliceNutrition", 5, 0, 20);
        SWEETBERRY_CAKE_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("sweetberryCakeSliceSaturation", 0.7, 0.0, 10.0);

        CHOCOLATE_CAKE_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Chocolate Cake Slice")
                .defineInRange("chocolateCakeSliceNutrition", 5, 0, 20);
        CHOCOLATE_CAKE_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("chocolateCakeSliceSaturation", 0.7, 0.0, 10.0);

        CHOCOLATE_GATEAU_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Chocolate Gateau Slice")
                .defineInRange("chocolateGateauSliceNutrition", 5, 0, 20);
        CHOCOLATE_GATEAU_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("chocolateGateauSliceSaturation", 0.7, 0.0, 10.0);

        BUNDT_CAKE_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Bundt Cake Slice")
                .defineInRange("bundtCakeSliceNutrition", 5, 0, 20);
        BUNDT_CAKE_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("bundtCakeSliceSaturation", 0.7, 0.0, 10.0);

        LINZER_TART_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Linzer Tart Slice")
                .defineInRange("linzerTartSliceNutrition", 5, 0, 20);
        LINZER_TART_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("linzerTartSliceSaturation", 0.7, 0.0, 10.0);

        APPLE_PIE_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Apple Pie Slice")
                .defineInRange("applePieSliceNutrition", 5, 0, 20);
        APPLE_PIE_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("applePieSliceSaturation", 0.7, 0.0, 10.0);

        GLOWBERRY_PIE_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Glowberry Pie Slice")
                .defineInRange("glowberryPieSliceNutrition", 5, 0, 20);
        GLOWBERRY_PIE_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("glowberryPieSliceSaturation", 0.7, 0.0, 10.0);

        CHOCOLATE_TART_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Chocolate Tart Slice")
                .defineInRange("chocolateTartSliceNutrition", 5, 0, 20);
        CHOCOLATE_TART_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("chocolateTartSliceSaturation", 0.7, 0.0, 10.0);

        PUDDING_SLICE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Pudding Slice")
                .defineInRange("puddingSliceNutrition", 5, 0, 20);
        PUDDING_SLICE_SATURATION = COMMON_BUILDER
                .defineInRange("puddingSliceSaturation", 0.7, 0.0, 10.0);

        STRAWBERRY_GLAZED_COOKIE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Strawberry Glazed Cookie")
                .defineInRange("strawberryGlazedCookieNutrition", 3, 0, 20);
        STRAWBERRY_GLAZED_COOKIE_SATURATION = COMMON_BUILDER
                .defineInRange("strawberryGlazedCookieSaturation", 0.5, 0.0, 10.0);

        SWEETBERRY_GLAZED_COOKIE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Sweetberry Glazed Cookie")
                .defineInRange("sweetberryGlazedCookieNutrition", 3, 0, 20);
        SWEETBERRY_GLAZED_COOKIE_SATURATION = COMMON_BUILDER
                .defineInRange("sweetberryGlazedCookieSaturation", 0.5, 0.0, 10.0);

        CHOCOLATE_GLAZED_COOKIE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Chocolate Glazed Cookie")
                .defineInRange("chocolateGlazedCookieNutrition", 3, 0, 20);
        CHOCOLATE_GLAZED_COOKIE_SATURATION = COMMON_BUILDER
                .defineInRange("chocolateGlazedCookieSaturation", 0.5, 0.0, 10.0);

        STRAWBERRY_CUPCAKE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Strawberry Cupcake")
                .defineInRange("strawberryCupcakeNutrition", 3, 0, 20);
        STRAWBERRY_CUPCAKE_SATURATION = COMMON_BUILDER
                .defineInRange("strawberryCupcakeSaturation", 0.5, 0.0, 10.0);

        SWEETBERRY_CUPCAKE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Sweetberry Cupcake")
                .defineInRange("sweetberryCupcakeNutrition", 3, 0, 20);
        SWEETBERRY_CUPCAKE_SATURATION = COMMON_BUILDER
                .defineInRange("sweetberryCupcakeSaturation", 0.5, 0.0, 10.0);

        APPLE_CUPCAKE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Apple Cupcake")
                .defineInRange("appleCupcakeNutrition", 3, 0, 20);
        APPLE_CUPCAKE_SATURATION = COMMON_BUILDER
                .defineInRange("appleCupcakeSaturation", 0.5, 0.0, 10.0);

        CORNET_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Cornet")
                .defineInRange("cornetNutrition", 3, 0, 20);
        CORNET_SATURATION = COMMON_BUILDER
                .defineInRange("cornetSaturation", 0.5, 0.0, 10.0);

        JAM_ROLL_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Jam Roll")
                .defineInRange("jamRollNutrition", 3, 0, 20);
        JAM_ROLL_SATURATION = COMMON_BUILDER
                .defineInRange("jamRollSaturation", 0.5, 0.0, 10.0);

        BUN_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Bun")
                .defineInRange("bunNutrition", 5, 0, 20);
        BUN_SATURATION = COMMON_BUILDER
                .defineInRange("bunSaturation", 1.2, 0.0, 10.0);

        WAFFLE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Waffle")
                .defineInRange("waffleNutrition", 5, 0, 20);
        WAFFLE_SATURATION = COMMON_BUILDER
                .defineInRange("waffleSaturation", 0.5, 0.0, 10.0);

        CHOCOLATE_TRUFFLE_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Chocolate Truffle")
                .defineInRange("chocolateTruffleNutrition", 2, 0, 20);
        CHOCOLATE_TRUFFLE_SATURATION = COMMON_BUILDER
                .defineInRange("chocolateTruffleSaturation", 0.4, 0.0, 10.0);

        MISSLILITU_BISCUIT_NUTRITION = COMMON_BUILDER
                .comment("Nutrition value for Misslilitu Biscuit")
                .defineInRange("misslilituBiscuitNutrition", 6, 0, 20);
        MISSLILITU_BISCUIT_SATURATION = COMMON_BUILDER
                .defineInRange("misslilituBiscuitSaturation", 0.6, 0.0, 10.0);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        spec.setConfig(file);
    }
}
