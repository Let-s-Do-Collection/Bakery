package net.satisfy.bakery.neoforge.core.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.satisfy.bakery.Bakery;

@EventBusSubscriber(modid = Bakery.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class BakeryNeoForgeConfig {
    public static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    // ----- Banner / Tooltip -----
    public static final ModConfigSpec.BooleanValue GIVE_EFFECT;
    public static final ModConfigSpec.BooleanValue SHOW_TOOLTIP;

    // ----- Dough -----
    public static final ModConfigSpec.IntValue CAKE_DOUGH_NUTRITION;
    public static final ModConfigSpec.DoubleValue CAKE_DOUGH_SATURATION;
    public static final ModConfigSpec.IntValue SWEET_DOUGH_NUTRITION;
    public static final ModConfigSpec.DoubleValue SWEET_DOUGH_SATURATION;

    // ----- Breads -----
    public static final ModConfigSpec.IntValue CROISSANT_NUTRITION;
    public static final ModConfigSpec.DoubleValue CROISSANT_SATURATION;
    public static final ModConfigSpec.IntValue CRUSTY_BREAD_NUTRITION;
    public static final ModConfigSpec.DoubleValue CRUSTY_BREAD_SATURATION;
    public static final ModConfigSpec.IntValue BREAD_NUTRITION;
    public static final ModConfigSpec.DoubleValue BREAD_SATURATION;
    public static final ModConfigSpec.IntValue BAGUETTE_NUTRITION;
    public static final ModConfigSpec.DoubleValue BAGUETTE_SATURATION;
    public static final ModConfigSpec.IntValue TOAST_NUTRITION;
    public static final ModConfigSpec.DoubleValue TOAST_SATURATION;
    public static final ModConfigSpec.IntValue BRAIDED_BREAD_NUTRITION;
    public static final ModConfigSpec.DoubleValue BRAIDED_BREAD_SATURATION;

    // ----- Sandwiches -----
    public static final ModConfigSpec.IntValue SANDWICH_NUTRITION;
    public static final ModConfigSpec.DoubleValue SANDWICH_SATURATION;
    public static final ModConfigSpec.IntValue VEGETABLE_SANDWICH_NUTRITION;
    public static final ModConfigSpec.DoubleValue VEGETABLE_SANDWICH_SATURATION;
    public static final ModConfigSpec.IntValue GRILLED_SALMON_SANDWICH_NUTRITION;
    public static final ModConfigSpec.DoubleValue GRILLED_SALMON_SANDWICH_SATURATION;
    public static final ModConfigSpec.IntValue GRILLED_BACON_SANDWICH_NUTRITION;
    public static final ModConfigSpec.DoubleValue GRILLED_BACON_SANDWICH_SATURATION;
    public static final ModConfigSpec.IntValue BREAD_WITH_JAM_NUTRITION;
    public static final ModConfigSpec.DoubleValue BREAD_WITH_JAM_SATURATION;

    // ----- Cakes & Tarts (Slices) -----
    public static final ModConfigSpec.IntValue STRAWBERRY_CAKE_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue STRAWBERRY_CAKE_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue SWEETBERRY_CAKE_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue SWEETBERRY_CAKE_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue CHOCOLATE_CAKE_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue CHOCOLATE_CAKE_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue CHOCOLATE_GATEAU_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue CHOCOLATE_GATEAU_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue BUNDT_CAKE_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue BUNDT_CAKE_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue LINZER_TART_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue LINZER_TART_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue APPLE_PIE_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue APPLE_PIE_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue GLOWBERRY_PIE_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue GLOWBERRY_PIE_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue CHOCOLATE_TART_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue CHOCOLATE_TART_SLICE_SATURATION;
    public static final ModConfigSpec.IntValue PUDDING_SLICE_NUTRITION;
    public static final ModConfigSpec.DoubleValue PUDDING_SLICE_SATURATION;

    // ----- Cookies -----
    public static final ModConfigSpec.IntValue STRAWBERRY_GLAZED_COOKIE_NUTRITION;
    public static final ModConfigSpec.DoubleValue STRAWBERRY_GLAZED_COOKIE_SATURATION;
    public static final ModConfigSpec.IntValue SWEETBERRY_GLAZED_COOKIE_NUTRITION;
    public static final ModConfigSpec.DoubleValue SWEETBERRY_GLAZED_COOKIE_SATURATION;
    public static final ModConfigSpec.IntValue CHOCOLATE_GLAZED_COOKIE_NUTRITION;
    public static final ModConfigSpec.DoubleValue CHOCOLATE_GLAZED_COOKIE_SATURATION;

    // ----- Cupcakes -----
    public static final ModConfigSpec.IntValue STRAWBERRY_CUPCAKE_NUTRITION;
    public static final ModConfigSpec.DoubleValue STRAWBERRY_CUPCAKE_SATURATION;
    public static final ModConfigSpec.IntValue SWEETBERRY_CUPCAKE_NUTRITION;
    public static final ModConfigSpec.DoubleValue SWEETBERRY_CUPCAKE_SATURATION;
    public static final ModConfigSpec.IntValue APPLE_CUPCAKE_NUTRITION;
    public static final ModConfigSpec.DoubleValue APPLE_CUPCAKE_SATURATION;

    // ----- Pastries & Others -----
    public static final ModConfigSpec.IntValue CORNET_NUTRITION;
    public static final ModConfigSpec.DoubleValue CORNET_SATURATION;
    public static final ModConfigSpec.IntValue JAM_ROLL_NUTRITION;
    public static final ModConfigSpec.DoubleValue JAM_ROLL_SATURATION;
    public static final ModConfigSpec.IntValue BUN_NUTRITION;
    public static final ModConfigSpec.DoubleValue BUN_SATURATION;
    public static final ModConfigSpec.IntValue WAFFLE_NUTRITION;
    public static final ModConfigSpec.DoubleValue WAFFLE_SATURATION;
    public static final ModConfigSpec.IntValue CHOCOLATE_TRUFFLE_NUTRITION;
    public static final ModConfigSpec.DoubleValue CHOCOLATE_TRUFFLE_SATURATION;
    public static final ModConfigSpec.IntValue MISSLILITU_BISCUIT_NUTRITION;
    public static final ModConfigSpec.DoubleValue MISSLILITU_BISCUIT_SATURATION;

    public static final ModConfigSpec COMMON_CONFIG;

    static {
        // Banner
        COMMON_BUILDER.push("Banner");
        GIVE_EFFECT  = COMMON_BUILDER.define("giveEffect", true);
        SHOW_TOOLTIP = COMMON_BUILDER.define("showTooltip", true);
        COMMON_BUILDER.pop();

        // Nutrition
        COMMON_BUILDER.push("Nutrition");

        // Dough
        CAKE_DOUGH_NUTRITION   = COMMON_BUILDER.defineInRange("cakeDoughNutrition", 5, 0, 20);
        CAKE_DOUGH_SATURATION  = COMMON_BUILDER.defineInRange("cakeDoughSaturation", 0.6, 0.0, 10.0);
        SWEET_DOUGH_NUTRITION  = COMMON_BUILDER.defineInRange("sweetDoughNutrition", 5, 0, 20);
        SWEET_DOUGH_SATURATION = COMMON_BUILDER.defineInRange("sweetDoughSaturation", 0.6, 0.0, 10.0);

        // Breads
        CROISSANT_NUTRITION      = COMMON_BUILDER.defineInRange("croissantNutrition", 5, 0, 20);
        CROISSANT_SATURATION     = COMMON_BUILDER.defineInRange("croissantSaturation", 0.6, 0.0, 10.0);
        CRUSTY_BREAD_NUTRITION   = COMMON_BUILDER.defineInRange("crustyBreadNutrition", 5, 0, 20);
        CRUSTY_BREAD_SATURATION  = COMMON_BUILDER.defineInRange("crustyBreadSaturation", 1.2, 0.0, 10.0);
        BREAD_NUTRITION          = COMMON_BUILDER.defineInRange("breadNutrition", 5, 0, 20);
        BREAD_SATURATION         = COMMON_BUILDER.defineInRange("breadSaturation", 1.2, 0.0, 10.0);
        BAGUETTE_NUTRITION       = COMMON_BUILDER.defineInRange("baguetteNutrition", 5, 0, 20);
        BAGUETTE_SATURATION      = COMMON_BUILDER.defineInRange("baguetteSaturation", 1.2, 0.0, 10.0);
        TOAST_NUTRITION          = COMMON_BUILDER.defineInRange("toastNutrition", 3, 0, 20);
        TOAST_SATURATION         = COMMON_BUILDER.defineInRange("toastSaturation", 0.8, 0.0, 10.0);
        BRAIDED_BREAD_NUTRITION  = COMMON_BUILDER.defineInRange("braidedBreadNutrition", 5, 0, 20);
        BRAIDED_BREAD_SATURATION = COMMON_BUILDER.defineInRange("braidedBreadSaturation", 1.2, 0.0, 10.0);

        // Sandwiches
        SANDWICH_NUTRITION                 = COMMON_BUILDER.defineInRange("sandwichNutrition", 7, 0, 20);
        SANDWICH_SATURATION                = COMMON_BUILDER.defineInRange("sandwichSaturation", 0.7, 0.0, 10.0);
        VEGETABLE_SANDWICH_NUTRITION       = COMMON_BUILDER.defineInRange("vegetableSandwichNutrition", 8, 0, 20);
        VEGETABLE_SANDWICH_SATURATION      = COMMON_BUILDER.defineInRange("vegetableSandwichSaturation", 0.6, 0.0, 10.0);
        GRILLED_SALMON_SANDWICH_NUTRITION  = COMMON_BUILDER.defineInRange("grilledSalmonSandwichNutrition", 6, 0, 20);
        GRILLED_SALMON_SANDWICH_SATURATION = COMMON_BUILDER.defineInRange("grilledSalmonSandwichSaturation", 0.8, 0.0, 10.0);
        GRILLED_BACON_SANDWICH_NUTRITION   = COMMON_BUILDER.defineInRange("grilledBaconSandwichNutrition", 7, 0, 20);
        GRILLED_BACON_SANDWICH_SATURATION  = COMMON_BUILDER.defineInRange("grilledBaconSandwichSaturation", 0.7, 0.0, 10.0);
        BREAD_WITH_JAM_NUTRITION           = COMMON_BUILDER.defineInRange("breadWithJamNutrition", 5, 0, 20);
        BREAD_WITH_JAM_SATURATION          = COMMON_BUILDER.defineInRange("breadWithJamSaturation", 0.5, 0.0, 10.0);

        // Cakes & Tarts (Slices)
        STRAWBERRY_CAKE_SLICE_NUTRITION   = COMMON_BUILDER.defineInRange("strawberryCakeSliceNutrition", 5, 0, 20);
        STRAWBERRY_CAKE_SLICE_SATURATION  = COMMON_BUILDER.defineInRange("strawberryCakeSliceSaturation", 0.7, 0.0, 10.0);
        SWEETBERRY_CAKE_SLICE_NUTRITION   = COMMON_BUILDER.defineInRange("sweetberryCakeSliceNutrition", 5, 0, 20);
        SWEETBERRY_CAKE_SLICE_SATURATION  = COMMON_BUILDER.defineInRange("sweetberryCakeSliceSaturation", 0.7, 0.0, 10.0);
        CHOCOLATE_CAKE_SLICE_NUTRITION    = COMMON_BUILDER.defineInRange("chocolateCakeSliceNutrition", 5, 0, 20);
        CHOCOLATE_CAKE_SLICE_SATURATION   = COMMON_BUILDER.defineInRange("chocolateCakeSliceSaturation", 0.7, 0.0, 10.0);
        CHOCOLATE_GATEAU_SLICE_NUTRITION  = COMMON_BUILDER.defineInRange("chocolateGateauSliceNutrition", 5, 0, 20);
        CHOCOLATE_GATEAU_SLICE_SATURATION = COMMON_BUILDER.defineInRange("chocolateGateauSliceSaturation", 0.7, 0.0, 10.0);
        BUNDT_CAKE_SLICE_NUTRITION        = COMMON_BUILDER.defineInRange("bundtCakeSliceNutrition", 5, 0, 20);
        BUNDT_CAKE_SLICE_SATURATION       = COMMON_BUILDER.defineInRange("bundtCakeSliceSaturation", 0.7, 0.0, 10.0);
        LINZER_TART_SLICE_NUTRITION       = COMMON_BUILDER.defineInRange("linzerTartSliceNutrition", 5, 0, 20);
        LINZER_TART_SLICE_SATURATION      = COMMON_BUILDER.defineInRange("linzerTartSliceSaturation", 0.7, 0.0, 10.0);
        APPLE_PIE_SLICE_NUTRITION         = COMMON_BUILDER.defineInRange("applePieSliceNutrition", 5, 0, 20);
        APPLE_PIE_SLICE_SATURATION        = COMMON_BUILDER.defineInRange("applePieSliceSaturation", 0.7, 0.0, 10.0);
        GLOWBERRY_PIE_SLICE_NUTRITION     = COMMON_BUILDER.defineInRange("glowberryPieSliceNutrition", 5, 0, 20);
        GLOWBERRY_PIE_SLICE_SATURATION    = COMMON_BUILDER.defineInRange("glowberryPieSliceSaturation", 0.7, 0.0, 10.0);
        CHOCOLATE_TART_SLICE_NUTRITION    = COMMON_BUILDER.defineInRange("chocolateTartSliceNutrition", 5, 0, 20);
        CHOCOLATE_TART_SLICE_SATURATION   = COMMON_BUILDER.defineInRange("chocolateTartSliceSaturation", 0.7, 0.0, 10.0);
        PUDDING_SLICE_NUTRITION           = COMMON_BUILDER.defineInRange("puddingSliceNutrition", 5, 0, 20);
        PUDDING_SLICE_SATURATION          = COMMON_BUILDER.defineInRange("puddingSliceSaturation", 0.7, 0.0, 10.0);

        // Cookies
        STRAWBERRY_GLAZED_COOKIE_NUTRITION  = COMMON_BUILDER.defineInRange("strawberryGlazedCookieNutrition", 3, 0, 20);
        STRAWBERRY_GLAZED_COOKIE_SATURATION = COMMON_BUILDER.defineInRange("strawberryGlazedCookieSaturation", 0.5, 0.0, 10.0);
        SWEETBERRY_GLAZED_COOKIE_NUTRITION  = COMMON_BUILDER.defineInRange("sweetberryGlazedCookieNutrition", 3, 0, 20);
        SWEETBERRY_GLAZED_COOKIE_SATURATION = COMMON_BUILDER.defineInRange("sweetberryGlazedCookieSaturation", 0.5, 0.0, 10.0);
        CHOCOLATE_GLAZED_COOKIE_NUTRITION   = COMMON_BUILDER.defineInRange("chocolateGlazedCookieNutrition", 3, 0, 20);
        CHOCOLATE_GLAZED_COOKIE_SATURATION  = COMMON_BUILDER.defineInRange("chocolateGlazedCookieSaturation", 0.5, 0.0, 10.0);

        // Cupcakes
        STRAWBERRY_CUPCAKE_NUTRITION = COMMON_BUILDER.defineInRange("strawberryCupcakeNutrition", 3, 0, 20);
        STRAWBERRY_CUPCAKE_SATURATION = COMMON_BUILDER.defineInRange("strawberryCupcakeSaturation", 0.5, 0.0, 10.0);
        SWEETBERRY_CUPCAKE_NUTRITION = COMMON_BUILDER.defineInRange("sweetberryCupcakeNutrition", 3, 0, 20);
        SWEETBERRY_CUPCAKE_SATURATION = COMMON_BUILDER.defineInRange("sweetberryCupcakeSaturation", 0.5, 0.0, 10.0);
        APPLE_CUPCAKE_NUTRITION = COMMON_BUILDER.defineInRange("appleCupcakeNutrition", 3, 0, 20);
        APPLE_CUPCAKE_SATURATION = COMMON_BUILDER.defineInRange("appleCupcakeSaturation", 0.5, 0.0, 10.0);

        // Pastries & Others
        CORNET_NUTRITION = COMMON_BUILDER.defineInRange("cornetNutrition", 3, 0, 20);
        CORNET_SATURATION = COMMON_BUILDER.defineInRange("cornetSaturation", 0.5, 0.0, 10.0);
        JAM_ROLL_NUTRITION = COMMON_BUILDER.defineInRange("jamRollNutrition", 3, 0, 20);
        JAM_ROLL_SATURATION = COMMON_BUILDER.defineInRange("jamRollSaturation", 0.5, 0.0, 10.0);
        BUN_NUTRITION = COMMON_BUILDER.defineInRange("bunNutrition", 5, 0, 20);
        BUN_SATURATION = COMMON_BUILDER.defineInRange("bunSaturation", 1.2, 0.0, 10.0);
        WAFFLE_NUTRITION = COMMON_BUILDER.defineInRange("waffleNutrition", 5, 0, 20);
        WAFFLE_SATURATION = COMMON_BUILDER.defineInRange("waffleSaturation", 0.5, 0.0, 10.0);
        CHOCOLATE_TRUFFLE_NUTRITION = COMMON_BUILDER.defineInRange("chocolateTruffleNutrition", 2, 0, 20);
        CHOCOLATE_TRUFFLE_SATURATION = COMMON_BUILDER.defineInRange("chocolateTruffleSaturation", 0.4, 0.0, 10.0);
        MISSLILITU_BISCUIT_NUTRITION = COMMON_BUILDER.defineInRange("misslilituBiscuitNutrition", 6, 0, 20);
        MISSLILITU_BISCUIT_SATURATION = COMMON_BUILDER.defineInRange("misslilituBiscuitSaturation", 0.6, 0.0, 10.0);

        COMMON_BUILDER.pop(); // Nutrition

        // Build spec at the very end
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    // ----- Cached values (werden im Event gesetzt) -----
    public static boolean give_effect;
    public static boolean show_tooltip;

    public static int cake_dough_nutrition;
    public static double cake_dough_saturation;
    public static int sweet_dough_nutrition;
    public static double sweet_dough_saturation;

    public static int croissant_nutrition;
    public static double croissant_saturation;
    public static int crusty_bread_nutrition;
    public static double crusty_bread_saturation;
    public static int bread_nutrition;
    public static double bread_saturation;
    public static int baguette_nutrition;
    public static double baguette_saturation;
    public static int toast_nutrition;
    public static double toast_saturation;
    public static int braided_bread_nutrition;
    public static double braided_bread_saturation;

    public static int sandwich_nutrition;
    public static double sandwich_saturation;
    public static int vegetable_sandwich_nutrition;
    public static double vegetable_sandwich_saturation;
    public static int grilled_salmon_sandwich_nutrition;
    public static double grilled_salmon_sandwich_saturation;
    public static int grilled_bacon_sandwich_nutrition;
    public static double grilled_bacon_sandwich_saturation;
    public static int bread_with_jam_nutrition;
    public static double bread_with_jam_saturation;

    public static int strawberry_cake_slice_nutrition;
    public static double strawberry_cake_slice_saturation;
    public static int sweetberry_cake_slice_nutrition;
    public static double sweetberry_cake_slice_saturation;
    public static int chocolate_cake_slice_nutrition;
    public static double chocolate_cake_slice_saturation;
    public static int chocolate_gateau_slice_nutrition;
    public static double chocolate_gateau_slice_saturation;
    public static int bundt_cake_slice_nutrition;
    public static double bundt_cake_slice_saturation;
    public static int linzer_tart_slice_nutrition;
    public static double linzer_tart_slice_saturation;
    public static int apple_pie_slice_nutrition;
    public static double apple_pie_slice_saturation;
    public static int glowberry_pie_slice_nutrition;
    public static double glowberry_pie_slice_saturation;
    public static int chocolate_tart_slice_nutrition;
    public static double chocolate_tart_slice_saturation;
    public static int pudding_slice_nutrition;
    public static double pudding_slice_saturation;

    public static int strawberry_glazed_cookie_nutrition;
    public static double strawberry_glazed_cookie_saturation;
    public static int sweetberry_glazed_cookie_nutrition;
    public static double sweetberry_glazed_cookie_saturation;
    public static int chocolate_glazed_cookie_nutrition;
    public static double chocolate_glazed_cookie_saturation;

    public static int strawberry_cupcake_nutrition;
    public static double strawberry_cupcake_saturation;
    public static int sweetberry_cupcake_nutrition;
    public static double sweetberry_cupcake_saturation;
    public static int apple_cupcake_nutrition;
    public static double apple_cupcake_saturation;

    public static int cornet_nutrition;
    public static double cornet_saturation;
    public static int jam_roll_nutrition;
    public static double jam_roll_saturation;
    public static int bun_nutrition;
    public static double bun_saturation;
    public static int waffle_nutrition;
    public static double waffle_saturation;
    public static int chocolate_truffle_nutrition;
    public static double chocolate_truffle_saturation;
    public static int misslilitu_biscuit_nutrition;
    public static double misslilitu_biscuit_saturation;

    // ----- Event handlers -----
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == COMMON_CONFIG) {
            refreshCache();
        }
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == COMMON_CONFIG) {
            refreshCache();
        }
    }

    // ----- Cache füllen -----
    private static void refreshCache() {
        give_effect  = GIVE_EFFECT.get();
        show_tooltip = SHOW_TOOLTIP.get();

        cake_dough_nutrition  = CAKE_DOUGH_NUTRITION.get();
        cake_dough_saturation = CAKE_DOUGH_SATURATION.get();
        sweet_dough_nutrition  = SWEET_DOUGH_NUTRITION.get();
        sweet_dough_saturation = SWEET_DOUGH_SATURATION.get();

        croissant_nutrition  = CROISSANT_NUTRITION.get();
        croissant_saturation = CROISSANT_SATURATION.get();
        crusty_bread_nutrition  = CRUSTY_BREAD_NUTRITION.get();
        crusty_bread_saturation = CRUSTY_BREAD_SATURATION.get();
        bread_nutrition  = BREAD_NUTRITION.get();
        bread_saturation = BREAD_SATURATION.get();
        baguette_nutrition  = BAGUETTE_NUTRITION.get();
        baguette_saturation = BAGUETTE_SATURATION.get();
        toast_nutrition  = TOAST_NUTRITION.get();
        toast_saturation = TOAST_SATURATION.get();
        braided_bread_nutrition  = BRAIDED_BREAD_NUTRITION.get();
        braided_bread_saturation = BRAIDED_BREAD_SATURATION.get();

        sandwich_nutrition  = SANDWICH_NUTRITION.get();
        sandwich_saturation = SANDWICH_SATURATION.get();
        vegetable_sandwich_nutrition  = VEGETABLE_SANDWICH_NUTRITION.get();
        vegetable_sandwich_saturation = VEGETABLE_SANDWICH_SATURATION.get();
        grilled_salmon_sandwich_nutrition  = GRILLED_SALMON_SANDWICH_NUTRITION.get();
        grilled_salmon_sandwich_saturation = GRILLED_SALMON_SANDWICH_SATURATION.get();
        grilled_bacon_sandwich_nutrition  = GRILLED_BACON_SANDWICH_NUTRITION.get();
        grilled_bacon_sandwich_saturation = GRILLED_BACON_SANDWICH_SATURATION.get();
        bread_with_jam_nutrition  = BREAD_WITH_JAM_NUTRITION.get();
        bread_with_jam_saturation = BREAD_WITH_JAM_SATURATION.get();

        strawberry_cake_slice_nutrition  = STRAWBERRY_CAKE_SLICE_NUTRITION.get();
        strawberry_cake_slice_saturation = STRAWBERRY_CAKE_SLICE_SATURATION.get();
        sweetberry_cake_slice_nutrition  = SWEETBERRY_CAKE_SLICE_NUTRITION.get();
        sweetberry_cake_slice_saturation = SWEETBERRY_CAKE_SLICE_SATURATION.get();
        chocolate_cake_slice_nutrition  = CHOCOLATE_CAKE_SLICE_NUTRITION.get();
        chocolate_cake_slice_saturation = CHOCOLATE_CAKE_SLICE_SATURATION.get();
        chocolate_gateau_slice_nutrition  = CHOCOLATE_GATEAU_SLICE_NUTRITION.get();
        chocolate_gateau_slice_saturation = CHOCOLATE_GATEAU_SLICE_SATURATION.get();
        bundt_cake_slice_nutrition  = BUNDT_CAKE_SLICE_NUTRITION.get();
        bundt_cake_slice_saturation = BUNDT_CAKE_SLICE_SATURATION.get();
        linzer_tart_slice_nutrition  = LINZER_TART_SLICE_NUTRITION.get();
        linzer_tart_slice_saturation = LINZER_TART_SLICE_SATURATION.get();
        apple_pie_slice_nutrition  = APPLE_PIE_SLICE_NUTRITION.get();
        apple_pie_slice_saturation = APPLE_PIE_SLICE_SATURATION.get();
        glowberry_pie_slice_nutrition  = GLOWBERRY_PIE_SLICE_NUTRITION.get();
        glowberry_pie_slice_saturation = GLOWBERRY_PIE_SLICE_SATURATION.get();
        chocolate_tart_slice_nutrition  = CHOCOLATE_TART_SLICE_NUTRITION.get();
        chocolate_tart_slice_saturation = CHOCOLATE_TART_SLICE_SATURATION.get();
        pudding_slice_nutrition  = PUDDING_SLICE_NUTRITION.get();
        pudding_slice_saturation = PUDDING_SLICE_SATURATION.get();

        strawberry_glazed_cookie_nutrition  = STRAWBERRY_GLAZED_COOKIE_NUTRITION.get();
        strawberry_glazed_cookie_saturation = STRAWBERRY_GLAZED_COOKIE_SATURATION.get();
        sweetberry_glazed_cookie_nutrition  = SWEETBERRY_GLAZED_COOKIE_NUTRITION.get();
        sweetberry_glazed_cookie_saturation = SWEETBERRY_GLAZED_COOKIE_SATURATION.get();
        chocolate_glazed_cookie_nutrition  = CHOCOLATE_GLAZED_COOKIE_NUTRITION.get();
        chocolate_glazed_cookie_saturation = CHOCOLATE_GLAZED_COOKIE_SATURATION.get();

        strawberry_cupcake_nutrition  = STRAWBERRY_CUPCAKE_NUTRITION.get();
        strawberry_cupcake_saturation = STRAWBERRY_CUPCAKE_SATURATION.get();
        sweetberry_cupcake_nutrition  = SWEETBERRY_CUPCAKE_NUTRITION.get();
        sweetberry_cupcake_saturation = SWEETBERRY_CUPCAKE_SATURATION.get();
        apple_cupcake_nutrition  = APPLE_CUPCAKE_NUTRITION.get();
        apple_cupcake_saturation = APPLE_CUPCAKE_SATURATION.get();

        cornet_nutrition  = CORNET_NUTRITION.get();
        cornet_saturation = CORNET_SATURATION.get();
        jam_roll_nutrition  = JAM_ROLL_NUTRITION.get();
        jam_roll_saturation = JAM_ROLL_SATURATION.get();
        bun_nutrition  = BUN_NUTRITION.get();
        bun_saturation = BUN_SATURATION.get();
        waffle_nutrition  = WAFFLE_NUTRITION.get();
        waffle_saturation = WAFFLE_SATURATION.get();
        chocolate_truffle_nutrition  = CHOCOLATE_TRUFFLE_NUTRITION.get();
        chocolate_truffle_saturation = CHOCOLATE_TRUFFLE_SATURATION.get();
        misslilitu_biscuit_nutrition  = MISSLILITU_BISCUIT_NUTRITION.get();
        misslilitu_biscuit_saturation = MISSLILITU_BISCUIT_SATURATION.get();
    }
}
