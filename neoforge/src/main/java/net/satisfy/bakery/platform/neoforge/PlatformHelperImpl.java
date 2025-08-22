package net.satisfy.bakery.platform.neoforge;

import net.satisfy.bakery.neoforge.core.config.BakeryNeoForgeConfig;
import net.satisfy.bakery.platform.PlatformHelper;

public class PlatformHelperImpl extends PlatformHelper {
    public static boolean shouldGiveEffect() {
        return BakeryNeoForgeConfig.GIVE_EFFECT.get();
    }

    public static boolean shouldShowTooltip() {
        return BakeryNeoForgeConfig.GIVE_EFFECT.get() && BakeryNeoForgeConfig.SHOW_TOOLTIP.get();
    }

    public static int getCroissantNutrition() {
        return BakeryNeoForgeConfig.CROISSANT_NUTRITION.get();
    }

    public static float getCroissantSaturation() {
        return BakeryNeoForgeConfig.CROISSANT_SATURATION.get().floatValue();
    }

    public static int getCrustyBreadNutrition() {
        return BakeryNeoForgeConfig.CRUSTY_BREAD_NUTRITION.get();
    }

    public static float getCrustyBreadSaturation() {
        return BakeryNeoForgeConfig.CRUSTY_BREAD_SATURATION.get().floatValue();
    }

    public static int getBreadNutrition() {
        return BakeryNeoForgeConfig.BREAD_NUTRITION.get();
    }

    public static float getBreadSaturation() {
        return BakeryNeoForgeConfig.BREAD_SATURATION.get().floatValue();
    }

    public static int getBaguetteNutrition() {
        return BakeryNeoForgeConfig.BAGUETTE_NUTRITION.get();
    }

    public static float getBaguetteSaturation() {
        return BakeryNeoForgeConfig.BAGUETTE_SATURATION.get().floatValue();
    }

    public static int getToastNutrition() {
        return BakeryNeoForgeConfig.TOAST_NUTRITION.get();
    }

    public static float getToastSaturation() {
        return BakeryNeoForgeConfig.TOAST_SATURATION.get().floatValue();
    }

    public static int getBraidedBreadNutrition() {
        return BakeryNeoForgeConfig.BRAIDED_BREAD_NUTRITION.get();
    }

    public static float getBraidedBreadSaturation() {
        return BakeryNeoForgeConfig.BRAIDED_BREAD_SATURATION.get().floatValue();
    }

    public static int getSandwichNutrition() {
        return BakeryNeoForgeConfig.SANDWICH_NUTRITION.get();
    }

    public static float getSandwichSaturation() {
        return BakeryNeoForgeConfig.SANDWICH_SATURATION.get().floatValue();
    }

    public static int getVegetableSandwichNutrition() {
        return BakeryNeoForgeConfig.VEGETABLE_SANDWICH_NUTRITION.get();
    }

    public static float getVegetableSandwichSaturation() {
        return BakeryNeoForgeConfig.VEGETABLE_SANDWICH_SATURATION.get().floatValue();
    }

    public static int getGrilledSalmonSandwichNutrition() {
        return BakeryNeoForgeConfig.GRILLED_SALMON_SANDWICH_NUTRITION.get();
    }

    public static float getGrilledSalmonSandwichSaturation() {
        return BakeryNeoForgeConfig.GRILLED_SALMON_SANDWICH_SATURATION.get().floatValue();
    }

    public static int getGrilledBaconSandwichNutrition() {
        return BakeryNeoForgeConfig.GRILLED_BACON_SANDWICH_NUTRITION.get();
    }

    public static float getGrilledBaconSandwichSaturation() {
        return BakeryNeoForgeConfig.GRILLED_BACON_SANDWICH_SATURATION.get().floatValue();
    }

    public static int getBreadWithJamNutrition() {
        return BakeryNeoForgeConfig.BREAD_WITH_JAM_NUTRITION.get();
    }

    public static float getBreadWithJamSaturation() {
        return BakeryNeoForgeConfig.BREAD_WITH_JAM_SATURATION.get().floatValue();
    }

    public static int getStrawberryCakeSliceNutrition() {
        return BakeryNeoForgeConfig.STRAWBERRY_CAKE_SLICE_NUTRITION.get();
    }

