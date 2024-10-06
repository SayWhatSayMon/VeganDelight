package net.player005.vegandelightfabric;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.biome.Biome;

public interface VeganDelightPlatform {
    TagKey<Biome> undergroundBiomeTag();

    default TagKey<Biome> overworldBiomeTag() {
        return BiomeTags.IS_OVERWORLD;
    }

    void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing);
}
