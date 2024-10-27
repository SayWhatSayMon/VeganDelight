package net.player005.vegandelightfabric;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluid;
import net.player005.vegandelightfabric.fluids.SimpleFlowableFluid;

public interface VeganDelightPlatform {
    TagKey<Biome> undergroundBiomeTag();

    default TagKey<Biome> overworldBiomeTag() {
        return BiomeTags.IS_OVERWORLD;
    }

    void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing);

    void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied,
                               GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier);

    Fluid createStillFluid(SimpleFlowableFluid.Properties properties);

    Fluid createFlowingFluid(SimpleFlowableFluid.Properties properties);
}
