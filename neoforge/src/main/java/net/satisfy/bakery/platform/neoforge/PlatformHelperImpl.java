package net.satisfy.bakery.platform.neoforge;

import net.satisfy.bakery.neoforge.core.config.BakeryNeoForgeConfig;
import net.satisfy.bakery.platform.PlatformHelper;

public class PlatformHelperImpl extends PlatformHelper {

    // Banner / Tooltip
    public static boolean shouldGiveEffect() {
        return BakeryNeoForgeConfig.give_effect;
    }

    public static boolean shouldShowTooltip() {
        return BakeryNeoForgeConfig.give_effect && BakeryNeoForgeConfig.show_tooltip;
    }

    // Dough
    public static int getCakeDoughNutrition() {
        return BakeryNeoForgeConfig.cake_dough_nutrition;
    }

    public static float getCakeDoughSaturation() {
        return (float) BakeryNeoForgeConfig.cake_dough_saturation;
    }

    public static int getSweetDoughNutrition() {
        return BakeryNeoForgeConfig.sweet_dough_nutrition;
    }

    public static float getSweetDoughSaturation() {
        return (float) BakeryNeoForgeConfig.sweet_dough_saturation;
    }

    // Breads
    public static int getCroissantNutrition() {
        return BakeryNeoForgeConfig.croissant_nutrition;
    }

    public static float getCroissantSaturation() {
        return (float) BakeryNeoForgeConfig.croissant_saturation;
    }

    public static int getCrustyBreadNutrition() {
        return BakeryNeoForgeConfig.crusty_bread_nutrition;
    }

    public static float getCrustyBreadSaturation() {
        return (float) BakeryNeoForgeConfig.crusty_bread_saturation;
    }

    public static int getBreadNutrition() {
        return BakeryNeoForgeConfig.bread_nutrition;
    }

    public static float getBreadSaturation() {
        return (float) BakeryNeoForgeConfig.bread_saturation;
    }

    public static int getBaguetteNutrition() {
        return BakeryNeoForgeConfig.baguette_nutrition;
    }

    public static float getBaguetteSaturation() {
        return (float) BakeryNeoForgeConfig.baguette_saturation;
    }

    public static int getToastNutrition() {
        return BakeryNeoForgeConfig.toast_nutrition;
    }

    public static float getToastSaturation() {
        return (float) BakeryNeoForgeConfig.toast_saturation;
    }

    public static int getBraidedBreadNutrition() {
        return BakeryNeoForgeConfig.braided_bread_nutrition;
    }

    public static float getBraidedBreadSaturation() {
        return (float) BakeryNeoForgeConfig.braided_bread_saturation;
    }

    // Sandwiches
    public static int getSandwichNutrition() {
        return BakeryNeoForgeConfig.sandwich_nutrition;
    }

    public static float getSandwichSaturation() {
        return (float) BakeryNeoForgeConfig.sandwich_saturation;
    }

    public static int getVegetableSandwichNutrition() {
        return BakeryNeoForgeConfig.vegetable_sandwich_nutrition;
    }

    public static float getVegetableSandwichSaturation() {
        return (float) BakeryNeoForgeConfig.vegetable_sandwich_saturation;
    }

    public static int getGrilledSalmonSandwichNutrition() {
        return BakeryNeoForgeConfig.grilled_salmon_sandwich_nutrition;
    }

    public static float getGrilledSalmonSandwichSaturation() {
        return (float) BakeryNeoForgeConfig.grilled_salmon_sandwich_saturation;
    }

    public static int getGrilledBaconSandwichNutrition() {
        return BakeryNeoForgeConfig.grilled_bacon_sandwich_nutrition;
    }

    public static float getGrilledBaconSandwichSaturation() {
        return (float) BakeryNeoForgeConfig.grilled_bacon_sandwich_saturation;
    }

    public static int getBreadWithJamNutrition() {
        return BakeryNeoForgeConfig.bread_with_jam_nutrition;
    }

    public static float getBreadWithJamSaturation() {
        return (float) BakeryNeoForgeConfig.bread_with_jam_saturation;
    }

    // Cakes & Tarts (Slices)
    public static int getStrawberryCakeSliceNutrition() {
        return BakeryNeoForgeConfig.strawberry_cake_slice_nutrition;
    }

    public static float getStrawberryCakeSliceSaturation() {
        return (float) BakeryNeoForgeConfig.strawberry_cake_slice_saturation;
    }

    public static int getSweetberryCakeSliceNutrition() {
        return BakeryNeoForgeConfig.sweetberry_cake_slice_nutrition;
    }

    public static float getSweetberryCakeSliceSaturation() {
        return (float) BakeryNeoForgeConfig.sweetberry_cake_slice_saturation;
    }

    public static int getChocolateCakeSliceNutrition() {
        return BakeryNeoForgeConfig.chocolate_cake_slice_nutrition;
    }

    public static float getChocolateCakeSliceSaturation() {
        return (float) BakeryNeoForgeConfig.chocolate_cake_slice_saturation;
    }

    public static int getChocolateGateauSliceNutrition() {
        return BakeryNeoForgeConfig.chocolate_gateau_slice_nutrition;
    }

