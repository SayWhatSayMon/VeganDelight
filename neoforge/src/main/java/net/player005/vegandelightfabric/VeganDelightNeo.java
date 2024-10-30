package net.player005.vegandelightfabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import net.player005.vegandelightfabric.fluids.FluidProperties;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Mod("vegandelight")
public class VeganDelightNeo {

    @SuppressWarnings("NotNullFieldNotInitialized")
    public static IEventBus eventBus;

    public VeganDelightNeo(@NotNull IEventBus eventBus) {
        VeganDelightNeo.eventBus = eventBus;
        VeganDelightMod.platform = new VDNeoforgePlatform();

        VeganDelightMod.registerBiomeModifers();
        VeganDelightMod.registerTrades();

        eventBus.<RegisterEvent>addListener(event -> {
            event.register(Registries.BLOCK, helper -> VeganBlocks.initialise());
            event.register(Registries.ITEM, helper -> VeganItems.initialise());
            event.register(Registries.CREATIVE_MODE_TAB, helper -> VeganCreativeTab.register());
            event.register(Registries.FLUID, helper -> VeganFluids.initialise());
        });

        NeoForge.EVENT_BUS.<ServerStartingEvent>addListener(event -> {
            VeganDelightMod.registerSubstitutes();
            RecipeManipulation.load(event.getServer().getRecipeManager());
        });
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

        @Override
        public TagKey<Biome> undergroundBiomeTag() {
            return TagKey.create(Registries.BIOME, ResourceLocation.parse("c:underground"));
        }

        @Override
        public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
            registeredTrades.add(new VillagerTrade(profession, level, itemListing));
        }

        @Override
        public void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied, GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier) {
        }

        @Override
        public FlowingFluid registerFluids(final String name, final @NotNull FluidProperties properties) {

            var fluidType = createFluidType(name);

            // Necessary because java
            var flowingRef = new AtomicReference<BaseFlowingFluid.Flowing>();
            var stillRef = new AtomicReference<BaseFlowingFluid.Source>();

            var fluidProperties = new BaseFlowingFluid.Properties(() -> fluidType, stillRef::get, flowingRef::get)
                    .block(properties.block())
                    .bucket(properties.bucket())
                    .levelDecreasePerBlock(properties.levelDecreasePerBlock())
                    .explosionResistance(properties.explosionResistance())
                    .tickRate(properties.tickRate())
                    .slopeFindDistance(properties.slopeFindDistance());

            flowingRef.set(new BaseFlowingFluid.Flowing(fluidProperties));
            stillRef.set(new BaseFlowingFluid.Source(fluidProperties));

//            var typeRegister = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, VeganDelightMod.modID);
//            typeRegister.register(name, () -> fluidType);
//            typeRegister.register(VeganDelightNeo.eventBus);

            Registry.register(NeoForgeRegistries.FLUID_TYPES,
                    ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name),
                    fluidType);

            Registry.register(BuiltInRegistries.FLUID,
                    ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name),
                    stillRef.get());
            Registry.register(BuiltInRegistries.FLUID,
                    ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "flowing_" + name),
                    flowingRef.get());

//            var fluidRegister = DeferredRegister.create(Registries.FLUID, VeganDelightMod.modID);
//            fluidRegister.register(name, stillRef::get);
//            fluidRegister.register(name + "_flowing", flowingRef::get);
//            fluidRegister.register(VeganDelightNeo.eventBus);

            return flowingRef.get();
        }

    }

    private static @NotNull FluidType createFluidType(String name) {
        var properties = FluidType.Properties.create();
        return new FluidType(properties) {
            @SuppressWarnings("removal")
            @Override
            public void initializeClient(@NotNull Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name + "_still");
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name + "_flowing");
                    }
                });
            }
        };
    }

    public record VillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
    }
}
