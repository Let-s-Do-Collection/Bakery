package net.satisfy.bakery.fabric.core.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "bakery")
@Config.Gui.Background("minecraft:textures/block/bricks.png")
public class BakeryFabricConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ItemsSettings items = new ItemsSettings();

    public static class ItemsSettings {

        @ConfigEntry.Gui.CollapsibleObject
        public BannerSettings banner = new BannerSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public NutritionSettings nutrition = new NutritionSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public VitalitySettings vitality = new VitalitySettings();

        public static class BannerSettings {
            public boolean giveEffect = true;
            public boolean showTooltip = true;

            public boolean isShowTooltipEnabled() {
                return giveEffect && showTooltip;
            }
        }

        public static class VitalitySettings {
            public int interval = 10;
            public float exhaustionReduction = 0.08f;
        }

        public static class NutritionSettings {
            public int cakeDoughNutrition = 5;
            public float cakeDoughSaturationMod = 0.6f;
            public int sweetDoughNutrition = 5;
            public float sweetDoughSaturationMod = 0.6f;
            public int croissantNutrition = 5;
            public float croissantSaturationMod = 0.6f;
            public int crustyBreadNutrition = 5;
            public float crustyBreadSaturationMod = 1.2f;
            public int breadNutrition = 5;
            public float breadSaturationMod = 1.2f;
            public int baguetteNutrition = 5;
            public float baguetteSaturationMod = 1.2f;
            public int toastNutrition = 3;
            public float toastSaturationMod = 0.8f;
            public int braidedBreadNutrition = 5;
            public float braidedBreadSaturationMod = 1.2f;
            public int sandwichNutrition = 7;
            public float sandwichSaturationMod = 0.7f;
            public int vegetableSandwichNutrition = 8;
            public float vegetableSandwichSaturationMod = 0.6f;
            public int grilledSalmonSandwichNutrition = 6;
            public float grilledSalmonSandwichSaturationMod = 0.8f;
            public int grilledBaconSandwichNutrition = 7;
            public float grilledBaconSandwichSaturationMod = 0.7f;
            public int breadWithJamNutrition = 5;
            public float breadWithJamSaturationMod = 0.5f;
            public int strawberryCakeSliceNutrition = 5;
            public float strawberryCakeSliceSaturationMod = 0.7f;
            public int sweetberryCakeSliceNutrition = 5;
            public float sweetberryCakeSliceSaturationMod = 0.7f;
            public int chocolateCakeSliceNutrition = 5;
            public float chocolateCakeSliceSaturationMod = 0.7f;
            public int chocolateGateauSliceNutrition = 5;
            public float chocolateGateauSliceSaturationMod = 0.7f;
            public int bundtCakeSliceNutrition = 5;
            public float bundtCakeSliceSaturationMod = 0.7f;
            public int linzerTartSliceNutrition = 5;
            public float linzerTartSliceSaturationMod = 0.7f;
            public int applePieSliceNutrition = 5;
            public float applePieSliceSaturationMod = 0.7f;
            public int glowberryPieSliceNutrition = 5;
            public float glowberryPieSliceSaturationMod = 0.7f;
            public int chocolateTartSliceNutrition = 5;
            public float chocolateTartSliceSaturationMod = 0.7f;
            public int puddingSliceNutrition = 5;
            public float puddingSliceSaturationMod = 0.7f;
            public int strawberryGlazedCookieNutrition = 3;
            public float strawberryGlazedCookieSaturationMod = 0.5f;
            public int sweetberryGlazedCookieNutrition = 3;
            public float sweetberryGlazedCookieSaturationMod = 0.5f;
            public int chocolateGlazedCookieNutrition = 3;
            public float chocolateGlazedCookieSaturationMod = 0.5f;
            public int strawberryCupcakeNutrition = 3;
            public float strawberryCupcakeSaturationMod = 0.5f;
            public int sweetberryCupcakeNutrition = 3;
            public float sweetberryCupcakeSaturationMod = 0.5f;
            public int appleCupcakeNutrition = 3;
            public float appleCupcakeSaturationMod = 0.5f;
            public int cornetNutrition = 3;
            public float cornetSaturationMod = 0.5f;
            public int jamRollNutrition = 3;
            public float jamRollSaturationMod = 0.5f;
            public int waffleNutrition = 5;
            public float waffleSaturationMod = 0.5f;
            public int chocolateTruffleNutrition = 2;
            public float chocolateTruffleSaturationMod = 0.4f;
            public int misslilituBiscuitNutrition = 6;
            public float misslilituBiscuitSaturationMod = 0.6f;
            public int bunNutrition = 5;
            public float bunSaturationMod = 1.2f;
        }
    }
}