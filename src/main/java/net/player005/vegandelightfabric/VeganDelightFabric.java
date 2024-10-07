package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModBiomeModifiers;

public class VeganDelightFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VeganDelightMod.initialize(new VeganDelightFabricPlatform());

        registerBiomeModifers();
    }

    private void registerBiomeModifers() {
        BiomeModifications.addFeature(
                new ModBiomeModifiers.FDBiomeSelector(
                        0.4f, 0.9f,
                        BiomeTags.IS_OVERWORLD, ConventionalBiomeTags.UNDERGROUND),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation("vegandelight:patch_wild_soybean"))
        );
    }

    public static class VeganDelightFabricPlatform implements VeganDelightPlatform {
        @Override
        public TagKey<Biome> undergroundBiomeTag() {
            return ConventionalBiomeTags.UNDERGROUND;
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
