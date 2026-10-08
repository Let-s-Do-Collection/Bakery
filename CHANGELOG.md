[2.1.8]

**Fixed**
* Candles can now be put on all cakes, tarts and the pudding. Cakes, tarts and pies hold up to four candles of one color, one per slice. Chocolate Gateau, Bundt Cake and Pudding take one candle in the middle. Light them with Flint and Steel and blow them out with an empty hand. Cutting or eating a slice drops the candle
* Sugar Rush applied its effect twice, so the first bite already gave two stacks

**Added**
* EMI support for Baking Station and Jam Pot recipes
* Bakery Banner now has Legendary rarity
* Wall Display, Cake Display, Cupcake Display and Cake Stand: every spot now holds up to 64 of the same item, like the Ingredient Cubby. Right-click with items to put in as many as fit, right-click to take one out, sneak + right-click to take the whole stack. Fuller spots show a small pile, with the Cake and Cupcake Display and Cake Stand adding one more for every 8 items. Whole cakes on the Cake Stand stay single
* Wall Display, Cake Display, Cupcake Display and Cake Stand now show an info tooltip with the item and amount in the spot you're looking at. Tray and Bread Box show everything inside. It follows the Dungarees setting like the other info tooltips, except for the Cake Display and Cake Stand: their glass top always lets you see inside. All of this can be turned off with the new `showDisplayInfo` option
* Trays can now be dyed with any dye. They keep their color when picked up
* Baking now gives experience: finishing a cake, cupcake or cookie on the Baking Station and bottling a batch of jam from the Small Cooking Pot. The amount is set per recipe with the optional `experience` field and follows the Farm & Charm cooking experience config
* Baker Station: Sweet Dough now starts cupcakes and cookies. Place it on the station to get four dough cubes
  * Cut them with a knife and the pieces hop apart into cupcake blanks
  * Flatten them with a Rolling Pin, or knead them with both hands empty. Every click presses the dough a bit flatter (8 clicks by hand, 5 with the Rolling Pin), then cut the sheet into cookie blanks
  * Cake Dough keeps working as before and makes the cake base
* Baker Station: Jam Rolls. Put a Sponge Sheet on the station, spread a filling on it, roll it up with empty hands and cut it into five slices with a knife
* Baker Station: Cornets. Put up to four Cornet Shells on the station and fill them all at once, then take them with an empty hand
* Fillings: milk for cream, or any jam and chocolate spread from the Jam Pot. The filling shows in the color of the item and in its tooltip. Perfect jam gives one piece more
* Baker Station: clicking too hectically mushes the dough back together, so it needs a few more presses. Find the rhythm!
* Baker Station: right-click with flour for a puff of flour, just for fun
* Baker Station: put down up to two tools (knife, Rolling Pin) on the right and left half of the station, then take them back with an empty hand on the same side. Tools and dough can't share the station
* Baker Station: animations for every step. Jam now spreads over cakes, cupcakes and cookies from the middle outwards, with particles and sound
* Baker Station: the hitbox follows the dough while it is flattened or kneaded
* Baker Station: info overlay when looking at the station or the dough, showing what can be used next. Can be turned off in the config (`showBakerStationInfo`)
* Cutting Board assembly recipes (Farm & Charm): Bread with Jam, Sandwich, Vegetable Sandwich, Grilled Salmon Sandwich, Chocolate Box and Basket of Bread

