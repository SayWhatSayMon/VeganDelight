package net.player005.vegandelightfabric;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelightMod {

    public static String modID = "vegandelight";
    public static VeganDelightPlatform platform;

    public static void initialize(VeganDelightPlatform platform) {
        VeganDelightMod.platform = platform;

        VeganItems.initialize();
        VeganFluids.initialise();
        VeganBlocks.initialise();

        registerBiomeModifers();
        registerTrades();

        ComposterBlock.COMPOSTABLES.put(VeganItems.SOYBEAN, 0.45f);
        ComposterBlock.COMPOSTABLES.put(VeganBlocks.WILD_SOYBEAN.asItem(), 0.65f);
    }

    private static void registerBiomeModifers() {
        platform.registerBiomeModifier(0.4f, 0.9f,
                platform.overworldBiomeTag(),
                platform.undergroundBiomeTag(),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation("vegandelight:patch_wild_soybean"))
        );
    }

    private static void registerTrades() {
        platform.registerVillagerTrade(VillagerProfession.FARMER, 1,
                (trader, random) -> new MerchantOffer(
                        new ItemStack(VeganItems.SOYBEAN, random.nextIntBetweenInclusive(16, 24)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 5, 0.05f
                ));
        platform.registerVillagerTrade(VillagerProfession.LEATHERWORKER, 4,
                (trader, random) -> new MerchantOffer(
                        new ItemStack(VeganItems.LEATHER_SUBSTITUTE, random.nextIntBetweenInclusive(8, 16)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 15, 0.1f
                ));
    }
}
