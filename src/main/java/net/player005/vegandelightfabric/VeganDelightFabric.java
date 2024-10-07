package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModBiomeModifiers;

public class VeganDelightFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VeganDelightMod.initialize(new VeganDelightFabricPlatform());
    }

    public static class VeganDelightFabricPlatform implements VeganDelightPlatform {
        @Override
        public TagKey<Biome> undergroundBiomeTag() {
            return ConventionalBiomeTags.UNDERGROUND;
        }

        @Override
        public void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied,
                                          GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier) {
            BiomeModifications.addFeature(
                    new ModBiomeModifiers.FDBiomeSelector(minTemp, maxTemp, allowed, denied),
                    step, modifier);
        }

        @Override
        public CreativeModeTab registerItemTab(ItemStack icon, Component title, @NotNull ItemLike... items) {
            return FabricItemGroup.builder().icon(() -> icon).title(title)
                    .displayItems((itemDisplayParameters, output) -> {
                        for (@NotNull ItemLike item : items) {
                            output.accept(item);
                        }
                    }).build();
        }

        @Override
        public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
            TradeOfferHelper.registerVillagerOffers(profession, level, (factories) -> factories.add(itemListing));
        }
    }
}
