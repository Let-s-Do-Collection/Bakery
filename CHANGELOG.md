[2.1.4]

**Added**
* Fixed Sugar Rush causing client and server crashes when saving player data

***

[2.1.3]

**Requires Farm & Charm 1.1.15+**

**Added**
* Sugar Rush: Increases movement speed by 2% per stack, up to 10%. At 5 stacks, also increases attack speed by 2% per stack, stacking up to 10 times
* Vitality: Periodically reduces player exhaustion, slowing down hunger depletion

**Changed**
* Removed BakeryIdentifier utility and moved identifier helper directly into the Bakery class
* Reduced overly saturated textures (work in progress)
* BakerStation Recipes are now Datadriven
* Most FoodItems now using the 2 new Effects
* Jam and Chocolate Spread can now be stacked up to 4 times

***

[2.1.2]

**Fixed**
* CompletionistBanner applying the wrong Effect to nearby Players
* Fixed Bakery config not applying values correctly

***

[2.1.1-neoforge]

**Fixed**
- Removed invalid mixin configuration in `neoforge.mods.toml` that caused crashes on NeoForge startup.

*** 

[2.1.1]

**Changed**
* Dough Recipes now using Flour instead of Wheat 
* *Effect registration now uses Farm & Charm’s unified Registration

**Fixed**
* Iron Bench not being mineable faster when using a Pickaxe

***

[2.1.0]

**Welcome to 1.21.1**

***

[2.0.5]

**Added**
* You can now add your own Text to StreetSigns

**Changed**
* Minecraft:Bread can now be stored in WallDisplays as well

**Fixed**
* Baby Zombies wont spawn with Bakery Items anymore

***

[2.0.4] 

**Added**
* Zombies have low Chance to spawn wielding a Small Cooking Pot, Rolling Pin or Bread Knife as a Weapon

**Changed**
* Increased BrickCounter crafting result count from "1" to "3"
* Increased Drawer crafting result count from "1" to "2"
* Increased Cabinet crafting result count from "1" to "2"
* Increased Wall Cabinet crafting result count from "1" to "2"
* Increased Wall Display crafting result count from "1" to "2"
* Reduced Iron Bench crafting result count to "4" to "2"
* Improved Cake Stand Texture
* Adjusted all Recipe .json the the new format

**Fixed**
* You can now safely eat Cake. Your game won't crash anymore. 

