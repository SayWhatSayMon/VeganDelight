package net.player005.vegandelightfabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.player005.vegandelightfabric.fluids.FluidProperties;

import java.util.function.Supplier;

public interface VeganDelightPlatform {

    default void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(VeganItems.SOYBEAN.value(), 0.45f);
        ComposterBlock.COMPOSTABLES.put(VeganBlocks.WILD_SOYBEAN.value().asItem(), 0.65f);
    }

    default <V, T extends V> Holder<T> register(Registry<V> registry, ResourceLocation rl, Supplier<T> supplier) {
        return register(registry, ResourceKey.create(registry.key(), rl), supplier);
    }

    @SuppressWarnings("unchecked")
    default <V, T extends V> Holder<T> register(Registry<V> registry, ResourceKey<V> rk, Supplier<T> supplier) {
        return (Holder<T>) Registry.registerForHolder(registry, rk, supplier.get());
    }

    TagKey<Biome> undergroundBiomeTag();

    default TagKey<Biome> overworldBiomeTag() {
        return BiomeTags.IS_OVERWORLD;
    }

    void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing itemListing);

    void registerBiomeModifier(float minTemp, float maxTemp, TagKey<Biome> allowed, TagKey<Biome> denied,
                               GenerationStep.Decoration step, ResourceKey<PlacedFeature> modifier);

    Supplier<FlowingFluid> registerFluids(String name, FluidProperties properties);

    boolean isModLoaded(String name);
}
