package net.player005.vegandelightfabric;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import net.player005.vegandelightfabric.fluids.SimpleFlowableFluid;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod("vegandelight")
public class VeganDelightNeo {

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
        public SimpleFlowableFluid createStillFluid(SimpleFlowableFluid.Properties properties) {
            return new SimpleFlowableFluid.Flowing(properties) {
                @Override
                public @NotNull FluidType getFluidType() {
                    return new FluidType(FluidType.Properties.create());
                }
            };
        }

        @Override
        public SimpleFlowableFluid createFlowingFluid(SimpleFlowableFluid.Properties properties) {
            return new SimpleFlowableFluid.Flowing(properties) {
                @Override
                public @NotNull FluidType getFluidType() {
                    return new FluidType(FluidType.Properties.create());
                }
            };
        }
    }

    public record VillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
    }
}