**Changed**
* Baker Station recipe now needs Flour instead of Sugar
* Wooden furnitures (Cabinets, Drawers, Street Sign, Bread Box, Tray, Bread Crate, Wall Display) are now flammable
* Kitchen Sink: turn the tap on with an empty hand and it slowly fills up. Fill bottles three times, empty it with a bucket, wash dyed leather, banners and shulker boxes, or put yourself out when you're on fire :P
* The Small Cooking Pot is now the Jam Pot, built like the wok from Wok & Bowl. No more GUI:
  * Put fruit and sugar in by right-clicking, and it starts cooking on its own on a stove or campfire
  * Now and then the jam bubbles up and wants to be stirred with an empty hand. Missing it lets the jam catch at the bottom
  * Bottle it with empty jars. When you bottle it decides how it turned out: too early is runny (one jar more), right on time is perfect, too late or not stirred is caramelized (one jar less)
  * Perfect jam is called "Perfect Strawberry Jam" and signed by the cook. Decorating at the Baker Station with it pops out one extra slice, cupcake or cookie
  * Left on the fire far too long, the jam turns black and burns. Empty the pot before cooking again
  * You can feel the jam thicken: runny jam whisks easily and fast, perfect jam pushes back, caramelized jam is tough and burnt jam holds the whisk stuck
  * Sneak + right-click with an empty hand tips the pot over and empties it. Ingredients that are not cooking yet come back
  * The pot simmers, wobbles when it wants to be stirred and shakes along when you whisk fast. Hold right-click to whisk faster and faster. The whisk can always be spun, even in an empty pot
  * Bubbles, splashes and the whisk: the pot bubbles and splashes in the color of the jam
  * Info overlay shows what to do next (`showJamPotInfo` in the config)
* The stove now bakes Cornet Shells (from Sweet Dough) and Sponge Sheets (from Cake Dough) instead of finished Cornets and Jam Rolls. They are filled at the Baker Station
* Fruit jams and chocolate spread are now made in the Jam Pot only. Chocolate truffles and pudding stay with the Farm & Charm cooking pot
* Food left in an old Small Cooking Pot is lost when updating
* Chocolate Gateau is now coated with Chocolate Spread, while the Chocolate Cake is made with Chocolate Truffles
* Bread Knife and Rolling Pin swing a lot faster (Knife 2.0, Rolling Pin 1.2 attacks per second)
* Bread Knife now uses iron stats (3 damage), Rolling Pin uses wood stats (1 damage). Their tiers were swapped before
* Grilled Salmon Sandwich now needs cooked salmon and is made on the Cutting Board instead of the stove
* Chocolate Box is made on the Cutting Board from a chest and three Chocolate Truffles
* Basket of Bread is made on the Cutting Board instead of the crafting table
* Bread with Jam, Sandwich and Vegetable Sandwich are made on the Cutting Board instead of the crafting table
* Vegetable Sandwich now only needs bread, cabbage and tomato and yields two
* Info tooltips can now require Dungarees from Farm & Charm (`needDungarees`)
* New config options:
  * Sugar Rush: max stacks, bonus per stack, stacks before attack speed kicks in, attack speed on/off
  * Completionist Banner: effect radius and Resistance level
  * Baker Station: animations on/off, kneading on/off, clicks for kneading and the Rolling Pin, duration of knife and jam
  * Attack speed of the Bread Knife and Rolling Pin
  * Effect duration of every food

***

[2.1.7]

**Added**
* Added ja_jp translation (thanks to Anpan715)
* Vanilla Blend: an optional built-in resource pack with muted, vanilla-friendly colors for breads, cakes, tarts, cupcakes, cookies, jams, sandwiches, the Bread Knife and Mob Effect icons. Enable it in the Resource Packs menu. Palettes inspired by Vanilla, Farmer's Delight, Supplementaries and Create

**Changed**
* Updated ru_ru translation (thanks to Tefny)
* Updated zh_cn translation (thanks to Number_Sir)
* Fixed and updated it_it translation (thanks to Serena)
* Converted images from PNG to WEBP to reduce file size
* Optimized images (via ImgBot)

***

[2.1.6]

**Fixed**
* Game rushing during startup due to early initialization of recipe remainders
* TrayBlock hitbox not rotating with block facing
* BreadCrate missing blockBreak particles

**Added**
* Added zh_tw translation (thanks to cherrypuff1120)

***

[2.1.5]

**Fixed**
* Containers such as bottles, jars, bowls and buckets not being returned after cooking

**Changed**
* Added crafting remainder to Jam & Chocolate Spread
* Updated pack.png

***

[2.1.4]

**Added**
* Fixed Sugar Rush causing client and server crashes when saving player data

**Changed**
* Adjusted bread knife attributes: slightly increased damage, significantly reduced attack speed

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

