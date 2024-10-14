package net.player005.vegandelightfabric;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod("vegandelight")
public class VeganDelightNeo {
    public static IEventBus eventBus;

    public VeganDelightNeo(@NotNull IEventBus eventBus) {
        VeganDelightNeo.eventBus = eventBus;
//        eventBus.addListener(VeganDelightNeo::onVillagerTrades);

        VeganDelightMod.initPlatform(new VDNeoforgePlatform());
//        VeganDelightMod.initialize(); TODO

        eventBus.<RegisterEvent>addListener(event -> {
            event.register(BuiltInRegistries.BLOCK.key(), helper -> VeganBlocks.initialise());
            event.register(BuiltInRegistries.ITEM.key(), helper -> VeganItems.initialise());
            event.register(BuiltInRegistries.CREATIVE_MODE_TAB.key(), helper -> VeganCreativeTab.initialise());
            event.register(BuiltInRegistries.FLUID.key(), helper -> VeganFluids.initialise());
//            event.register(NeoForgeDataMaps.COMPOSTABLES.registryKey(), helper -> VeganDelightMod.initialize());
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
        public CreativeModeTab registerItemTab(ItemStack icon, @NotNull Component title, ItemLike... items) {
            var tabDeferredRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VeganDelightMod.modID);
            var tab = tabDeferredRegister.register(VeganDelightMod.modID, () -> CreativeModeTab.builder()
                    .icon(() -> icon)
                    .title(title)
                    .displayItems((parameters, output) -> {
                        for (ItemLike item : items) {
                            output.accept(item);
                        }
                    }).build());
            tabDeferredRegister.register(VeganDelightNeo.eventBus);
            return tab.get();
        }

        @Override
        public void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied, GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier) {
        }
    }

    public record VillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
    }
}
