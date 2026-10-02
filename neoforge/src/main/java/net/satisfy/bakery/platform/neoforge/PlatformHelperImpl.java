package net.satisfy.bakery.platform.neoforge;

import net.satisfy.bakery.neoforge.core.config.BakeryNeoForgeConfig;
import net.satisfy.bakery.platform.PlatformHelper;

public class PlatformHelperImpl extends PlatformHelper {
    public static boolean shouldGiveEffect() {
        return BakeryNeoForgeConfig.give_effect;
    }

    public static boolean shouldShowTooltip() {
        return BakeryNeoForgeConfig.give_effect && BakeryNeoForgeConfig.show_tooltip;
    }

    public static boolean showBakerStationInfo() {
        return BakeryNeoForgeConfig.show_baker_station_info;
    }

    public static boolean showJamPotInfo() {
        return BakeryNeoForgeConfig.show_jam_pot_info;
    }

    public static boolean infoTooltipsNeedDungarees() {
        return BakeryNeoForgeConfig.info_tooltips_need_dungarees;
    }

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

    public static int getVitalityEffectInterval() {
        return BakeryNeoForgeConfig.vitality_interval;
    }

    public static float getVitalityEffectExhaustionReduction() {
        return (float) BakeryNeoForgeConfig.vitality_exhaustion_reduction;
    }

    public static int getSugarRushMaxStacks() {
        return BakeryNeoForgeConfig.sugar_rush_max_stacks;
    }

    public static double getSugarRushBonusPerStack() {
        return BakeryNeoForgeConfig.sugar_rush_bonus_per_stack;
    }

    public static int getSugarRushAttackSpeedStacks() {
        return BakeryNeoForgeConfig.sugar_rush_attack_speed_stacks;
    }

    public static boolean isSugarRushAttackSpeedEnabled() {
        return BakeryNeoForgeConfig.sugar_rush_attack_speed;
    }

    public static int getBannerEffectRadius() {
        return BakeryNeoForgeConfig.banner_effect_radius;
    }

    public static int getBannerEffectAmplifier() {
        return BakeryNeoForgeConfig.banner_effect_amplifier;
    }

    public static boolean isBakerStationAnimationEnabled() {
        return BakeryNeoForgeConfig.baker_station_animations;
    }

    public static boolean isKneadingEnabled() {
        return BakeryNeoForgeConfig.enable_kneading;
    }

    public static int getKneadPresses() {
        return BakeryNeoForgeConfig.knead_presses;
    }

    public static int getRollingPinPresses() {
        return BakeryNeoForgeConfig.rolling_pin_presses;
    }

    public static int getKnifeDuration() {
        return BakeryNeoForgeConfig.knife_duration;
    }

    public static int getJamDuration() {
        return BakeryNeoForgeConfig.jam_duration;
    }

    public static float getKnifeAttackSpeed() {
        return (float) BakeryNeoForgeConfig.knife_attack_speed;
    }

    public static float getRollingPinAttackSpeed() {
        return (float) BakeryNeoForgeConfig.rolling_pin_attack_speed;
    }

    public static int getCroissantEffectDuration() {
        return BakeryNeoForgeConfig.croissant_effect_duration;
    }

    public static int getCrustyBreadEffectDuration() {
        return BakeryNeoForgeConfig.crusty_bread_effect_duration;
    }

    public static int getBreadEffectDuration() {
        return BakeryNeoForgeConfig.bread_effect_duration;
    }

    public static int getBaguetteEffectDuration() {
        return BakeryNeoForgeConfig.baguette_effect_duration;
    }

    public static int getToastEffectDuration() {
        return BakeryNeoForgeConfig.toast_effect_duration;
    }

    public static int getBraidedBreadEffectDuration() {
        return BakeryNeoForgeConfig.braided_bread_effect_duration;
    }

    public static int getSandwichEffectDuration() {
        return BakeryNeoForgeConfig.sandwich_effect_duration;
    }

