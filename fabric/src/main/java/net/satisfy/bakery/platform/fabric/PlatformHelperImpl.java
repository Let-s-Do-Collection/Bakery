package net.satisfy.bakery.platform.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import net.satisfy.bakery.fabric.core.config.BakeryFabricConfig;
import net.satisfy.bakery.platform.PlatformHelper;

public class PlatformHelperImpl extends PlatformHelper {
    public static boolean shouldGiveEffect() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.banner.giveEffect;
    }

    public static boolean shouldShowTooltip() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.banner.isShowTooltipEnabled();
    }

    public static int getCroissantNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.croissantNutrition;
    }

    public static float getCroissantSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.croissantSaturationMod;
    }

    public static int getCrustyBreadNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.crustyBreadNutrition;
    }

    public static float getCrustyBreadSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.crustyBreadSaturationMod;
    }

    public static int getBreadNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.breadNutrition;
    }

    public static float getBreadSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.breadSaturationMod;
    }

    public static int getBaguetteNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.baguetteNutrition;
    }

    public static float getBaguetteSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.baguetteSaturationMod;
    }

    public static int getToastNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.toastNutrition;
    }

    public static float getToastSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.toastSaturationMod;
    }

    public static int getBraidedBreadNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.braidedBreadNutrition;
    }

    public static float getBraidedBreadSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.braidedBreadSaturationMod;
    }

    public static int getSandwichNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sandwichNutrition;
    }

    public static float getSandwichSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sandwichSaturationMod;
    }

    public static int getVegetableSandwichNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.vegetableSandwichNutrition;
    }

    public static float getVegetableSandwichSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.vegetableSandwichSaturationMod;
    }

    public static int getGrilledSalmonSandwichNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.grilledSalmonSandwichNutrition;
    }

    public static float getGrilledSalmonSandwichSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.grilledSalmonSandwichSaturationMod;
    }

    public static int getGrilledBaconSandwichNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.grilledBaconSandwichNutrition;
    }

    public static float getGrilledBaconSandwichSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.grilledBaconSandwichSaturationMod;
    }

    public static int getBreadWithJamNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.breadWithJamNutrition;
    }

    public static float getBreadWithJamSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.breadWithJamSaturationMod;
    }

    public static int getStrawberryCakeSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.strawberryCakeSliceNutrition;
    }

    public static float getStrawberryCakeSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.strawberryCakeSliceSaturationMod;
    }

    public static int getSweetberryCakeSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sweetberryCakeSliceNutrition;
    }

    public static float getSweetberryCakeSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sweetberryCakeSliceSaturationMod;
    }

    public static int getChocolateCakeSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateCakeSliceNutrition;
    }

    public static float getChocolateCakeSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateCakeSliceSaturationMod;
    }

    public static int getChocolateGateauSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateGateauSliceNutrition;
    }

    public static float getChocolateGateauSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateGateauSliceSaturationMod;
    }

    public static int getBundtCakeSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.bundtCakeSliceNutrition;
    }

    public static float getBundtCakeSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.bundtCakeSliceSaturationMod;
    }

    public static int getLinzerTartSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.linzerTartSliceNutrition;
    }

    public static float getLinzerTartSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.linzerTartSliceSaturationMod;
    }

    public static int getApplePieSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.applePieSliceNutrition;
    }

    public static float getApplePieSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.applePieSliceSaturationMod;
    }

    public static int getGlowberryPieSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.glowberryPieSliceNutrition;
    }

    public static float getGlowberryPieSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.glowberryPieSliceSaturationMod;
    }

    public static int getChocolateTartSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateTartSliceNutrition;
    }

    public static float getChocolateTartSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateTartSliceSaturationMod;
    }

    public static int getPuddingSliceNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.puddingSliceNutrition;
    }

    public static float getPuddingSliceSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.puddingSliceSaturationMod;
    }

    public static int getStrawberryGlazedCookieNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.strawberryGlazedCookieNutrition;
    }

    public static float getStrawberryGlazedCookieSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.strawberryGlazedCookieSaturationMod;
    }

    public static int getSweetberryGlazedCookieNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sweetberryGlazedCookieNutrition;
    }

    public static float getSweetberryGlazedCookieSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sweetberryGlazedCookieSaturationMod;
    }

    public static int getChocolateGlazedCookieNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateGlazedCookieNutrition;
    }

    public static float getChocolateGlazedCookieSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateGlazedCookieSaturationMod;
    }

    public static int getStrawberryCupcakeNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.strawberryCupcakeNutrition;
    }

    public static float getStrawberryCupcakeSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.strawberryCupcakeSaturationMod;
    }

    public static int getSweetberryCupcakeNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sweetberryCupcakeNutrition;
    }

    public static float getSweetberryCupcakeSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.sweetberryCupcakeSaturationMod;
    }

    public static int getAppleCupcakeNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.appleCupcakeNutrition;
    }

    public static float getAppleCupcakeSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.appleCupcakeSaturationMod;
    }

    public static int getCornetNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.cornetNutrition;
    }

    public static float getCornetSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.cornetSaturationMod;
    }

    public static int getJamRollNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.jamRollNutrition;
    }

    public static float getJamRollSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.jamRollSaturationMod;
    }

    public static int getWaffleNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.waffleNutrition;
    }

    public static float getWaffleSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.waffleSaturationMod;
    }

    public static int getChocolateTruffleNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateTruffleNutrition;
    }

    public static float getChocolateTruffleSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.chocolateTruffleSaturationMod;
    }

    public static int getMisslilituBiscuitNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.misslilituBiscuitNutrition;
    }

    public static float getMisslilituBiscuitSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.misslilituBiscuitSaturationMod;
    }

    public static int getBunNutrition() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.bunNutrition;
    }

    public static float getBunSaturation() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.nutrition.bunSaturationMod;
    }

    public static int getVitalityEffectInterval() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.vitality.interval;
    }

    public static float getVitalityEffectExhaustionReduction() {
        BakeryFabricConfig config = AutoConfig.getConfigHolder(BakeryFabricConfig.class).getConfig();
        return config.items.vitality.exhaustionReduction;
    }
}
