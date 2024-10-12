package net.player005.vegandelightfabric;

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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod("vegandelight")
public class VeganDelightNeo {
    public static IEventBus eventBus;

    public VeganDelightNeo(IEventBus eventBus) {
        VeganDelightNeo.eventBus = eventBus;

        VeganDelightMod.initialize(new VDNeoforgePlatform());
    }

    @Mod.EventBusSubscriber(modid = "vegandelight")
    public static class EventSubscriber {
        public EventSubscriber() {
            NeoForge.EVENT_BUS.register(this);
        }

        public static void onVillagerTrades(VillagerTradesEvent event) {
            for (VillagerTrade trade : VDNeoforgePlatform.registeredTrades) {
                if (event.getType() == trade.profession) {
                    var levelTrades = event.getTrades().get(trade.level);

                    levelTrades.add(trade.itemListing);
                }
            }
        }
    }

    public static class VDNeoforgePlatform implements VeganDelightPlatform {
        public static final List<VillagerTrade> registeredTrades = new ArrayList<>();

        @Override
        public TagKey<Biome> undergroundBiomeTag() {
            return TagKey.create(Registries.BIOME, new ResourceLocation("c:underground"));
        }

        @Override
        public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
            registeredTrades.add(new VillagerTrade(profession, level, itemListing));
        }

        @Override
        public CreativeModeTab registerItemTab(ItemStack icon, @NotNull Component title, ItemLike... items) {
            var tabDeferredRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VeganDelightMod.modID);
            var tab = tabDeferredRegister.register(title.getString(), () -> CreativeModeTab.builder()
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
            // biome modifiers are loaded via file in forge/neoforge
        }
    }

    public record VillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing) {
    }
}