    public static int getVegetableSandwichEffectDuration() {
        return BakeryNeoForgeConfig.vegetable_sandwich_effect_duration;
    }

    public static int getGrilledSalmonSandwichEffectDuration() {
        return BakeryNeoForgeConfig.grilled_salmon_sandwich_effect_duration;
    }

    public static int getGrilledBaconSandwichEffectDuration() {
        return BakeryNeoForgeConfig.grilled_bacon_sandwich_effect_duration;
    }

    public static int getBreadWithJamEffectDuration() {
        return BakeryNeoForgeConfig.bread_with_jam_effect_duration;
    }

    public static int getStrawberryCakeSliceEffectDuration() {
        return BakeryNeoForgeConfig.strawberry_cake_slice_effect_duration;
    }

    public static int getSweetberryCakeSliceEffectDuration() {
        return BakeryNeoForgeConfig.sweetberry_cake_slice_effect_duration;
    }

    public static int getChocolateCakeSliceEffectDuration() {
        return BakeryNeoForgeConfig.chocolate_cake_slice_effect_duration;
    }

    public static int getChocolateGateauSliceEffectDuration() {
        return BakeryNeoForgeConfig.chocolate_gateau_slice_effect_duration;
    }

    public static int getBundtCakeSliceEffectDuration() {
        return BakeryNeoForgeConfig.bundt_cake_slice_effect_duration;
    }

    public static int getLinzerTartSliceEffectDuration() {
        return BakeryNeoForgeConfig.linzer_tart_slice_effect_duration;
    }

    public static int getApplePieSliceEffectDuration() {
        return BakeryNeoForgeConfig.apple_pie_slice_effect_duration;
    }

    public static int getGlowberryPieSliceEffectDuration() {
        return BakeryNeoForgeConfig.glowberry_pie_slice_effect_duration;
    }

    public static int getChocolateTartSliceEffectDuration() {
        return BakeryNeoForgeConfig.chocolate_tart_slice_effect_duration;
    }

    public static int getPuddingSliceEffectDuration() {
        return BakeryNeoForgeConfig.pudding_slice_effect_duration;
    }

    public static int getStrawberryGlazedCookieEffectDuration() {
        return BakeryNeoForgeConfig.strawberry_glazed_cookie_effect_duration;
    }

    public static int getSweetberryGlazedCookieEffectDuration() {
        return BakeryNeoForgeConfig.sweetberry_glazed_cookie_effect_duration;
    }

    public static int getChocolateGlazedCookieEffectDuration() {
        return BakeryNeoForgeConfig.chocolate_glazed_cookie_effect_duration;
    }

    public static int getStrawberryCupcakeEffectDuration() {
        return BakeryNeoForgeConfig.strawberry_cupcake_effect_duration;
    }

    public static int getSweetberryCupcakeEffectDuration() {
        return BakeryNeoForgeConfig.sweetberry_cupcake_effect_duration;
    }

    public static int getAppleCupcakeEffectDuration() {
        return BakeryNeoForgeConfig.apple_cupcake_effect_duration;
    }

    public static int getCornetEffectDuration() {
        return BakeryNeoForgeConfig.cornet_effect_duration;
    }

    public static int getJamRollEffectDuration() {
        return BakeryNeoForgeConfig.jam_roll_effect_duration;
    }

    public static int getChocolateTruffleEffectDuration() {
        return BakeryNeoForgeConfig.chocolate_truffle_effect_duration;
    }

    public static int getMisslilituBiscuitEffectDuration() {
        return BakeryNeoForgeConfig.misslilitu_biscuit_effect_duration;
    }

    public static int getWaffleEffectDuration() {
        return BakeryNeoForgeConfig.waffle_effect_duration;
    }

    public static int getBunEffectDuration() {
        return BakeryNeoForgeConfig.bun_effect_duration;
    }
}
