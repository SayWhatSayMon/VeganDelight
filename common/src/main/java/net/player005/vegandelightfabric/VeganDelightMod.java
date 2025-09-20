package net.player005.vegandelightfabric;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.player005.recipe_modification.api.RecipeFilter;
import net.player005.recipe_modification.api.RecipeModification;
import net.player005.recipe_modification.api.RecipeModifier;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import net.player005.vegandelightfabric.labels.VeganLabels;
import net.player005.vegandelightfabric.labels.VeganDataComponents;

public class VeganDelightMod {

    public static String modID = "vegandelight";
    @SuppressWarnings("NotNullFieldNotInitialized")
    private static VeganDelightPlatform platform;

    public static void initializeAll(VeganDelightPlatform platform) {
        VeganDelightMod.platform = platform;

        VeganFluids.initialize();
        VeganItems.initialize();
        VeganBlocks.initialize();
        VeganCreativeTab.initialize();
        VeganDataComponents.initialize();

        RecipeModification.onRecipeInit(recipeManager -> {
            VeganLabels.initialize();
            registerSubstitutes();
        });
        RatsCompat.initialize();

        registerBiomeModifers();
        registerTrades();

        platform.registerCompostables();
    }

    private static void registerSubstitutes() {
        RecipeModification.registerModifier(
            ResourceLocation.parse("vegandelight:leather_substitute"),
            RecipeFilter.acceptsIngredient(Items.LEATHER.getDefaultInstance()),
            RecipeModifier.addAlternative(Items.LEATHER, VeganItems.LEATHER_SUBSTITUTE.value())
        );
    }

    private static void registerBiomeModifers() {
        getPlatform().registerBiomeModifier(0.4f, 0.9f,
            getPlatform().overworldBiomeTag(),
            getPlatform().undergroundBiomeTag(),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.parse("vegandelight:patch_wild_soybean"))
        );
    }

    private static void registerTrades() {
        getPlatform().registerVillagerTrade(VillagerProfession.FARMER, 1,
            (trader, random) -> new MerchantOffer(
                new ItemCost(VeganItems.SOYBEAN.value(), random.nextIntBetweenInclusive(16, 24)),
                new ItemStack(Items.EMERALD, 1),
                12, 5, 0.05f
            ));
        getPlatform().registerVillagerTrade(VillagerProfession.LEATHERWORKER, 4,
            (trader, random) -> new MerchantOffer(
                new ItemCost(VeganItems.LEATHER_SUBSTITUTE.value(), random.nextIntBetweenInclusive(8, 16)),
                new ItemStack(Items.EMERALD, 1),
                12, 15, 0.1f
            ));
    }

    public static VeganDelightPlatform getPlatform() {
        return platform;
    }
}
