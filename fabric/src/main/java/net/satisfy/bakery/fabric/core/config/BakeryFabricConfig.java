package net.satisfy.bakery.fabric.core.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "bakery")
@Config.Gui.Background("minecraft:textures/block/bricks.png")
public class BakeryFabricConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public EffectsSettings effects = new EffectsSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public MiscSettings misc = new MiscSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public FoodSettings food = new FoodSettings();

    public static class EffectsSettings {
        @ConfigEntry.Gui.CollapsibleObject
        public VitalityEffectSettings vitalityEffect = new VitalityEffectSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public SugarRushEffectSettings sugarRushEffect = new SugarRushEffectSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public CompletionistBannerEffectSettings completionistBannerEffect = new CompletionistBannerEffectSettings();

        public static class VitalityEffectSettings {
            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
            public int vitalityEffectInterval = 10;

            @ConfigEntry.Gui.Tooltip
            public float vitalityEffectExhaustionReduction = 0.08f;
        }

        public static class SugarRushEffectSettings {
            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 1, max = 50)
            public int sugarRushMaxStacks = 10;

            @ConfigEntry.Gui.Tooltip
            public double sugarRushBonusPerStack = 0.02;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 1, max = 50)
            public int sugarRushAttackSpeedStacks = 5;

            @ConfigEntry.Gui.Tooltip
            public boolean sugarRushAttackSpeed = true;
        }

        public static class CompletionistBannerEffectSettings {
            @ConfigEntry.Gui.Tooltip
            public boolean bannerGiveEffect = true;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 1, max = 32)
            public int bannerEffectRadius = 8;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 0, max = 4)
            public int bannerEffectAmplifier = 1;
        }
    }

    public static class MiscSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean bannerShowTooltip = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showBakerStationInfo = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showJamPotInfo = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showDisplayInfo = true;

        @ConfigEntry.Gui.Tooltip
        public boolean needDungarees = false;

        @ConfigEntry.Gui.Tooltip
        public boolean animations = true;

        @ConfigEntry.Gui.Tooltip
        public boolean enableKneading = true;

        @ConfigEntry.Gui.Tooltip
        public boolean kneadingAnimation = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 50)
        public int kneadPresses = 8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 50)
        public int rollingPinPresses = 5;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 400)
        public int knifeDuration = 10;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 400)
        public int jamDuration = 16;

        @ConfigEntry.Gui.Tooltip
        public float knifeAttackSpeed = 2.0f;

        @ConfigEntry.Gui.Tooltip
        public float rollingPinAttackSpeed = 1.2f;
    }

    public static class FoodSettings {
        public int cakeDoughNutrition = 5;
        public float cakeDoughSaturationMod = 0.6f;
        public int sweetDoughNutrition = 5;
        public float sweetDoughSaturationMod = 0.6f;
        public int croissantNutrition = 5;
        public float croissantSaturationMod = 0.6f;
        public int croissantEffectDuration = 900;
        public int crustyBreadNutrition = 5;
        public float crustyBreadSaturationMod = 1.2f;
        public int crustyBreadEffectDuration = 4800;
        public int breadNutrition = 5;
        public float breadSaturationMod = 1.2f;
        public int breadEffectDuration = 4200;
        public int baguetteNutrition = 5;
        public float baguetteSaturationMod = 1.2f;
        public int baguetteEffectDuration = 4200;
        public int toastNutrition = 3;
        public float toastSaturationMod = 0.8f;
        public int toastEffectDuration = 5400;
        public int braidedBreadNutrition = 5;
        public float braidedBreadSaturationMod = 1.2f;
        public int braidedBreadEffectDuration = 4200;
        public int sandwichNutrition = 7;
        public float sandwichSaturationMod = 0.7f;
        public int sandwichEffectDuration = 1500;
        public int vegetableSandwichNutrition = 8;
        public float vegetableSandwichSaturationMod = 0.6f;
        public int vegetableSandwichEffectDuration = 1800;
        public int grilledSalmonSandwichNutrition = 6;
        public float grilledSalmonSandwichSaturationMod = 0.8f;
        public int grilledSalmonSandwichEffectDuration = 1200;
        public int grilledBaconSandwichNutrition = 7;
        public float grilledBaconSandwichSaturationMod = 0.7f;
        public int grilledBaconSandwichEffectDuration = 1200;
        public int breadWithJamNutrition = 5;
        public float breadWithJamSaturationMod = 0.5f;
        public int breadWithJamEffectDuration = 400;
        public int strawberryCakeSliceNutrition = 5;
        public float strawberryCakeSliceSaturationMod = 0.7f;
        public int strawberryCakeSliceEffectDuration = 600;
        public int sweetberryCakeSliceNutrition = 5;
        public float sweetberryCakeSliceSaturationMod = 0.7f;
        public int sweetberryCakeSliceEffectDuration = 600;
        public int chocolateCakeSliceNutrition = 5;
        public float chocolateCakeSliceSaturationMod = 0.7f;
        public int chocolateCakeSliceEffectDuration = 600;
        public int chocolateGateauSliceNutrition = 5;
        public float chocolateGateauSliceSaturationMod = 0.7f;
        public int chocolateGateauSliceEffectDuration = 600;
        public int bundtCakeSliceNutrition = 5;
        public float bundtCakeSliceSaturationMod = 0.7f;
        public int bundtCakeSliceEffectDuration = 600;
        public int linzerTartSliceNutrition = 5;
        public float linzerTartSliceSaturationMod = 0.7f;
        public int linzerTartSliceEffectDuration = 600;
        public int applePieSliceNutrition = 5;
        public float applePieSliceSaturationMod = 0.7f;
        public int applePieSliceEffectDuration = 600;
        public int glowberryPieSliceNutrition = 5;
        public float glowberryPieSliceSaturationMod = 0.7f;
        public int glowberryPieSliceEffectDuration = 600;
        public int chocolateTartSliceNutrition = 5;
        public float chocolateTartSliceSaturationMod = 0.7f;
        public int chocolateTartSliceEffectDuration = 600;
        public int puddingSliceNutrition = 5;
        public float puddingSliceSaturationMod = 0.7f;
        public int puddingSliceEffectDuration = 600;
        public int strawberryGlazedCookieNutrition = 3;
        public float strawberryGlazedCookieSaturationMod = 0.5f;
        public int strawberryGlazedCookieEffectDuration = 400;
        public int sweetberryGlazedCookieNutrition = 3;
        public float sweetberryGlazedCookieSaturationMod = 0.5f;
        public int sweetberryGlazedCookieEffectDuration = 400;
        public int chocolateGlazedCookieNutrition = 3;
        public float chocolateGlazedCookieSaturationMod = 0.5f;
        public int chocolateGlazedCookieEffectDuration = 400;
        public int strawberryCupcakeNutrition = 3;
        public float strawberryCupcakeSaturationMod = 0.5f;
        public int strawberryCupcakeEffectDuration = 400;
        public int sweetberryCupcakeNutrition = 3;
        public float sweetberryCupcakeSaturationMod = 0.5f;
        public int sweetberryCupcakeEffectDuration = 400;
        public int appleCupcakeNutrition = 3;
        public float appleCupcakeSaturationMod = 0.5f;
        public int appleCupcakeEffectDuration = 400;
        public int cornetNutrition = 3;
        public float cornetSaturationMod = 0.5f;
        public int cornetEffectDuration = 400;
        public int jamRollNutrition = 3;
        public float jamRollSaturationMod = 0.5f;
        public int jamRollEffectDuration = 400;
        public int waffleNutrition = 5;
        public float waffleSaturationMod = 0.5f;
        public int waffleEffectDuration = 800;
        public int chocolateTruffleNutrition = 2;
        public float chocolateTruffleSaturationMod = 0.4f;
        public int chocolateTruffleEffectDuration = 200;
        public int misslilituBiscuitNutrition = 6;
        public float misslilituBiscuitSaturationMod = 0.6f;
        public int misslilituBiscuitEffectDuration = 900;
        public int bunNutrition = 5;
        public float bunSaturationMod = 1.2f;
        public int bunEffectDuration = 2800;
    }
}
