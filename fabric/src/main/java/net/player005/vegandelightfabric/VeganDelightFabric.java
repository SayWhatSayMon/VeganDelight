package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.player005.vegandelightfabric.fluids.FluidProperties;
import vectorwing.farmersdelight.common.registry.ModBiomeModifiers;

import java.util.concurrent.atomic.AtomicReference;

public class VeganDelightFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VeganDelightMod.initialiseAll(new VeganDelightFabricPlatform());

        ServerLifecycleEvents.SERVER_STARTED.register(server -> RecipeManipulation.load(server.getRecipeManager()));
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) ->
                RecipeManipulation.load(server.getRecipeManager())
        );
    }

    public static class VeganDelightFabricPlatform implements VeganDelightPlatform {

        @Override
        public FlowingFluid registerFluids(String name, FluidProperties properties) {
            var flowingRef = new AtomicReference<FlowingFluid>();
            var stillRef = new AtomicReference<FlowingFluid>();

            flowingRef.set(Registry.register(
                    BuiltInRegistries.FLUID,
                    ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name + "_flowing"),
                    new SimpleFlowableFluid.Flowing(properties, flowingRef::get, stillRef::get)
            ));

            stillRef.set(Registry.register(
                    BuiltInRegistries.FLUID,
                    ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name),
                    new SimpleFlowableFluid.Still(properties, flowingRef::get, stillRef::get)
            ));

            return flowingRef.get();
        }

        @Override
        public TagKey<Biome> undergroundBiomeTag() {
            return ConventionalBiomeTags.IS_UNDERGROUND;
        }

        @Override
        public void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied,
                                          GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier) {
            BiomeModifications.addFeature(
                    new ModBiomeModifiers.FDBiomeSelector(minTemp, maxTemp, allowed, denied),
                    step, modifier);
        }

        @Override
        public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
            TradeOfferHelper.registerVillagerOffers(profession, level, (factories) -> factories.add(itemListing));
        }
    }
}
