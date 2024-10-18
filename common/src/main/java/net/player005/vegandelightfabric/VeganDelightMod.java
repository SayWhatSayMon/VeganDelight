package net.player005.vegandelightfabric;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import org.jetbrains.annotations.NotNull;

public class VeganDelightMod {

    public static String modID = "vegandelight";

    public static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(VeganItems.SOYBEAN, 0.45f);
        ComposterBlock.COMPOSTABLES.put(VeganBlocks.WILD_SOYBEAN.asItem(), 0.65f);
    }

    public static void initialiseAll(VeganDelightPlatform platform) {
        VeganItems.initialise();
        VeganFluids.initialise();
        VeganBlocks.initialise();
        VeganCreativeTab.register();

        registerBiomeModifers(platform);
        registerTrades(platform);
        registerCompostables();
    }

    public static void registerBiomeModifers(@NotNull VeganDelightPlatform platform) {
        platform.registerBiomeModifier(0.4f, 0.9f,
                platform.overworldBiomeTag(),
                platform.undergroundBiomeTag(),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.parse("vegandelight:patch_wild_soybean"))
        );
    }

    public static void registerTrades(@NotNull VeganDelightPlatform platform) {
        platform.registerVillagerTrade(VillagerProfession.FARMER, 1,
                (trader, random) -> new MerchantOffer(
                        new ItemCost(VeganItems.SOYBEAN, random.nextIntBetweenInclusive(16, 24)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 5, 0.05f
                ));
        platform.registerVillagerTrade(VillagerProfession.LEATHERWORKER, 4,
                (trader, random) -> new MerchantOffer(
                        new ItemCost(VeganItems.LEATHER_SUBSTITUTE, random.nextIntBetweenInclusive(8, 16)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 15, 0.1f
                ));
    }

}