    public static float getChocolateGateauSliceSaturation() {
        return (float) BakeryNeoForgeConfig.chocolate_gateau_slice_saturation;
    }

    public static int getBundtCakeSliceNutrition() {
        return BakeryNeoForgeConfig.bundt_cake_slice_nutrition;
    }

    public static float getBundtCakeSliceSaturation() {
        return (float) BakeryNeoForgeConfig.bundt_cake_slice_saturation;
    }

    public static int getLinzerTartSliceNutrition() {
        return BakeryNeoForgeConfig.linzer_tart_slice_nutrition;
    }

    public static float getLinzerTartSliceSaturation() {
        return (float) BakeryNeoForgeConfig.linzer_tart_slice_saturation;
    }

    public static int getApplePieSliceNutrition() {
        return BakeryNeoForgeConfig.apple_pie_slice_nutrition;
    }

    public static float getApplePieSliceSaturation() {
        return (float) BakeryNeoForgeConfig.apple_pie_slice_saturation;
    }

    public static int getGlowberryPieSliceNutrition() {
        return BakeryNeoForgeConfig.glowberry_pie_slice_nutrition;
    }

    public static float getGlowberryPieSliceSaturation() {
        return (float) BakeryNeoForgeConfig.glowberry_pie_slice_saturation;
    }

    public static int getChocolateTartSliceNutrition() {
        return BakeryNeoForgeConfig.chocolate_tart_slice_nutrition;
    }

    public static float getChocolateTartSliceSaturation() {
        return (float) BakeryNeoForgeConfig.chocolate_tart_slice_saturation;
    }

    public static int getPuddingSliceNutrition() {
        return BakeryNeoForgeConfig.pudding_slice_nutrition;
    }

    public static float getPuddingSliceSaturation() {
        return (float) BakeryNeoForgeConfig.pudding_slice_saturation;
    }

    // Cookies
    public static int getStrawberryGlazedCookieNutrition() {
        return BakeryNeoForgeConfig.strawberry_glazed_cookie_nutrition;
    }

    public static float getStrawberryGlazedCookieSaturation() {
        return (float) BakeryNeoForgeConfig.strawberry_glazed_cookie_saturation;
    }

    public static int getSweetberryGlazedCookieNutrition() {
        return BakeryNeoForgeConfig.sweetberry_glazed_cookie_nutrition;
    }

    public static float getSweetberryGlazedCookieSaturation() {
        return (float) BakeryNeoForgeConfig.sweetberry_glazed_cookie_saturation;
    }

    public static int getChocolateGlazedCookieNutrition() {
        return BakeryNeoForgeConfig.chocolate_glazed_cookie_nutrition;
    }

    public static float getChocolateGlazedCookieSaturation() {
        return (float) BakeryNeoForgeConfig.chocolate_glazed_cookie_saturation;
    }

    // Cupcakes
    public static int getStrawberryCupcakeNutrition() {
        return BakeryNeoForgeConfig.strawberry_cupcake_nutrition;
    }

    public static float getStrawberryCupcakeSaturation() {
        return (float) BakeryNeoForgeConfig.strawberry_cupcake_saturation;
    }

    public static int getSweetberryCupcakeNutrition() {
        return BakeryNeoForgeConfig.sweetberry_cupcake_nutrition;
    }

    public static float getSweetberryCupcakeSaturation() {
        return (float) BakeryNeoForgeConfig.sweetberry_cupcake_saturation;
    }

    public static int getAppleCupcakeNutrition() {
        return BakeryNeoForgeConfig.apple_cupcake_nutrition;
    }

    public static float getAppleCupcakeSaturation() {
        return (float) BakeryNeoForgeConfig.apple_cupcake_saturation;
    }

    // Pastries & Others
    public static int getCornetNutrition() {
        return BakeryNeoForgeConfig.cornet_nutrition;
    }

    public static float getCornetSaturation() {
        return (float) BakeryNeoForgeConfig.cornet_saturation;
    }

    public static int getJamRollNutrition() {
        return BakeryNeoForgeConfig.jam_roll_nutrition;
    }

    public static float getJamRollSaturation() {
        return (float) BakeryNeoForgeConfig.jam_roll_saturation;
    }

    public static int getBunNutrition() {
        return BakeryNeoForgeConfig.bun_nutrition;
    }

    public static float getBunSaturation() {
        return (float) BakeryNeoForgeConfig.bun_saturation;
    }

    public static int getWaffleNutrition() {
        return BakeryNeoForgeConfig.waffle_nutrition;
    }

    public static float getWaffleSaturation() {
        return (float) BakeryNeoForgeConfig.waffle_saturation;
    }

    public static int getChocolateTruffleNutrition() {
        return BakeryNeoForgeConfig.chocolate_truffle_nutrition;
    }

    public static float getChocolateTruffleSaturation() {
        return (float) BakeryNeoForgeConfig.chocolate_truffle_saturation;
    }

    public static int getMisslilituBiscuitNutrition() {
        return BakeryNeoForgeConfig.misslilitu_biscuit_nutrition;
    }

    public static float getMisslilituBiscuitSaturation() {
        return (float) BakeryNeoForgeConfig.misslilitu_biscuit_saturation;
    }
}
