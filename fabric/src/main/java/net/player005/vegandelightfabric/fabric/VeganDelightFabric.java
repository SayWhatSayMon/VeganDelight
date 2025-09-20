package net.player005.vegandelightfabric.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.fabricmc.loader.api.FabricLoader;
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
import net.player005.vegandelightfabric.VeganDelightMod;
import net.player005.vegandelightfabric.VeganDelightPlatform;
import net.player005.vegandelightfabric.fabric.client.VeganDelightClient;
import net.player005.vegandelightfabric.fluids.FluidProperties;
import vectorwing.farmersdelight.common.registry.ModBiomeModifiers;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class VeganDelightFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VeganDelightMod.initializeAll(new VeganDelightFabricPlatform());
    }

    public static class VeganDelightFabricPlatform implements VeganDelightPlatform {

        @Override
        public boolean isModLoaded(String name) {
            return FabricLoader.getInstance().isModLoaded(name);
        }

        @Override
        public Supplier<FlowingFluid> registerFluids(String name, FluidProperties properties) {
            // we need to use some kind of references or java will complain
            final AtomicReference<FlowingFluid> flowingRef = new AtomicReference<>();
            final var stillRef = new AtomicReference<FlowingFluid>();

            final FlowingFluid flowing = Registry.register(
                BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "flowing_" + name),
                new SimpleFlowableFluid.Flowing(properties, flowingRef::get, stillRef::get)
            );
            flowingRef.set(flowing);

            final var still = Registry.register(
                BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name),
                new SimpleFlowableFluid.Still(properties, flowingRef::get, stillRef::get)
            );
            stillRef.set(still);

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                VeganDelightClient.registerFluidRenderers(name, still, flowing);
            }

            return flowingRef::get;
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
        public void registerVillagerTrade(VillagerProfession profession, int level,
                                          VillagerTrades.ItemListing itemListing) {
            TradeOfferHelper.registerVillagerOffers(profession, level, (factories) -> factories.add(itemListing));
        }
    }
}
