package net.player005.vegandelightfabric;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;

public interface VeganDelightPlatform {
    TagKey<Biome> undergroundBiomeTag();

    default TagKey<Biome> overworldBiomeTag() {
        return BiomeTags.IS_OVERWORLD;
    }

    void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing);

    CreativeModeTab registerItemTab(ItemStack icon, Component title, ItemLike... items);
}
