package net.player005.vegandelightfabric;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.player005.vegandelightfabric.fluids.FluidProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Mod("vegandelight")
public class VeganDelightNeo {

    @SuppressWarnings("NotNullFieldNotInitialized")
    private static IEventBus eventBus;

    public VeganDelightNeo(IEventBus eventBus) {
        VeganDelightNeo.eventBus = eventBus;

        VeganDelightMod.initializeAll(new VDNeoforgePlatform());
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        for (VillagerTrade trade : VDNeoforgePlatform.registeredTrades) {
            if (event.getType() == trade.profession) {
                var levelTrades = event.getTrades().get(trade.level);

                levelTrades.add(trade.itemListing);
            }
        }
    }


    public static class VDNeoforgePlatform implements VeganDelightPlatform {

        public static final List<VillagerTrade> registeredTrades = new ArrayList<>();

        @SuppressWarnings("unchecked")
        @Override
        public <V, T extends V> Holder<T> register(Registry<V> registry, ResourceKey<V> rk, Supplier<T> supplier) {
            VeganDelightNeo.eventBus.<RegisterEvent>addListener(event ->
                event.register((ResourceKey<? extends Registry<T>>) registry.key(), rk.location(), supplier)
            );
            return (Holder<T>) DeferredHolder.create(registry.key(), rk.location());
        }

        @Override
        public TagKey<Biome> undergroundBiomeTag() {
            return TagKey.create(Registries.BIOME, ResourceLocation.parse("c:underground"));
        }

        @Override
        public void registerVillagerTrade(VillagerProfession profession, int level,
                                          VillagerTrades.ItemListing itemListing) {
            registeredTrades.add(new VillagerTrade(profession, level, itemListing));
        }

        // biome modifiers for neoforge are registered in datapack via json files
        @Override
        public void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied,
                                          GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier) { }

        @Override
        public Supplier<FlowingFluid> registerFluids(final String name, final FluidProperties properties) {

            var fluidType = createFluidType(name);

            // Necessary because java
            var stillRef = new AtomicReference<BaseFlowingFluid.Source>();
            var flowingRef = new AtomicReference<BaseFlowingFluid.Flowing>();

            var fluidProperties = new BaseFlowingFluid.Properties(() -> fluidType, stillRef::get, flowingRef::get)
                .block(properties.block())
                .bucket(properties.bucket())
                .levelDecreasePerBlock(properties.levelDecreasePerBlock())
                .explosionResistance(properties.explosionResistance())
                .tickRate(properties.tickRate())
                .slopeFindDistance(properties.slopeFindDistance());

            register(NeoForgeRegistries.FLUID_TYPES,
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name),
                () -> fluidType);

            register(BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name),
                () -> {
                    flowingRef.set(new BaseFlowingFluid.Flowing(fluidProperties));
                    return flowingRef.get();
                });
            register(BuiltInRegistries.FLUID,
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "flowing_" + name),
                () -> {
                    stillRef.set(new BaseFlowingFluid.Source(fluidProperties));
                    return stillRef.get();
                });

            return flowingRef::get;
        }

        @Override
        public void registerCompostables() { } // compostables are registered as a data map in datapack

        @Override
        public boolean isModLoaded(String name) {
            return ModList.get().isLoaded(name);
        }

    }

    private static FluidType createFluidType(String name) {
        var properties = FluidType.Properties.create();
        var fluidType = new FluidType(properties);

        var clientFluidExtensions = new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "block/" + name + "_still");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "block/" + name + "_flowing");
            }
        };
        VeganDelightNeo.eventBus.<RegisterClientExtensionsEvent>addListener(event -> event.registerFluidType(clientFluidExtensions, fluidType));

        return fluidType;
    }

    public record VillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) { }
}
