package net.player005.vegandelightfabric.labels;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.player005.vegandelightfabric.VeganDelightMod;

public class VeganTags {

    public static final TagKey<Item> vegan_alternative = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "vegan_alternative"));

    public static final TagKey<Item> vegan = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "vegan"));

    public static final TagKey<Item> not_vegan = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "not_vegan"));
}
