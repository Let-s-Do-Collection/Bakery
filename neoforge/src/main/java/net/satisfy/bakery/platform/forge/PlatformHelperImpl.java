package net.satisfy.bakery.platform.forge;

import net.satisfy.bakery.forge.core.config.BakeryForgeConfig;
import net.satisfy.bakery.platform.PlatformHelper;

public class PlatformHelperImpl extends PlatformHelper {
    public static boolean shouldGiveEffect() {
        return BakeryForgeConfig.GIVE_EFFECT.get();
    }

    public static boolean shouldShowTooltip() {
        return BakeryForgeConfig.GIVE_EFFECT.get() && BakeryForgeConfig.SHOW_TOOLTIP.get();
    }

    public static int getCroissantNutrition() {
        return BakeryForgeConfig.CROISSANT_NUTRITION.get();
    }

    public static float getCroissantSaturation() {
        return BakeryForgeConfig.CROISSANT_SATURATION.get().floatValue();
    }

    public static int getCrustyBreadNutrition() {
        return BakeryForgeConfig.CRUSTY_BREAD_NUTRITION.get();
    }

    public static float getCrustyBreadSaturation() {
        return BakeryForgeConfig.CRUSTY_BREAD_SATURATION.get().floatValue();
    }

    public static int getBreadNutrition() {
        return BakeryForgeConfig.BREAD_NUTRITION.get();
    }

    public static float getBreadSaturation() {
        return BakeryForgeConfig.BREAD_SATURATION.get().floatValue();
    }

    public static int getBaguetteNutrition() {
        return BakeryForgeConfig.BAGUETTE_NUTRITION.get();
    }

    public static float getBaguetteSaturation() {
        return BakeryForgeConfig.BAGUETTE_SATURATION.get().floatValue();
    }

    public static int getToastNutrition() {
        return BakeryForgeConfig.TOAST_NUTRITION.get();
    }

    public static float getToastSaturation() {
        return BakeryForgeConfig.TOAST_SATURATION.get().floatValue();
    }

    public static int getBraidedBreadNutrition() {
        return BakeryForgeConfig.BRAIDED_BREAD_NUTRITION.get();
    }

    public static float getBraidedBreadSaturation() {
        return BakeryForgeConfig.BRAIDED_BREAD_SATURATION.get().floatValue();
    }

    public static int getSandwichNutrition() {
        return BakeryForgeConfig.SANDWICH_NUTRITION.get();
    }

    public static float getSandwichSaturation() {
        return BakeryForgeConfig.SANDWICH_SATURATION.get().floatValue();
    }

    public static int getVegetableSandwichNutrition() {
        return BakeryForgeConfig.VEGETABLE_SANDWICH_NUTRITION.get();
    }

    public static float getVegetableSandwichSaturation() {
        return BakeryForgeConfig.VEGETABLE_SANDWICH_SATURATION.get().floatValue();
    }

    public static int getGrilledSalmonSandwichNutrition() {
        return BakeryForgeConfig.GRILLED_SALMON_SANDWICH_NUTRITION.get();
    }

    public static float getGrilledSalmonSandwichSaturation() {
        return BakeryForgeConfig.GRILLED_SALMON_SANDWICH_SATURATION.get().floatValue();
    }

    public static int getGrilledBaconSandwichNutrition() {
        return BakeryForgeConfig.GRILLED_BACON_SANDWICH_NUTRITION.get();
    }

    public static float getGrilledBaconSandwichSaturation() {
        return BakeryForgeConfig.GRILLED_BACON_SANDWICH_SATURATION.get().floatValue();
    }

    public static int getBreadWithJamNutrition() {
        return BakeryForgeConfig.BREAD_WITH_JAM_NUTRITION.get();
    }

    public static float getBreadWithJamSaturation() {
        return BakeryForgeConfig.BREAD_WITH_JAM_SATURATION.get().floatValue();
    }

    public static int getStrawberryCakeSliceNutrition() {
        return BakeryForgeConfig.STRAWBERRY_CAKE_SLICE_NUTRITION.get();
    }