    public static float getStrawberryCakeSliceSaturation() {
        return BakeryNeoForgeConfig.STRAWBERRY_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getSweetberryCakeSliceNutrition() {
        return BakeryNeoForgeConfig.SWEETBERRY_CAKE_SLICE_NUTRITION.get();
    }

    public static float getSweetberryCakeSliceSaturation() {
        return BakeryNeoForgeConfig.SWEETBERRY_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getChocolateCakeSliceNutrition() {
        return BakeryNeoForgeConfig.CHOCOLATE_CAKE_SLICE_NUTRITION.get();
    }

    public static float getChocolateCakeSliceSaturation() {
        return BakeryNeoForgeConfig.CHOCOLATE_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getChocolateGateauSliceNutrition() {
        return BakeryNeoForgeConfig.CHOCOLATE_GATEAU_SLICE_NUTRITION.get();
    }

    public static float getChocolateGateauSliceSaturation() {
        return BakeryNeoForgeConfig.CHOCOLATE_GATEAU_SLICE_SATURATION.get().floatValue();
    }

    public static int getBundtCakeSliceNutrition() {
        return BakeryNeoForgeConfig.BUNDT_CAKE_SLICE_NUTRITION.get();
    }

    public static float getBundtCakeSliceSaturation() {
        return BakeryNeoForgeConfig.BUNDT_CAKE_SLICE_SATURATION.get().floatValue();
    }

    public static int getLinzerTartSliceNutrition() {
        return BakeryNeoForgeConfig.LINZER_TART_SLICE_NUTRITION.get();
    }

    public static float getLinzerTartSliceSaturation() {
        return BakeryNeoForgeConfig.LINZER_TART_SLICE_SATURATION.get().floatValue();
    }

    public static int getApplePieSliceNutrition() {
        return BakeryNeoForgeConfig.APPLE_PIE_SLICE_NUTRITION.get();
    }

    public static float getApplePieSliceSaturation() {
        return BakeryNeoForgeConfig.APPLE_PIE_SLICE_SATURATION.get().floatValue();
    }

    public static int getGlowberryPieSliceNutrition() {
        return BakeryNeoForgeConfig.GLOWBERRY_PIE_SLICE_NUTRITION.get();
    }

    public static float getGlowberryPieSliceSaturation() {
        return BakeryNeoForgeConfig.GLOWBERRY_PIE_SLICE_SATURATION.get().floatValue();
    }

    public static int getChocolateTartSliceNutrition() {
        return BakeryNeoForgeConfig.CHOCOLATE_TART_SLICE_NUTRITION.get();
    }

    public static float getChocolateTartSliceSaturation() {
        return BakeryNeoForgeConfig.CHOCOLATE_TART_SLICE_SATURATION.get().floatValue();
    }

    public static int getPuddingSliceNutrition() {
        return BakeryNeoForgeConfig.PUDDING_SLICE_NUTRITION.get();
    }

    public static float getPuddingSliceSaturation() {
        return BakeryNeoForgeConfig.PUDDING_SLICE_SATURATION.get().floatValue();
    }

    public static int getStrawberryGlazedCookieNutrition() {
        return BakeryNeoForgeConfig.STRAWBERRY_GLAZED_COOKIE_NUTRITION.get();
    }

    public static float getStrawberryGlazedCookieSaturation() {
        return BakeryNeoForgeConfig.STRAWBERRY_GLAZED_COOKIE_SATURATION.get().floatValue();
    }

    public static int getSweetberryGlazedCookieNutrition() {
        return BakeryNeoForgeConfig.SWEETBERRY_GLAZED_COOKIE_NUTRITION.get();
    }

    public static float getSweetberryGlazedCookieSaturation() {
        return BakeryNeoForgeConfig.SWEETBERRY_GLAZED_COOKIE_SATURATION.get().floatValue();
    }

    public static int getChocolateGlazedCookieNutrition() {
        return BakeryNeoForgeConfig.CHOCOLATE_GLAZED_COOKIE_NUTRITION.get();
    }

    public static float getChocolateGlazedCookieSaturation() {
        return BakeryNeoForgeConfig.CHOCOLATE_GLAZED_COOKIE_SATURATION.get().floatValue();
    }

    public static int getStrawberryCupcakeNutrition() {
        return BakeryNeoForgeConfig.STRAWBERRY_CUPCAKE_NUTRITION.get();
    }

    public static float getStrawberryCupcakeSaturation() {
        return BakeryNeoForgeConfig.STRAWBERRY_CUPCAKE_SATURATION.get().floatValue();
    }

    public static int getSweetberryCupcakeNutrition() {
        return BakeryNeoForgeConfig.SWEETBERRY_CUPCAKE_NUTRITION.get();
    }

    public static float getSweetberryCupcakeSaturation() {
        return BakeryNeoForgeConfig.SWEETBERRY_CUPCAKE_SATURATION.get().floatValue();
    }

    public static int getAppleCupcakeNutrition() {
        return BakeryNeoForgeConfig.APPLE_CUPCAKE_NUTRITION.get();
    }

    public static float getAppleCupcakeSaturation() {
        return BakeryNeoForgeConfig.APPLE_CUPCAKE_SATURATION.get().floatValue();
    }

    public static int getCornetNutrition() {
        return BakeryNeoForgeConfig.CORNET_NUTRITION.get();
    }

    public static float getCornetSaturation() {
        return BakeryNeoForgeConfig.CORNET_SATURATION.get().floatValue();
    }

    public static int getJamRollNutrition() {
        return BakeryNeoForgeConfig.JAM_ROLL_NUTRITION.get();
    }

    public static float getJamRollSaturation() {
        return BakeryNeoForgeConfig.JAM_ROLL_SATURATION.get().floatValue();
    }

    public static int getBunNutrition() {
        return BakeryNeoForgeConfig.BUN_NUTRITION.get();
    }

    public static float getBunSaturation() {
        return BakeryNeoForgeConfig.BUN_SATURATION.get().floatValue();
    }

    public static int getWaffleNutrition() {
        return BakeryNeoForgeConfig.WAFFLE_NUTRITION.get();
    }

    public static float getWaffleSaturation() {
        return BakeryNeoForgeConfig.WAFFLE_SATURATION.get().floatValue();
    }

    public static int getChocolateTruffleNutrition() {
        return BakeryNeoForgeConfig.CHOCOLATE_TRUFFLE_NUTRITION.get();
    }

    public static float getChocolateTruffleSaturation() {
        return BakeryNeoForgeConfig.CHOCOLATE_TRUFFLE_SATURATION.get().floatValue();
    }

    public static int getMisslilituBiscuitNutrition() {
        return BakeryNeoForgeConfig.MISSLILITU_BISCUIT_NUTRITION.get();
    }

    public static float getMisslilituBiscuitSaturation() {
        return BakeryNeoForgeConfig.MISSLILITU_BISCUIT_SATURATION.get().floatValue();
    }
}
