package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModBiomeModifiers;

public class VeganDelightFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VeganDelightMod.initialize(new VeganDelightFabricPlatform());

        ServerLifecycleEvents.SERVER_STARTED.register(server -> RecipeManipulation.load(server.getRecipeManager()));
    }

    public static class VeganDelightFabricPlatform implements VeganDelightPlatform {

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public void registerFluidHandler(Item full, Item empty, Fluid fluid, int millibuckets) {
            var fluidVariant = FluidVariant.of(fluid);
            FluidStorage.ITEM.registerForItems((itemStack, context) ->
                new FullItemFluidStorage(context, empty, fluidVariant, millibuckets * 81L), full);
            FluidStorage.combinedItemApiProvider(Items.BUCKET).register(context -> new EmptyItemFluidStorage(
                context, full, fluid, millibuckets * 81L
            ));
        }

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
        public void registerVillagerTrade(VillagerProfession profession, int level,
                                          VillagerTrades.ItemListing itemListing) {
            TradeOfferHelper.registerVillagerOffers(profession, level, (factories) -> factories.add(itemListing));
        }
    }
}
