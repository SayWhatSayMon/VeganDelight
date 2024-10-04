package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import vectorwing.farmersdelight.common.registry.ModBiomeModifiers;

public class VeganDelight implements ModInitializer {
    public static String modID = "vegandelight";

    @Override
    public void onInitialize() {
        VeganItems.initialize();
        VeganFluids.initialise();
        VeganBlocks.initialise();

        registerTrades();
        registerBiomeModifers();

        ComposterBlock.COMPOSTABLES.put(VeganItems.SOYBEAN, 0.45f);
        ComposterBlock.COMPOSTABLES.put(VeganBlocks.WILD_SOYBEAN.asItem(), 0.65f);
    }

    private void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                (factories) -> factories.add((trader, random) -> new MerchantOffer(
                        new ItemStack(VeganItems.SOYBEAN, random.nextIntBetweenInclusive(16, 24)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 5, 0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LEATHERWORKER, 4,
                (factories) -> factories.add((trader, random) -> new MerchantOffer(
                        new ItemStack(VeganItems.LEATHER_SUBSTITUTE, random.nextIntBetweenInclusive(8, 16)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 15, 0.1f
                )));
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
}
