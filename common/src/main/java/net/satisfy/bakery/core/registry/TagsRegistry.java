package net.satisfy.bakery.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.satisfy.bakery.core.util.BakeryIdentifier;

public class TagsRegistry {

    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, BakeryIdentifier.identifier("ignore_block_item"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registries.BLOCK, BakeryIdentifier.identifier("can_not_connect"));
    public static final TagKey<Item> BREAD = TagKey.create(Registries.ITEM, BakeryIdentifier.identifier("bread"));
    public static final TagKey<Item> KNIVES = TagKey.create(Registries.ITEM, BakeryIdentifier.identifier("knives"));
}