    public static float getStrawberryCakeSliceSaturation() {
        return BakeryForgeConfig.STRAWBERRY_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getSweetberryCakeSliceNutrition() {
        return BakeryForgeConfig.SWEETBERRY_CAKE_SLICE_NUTRITION.get();
    }

    public static float getSweetberryCakeSliceSaturation() {
        return BakeryForgeConfig.SWEETBERRY_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getChocolateCakeSliceNutrition() {
        return BakeryForgeConfig.CHOCOLATE_CAKE_SLICE_NUTRITION.get();
    }

    public static float getChocolateCakeSliceSaturation() {
        return BakeryForgeConfig.CHOCOLATE_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getChocolateGateauSliceNutrition() {
        return BakeryForgeConfig.CHOCOLATE_GATEAU_SLICE_NUTRITION.get();
    }

    public static float getChocolateGateauSliceSaturation() {
        return BakeryForgeConfig.CHOCOLATE_GATEAU_SLICE_SATURATION.get().floatValue();
    }

    public static int getBundtCakeSliceNutrition() {
        return BakeryForgeConfig.BUNDT_CAKE_SLICE_NUTRITION.get();
    }

    public static float getBundtCakeSliceSaturation() {
        return BakeryForgeConfig.BUNDT_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getLinzerTartSliceNutrition() {
        return BakeryForgeConfig.LINZER_TART_SLICE_NUTRITION.get();
    }

    public static float getLinzerTartSliceSaturation() {
        return BakeryForgeConfig.LINZER_TART_SLICE_SATURATION.get().floatValue();
    }

    public static int getApplePieSliceNutrition() {
        return BakeryForgeConfig.APPLE_PIE_SLICE_NUTRITION.get();
    }

    public static float getApplePieSliceSaturation() {
        return BakeryForgeConfig.APPLE_PIE_SLICE_SATURATION.get().floatValue();
    }

    public static int getGlowberryPieSliceNutrition() {
        return BakeryForgeConfig.GLOWBERRY_PIE_SLICE_NUTRITION.get();
    }

    public static float getGlowberryPieSliceSaturation() {
        return BakeryForgeConfig.GLOWBERRY_PIE_SLICE_SATURATION.get().floatValue();
    }

    public static int getChocolateTartSliceNutrition() {
        return BakeryForgeConfig.CHOCOLATE_TART_SLICE_NUTRITION.get();
    }

    public static float getChocolateTartSliceSaturation() {
        return BakeryForgeConfig.CHOCOLATE_TART_SLICE_SATURATION.get().floatValue();
    }

    public static int getPuddingSliceNutrition() {
        return BakeryForgeConfig.PUDDING_SLICE_NUTRITION.get();
    }

    public static float getPuddingSliceSaturation() {
        return BakeryForgeConfig.PUDDING_SLICE_SATURATION.get().floatValue();
    }

    public static int getStrawberryGlazedCookieNutrition() {
        return BakeryForgeConfig.STRAWBERRY_GLAZED_COOKIE_NUTRITION.get();
    }

    public static float getStrawberryGlazedCookieSaturation() {
        return BakeryForgeConfig.STRAWBERRY_GLAZED_COOKIE_SATURATION.get().floatValue();
    }

    public static int getSweetberryGlazedCookieNutrition() {
        return BakeryForgeConfig.SWEETBERRY_GLAZED_COOKIE_NUTRITION.get();
    }

    public static float getSweetberryGlazedCookieSaturation() {
        return BakeryForgeConfig.SWEETBERRY_GLAZED_COOKIE_SATURATION.get().floatValue();
    }

    public static int getChocolateGlazedCookieNutrition() {
        return BakeryForgeConfig.CHOCOLATE_GLAZED_COOKIE_NUTRITION.get();
    }

    public static float getChocolateGlazedCookieSaturation() {
        return BakeryForgeConfig.CHOCOLATE_GLAZED_COOKIE_SATURATION.get().floatValue();
    }

    public static int getStrawberryCupcakeNutrition() {
        return BakeryForgeConfig.STRAWBERRY_CUPCAKE_NUTRITION.get();
    }

    public static float getStrawberryCupcakeSaturation() {
        return BakeryForgeConfig.STRAWBERRY_CUPCAKE_SATURATION.get().floatValue();
    }

    public static int getSweetberryCupcakeNutrition() {
        return BakeryForgeConfig.SWEETBERRY_CUPCAKE_NUTRITION.get();
    }

    public static float getSweetberryCupcakeSaturation() {
        return BakeryForgeConfig.SWEETBERRY_CUPCAKE_SATURATION.get().floatValue();
    }

    public static int getAppleCupcakeNutrition() {
        return BakeryForgeConfig.APPLE_CUPCAKE_NUTRITION.get();
    }

    public static float getAppleCupcakeSaturation() {
        return BakeryForgeConfig.APPLE_CUPCAKE_SATURATION.get().floatValue();
    }

    public static int getCornetNutrition() {
        return BakeryForgeConfig.CORNET_NUTRITION.get();
    }

    public static float getCornetSaturation() {
        return BakeryForgeConfig.CORNET_SATURATION.get().floatValue();
    }

    public static int getJamRollNutrition() {
        return BakeryForgeConfig.JAM_ROLL_NUTRITION.get();
    }

    public static float getJamRollSaturation() {
        return BakeryForgeConfig.JAM_ROLL_SATURATION.get().floatValue();
    }

    public static int getBunNutrition() {
        return BakeryForgeConfig.BUN_NUTRITION.get();
    }

    public static float getBunSaturation() {
        return BakeryForgeConfig.BUN_SATURATION.get().floatValue();
    }

    public static int getWaffleNutrition() {
        return BakeryForgeConfig.WAFFLE_NUTRITION.get();
    }

    public static float getWaffleSaturation() {
        return BakeryForgeConfig.WAFFLE_SATURATION.get().floatValue();
    }

    public static int getChocolateTruffleNutrition() {
        return BakeryForgeConfig.CHOCOLATE_TRUFFLE_NUTRITION.get();
    }

    public static float getChocolateTruffleSaturation() {
        return BakeryForgeConfig.CHOCOLATE_TRUFFLE_SATURATION.get().floatValue();
    }

    public static int getMisslilituBiscuitNutrition() {
        return BakeryForgeConfig.MISSLILITU_BISCUIT_NUTRITION.get();
    }

    public static float getMisslilituBiscuitSaturation() {
        return BakeryForgeConfig.MISSLILITU_BISCUIT_SATURATION.get().floatValue();
    }
}
