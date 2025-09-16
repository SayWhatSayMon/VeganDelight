package net.player005.vegandelightfabric.labels;

import com.google.common.base.Stopwatch;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.player005.recipe_modification.api.RecipeModification;
import net.player005.recipe_modification.api.ResultItemModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeganLabels {

    private static final Map<Item, VeganStatus> veganFromRecipes = new HashMap<>();
    private static final ResultItemModifier resultModifier = (recipe, result, recipeInput) -> {
        if (recipeInput != null) modifyRecipeResult(recipeInput, result);
        return result;
    };

    @ApiStatus.Internal
    public static void init() {
        var timer = Stopwatch.createStarted();
        for (Item item : BuiltInRegistries.ITEM) {
            scanRecipesRecursively(item, new ArrayList<>(BuiltInRegistries.ITEM.size()));
        }
        LoggerFactory.getLogger(VeganLabels.class)
            .info("Scanned {} items for vegan recipes in {}", BuiltInRegistries.ITEM.size(), timer);
    }

    public static VeganStatus isVegan(ItemStack itemStack) {
        var fromComponent = itemStack.get(VeganDataComponents.is_vegan.value());
        if (fromComponent != null) return VeganStatus.fromBoolean(fromComponent);

        if (itemStack.is(VeganTags.vegan)) return VeganStatus.VEGAN;
        if (itemStack.is(VeganTags.not_vegan)) return VeganStatus.NOT_VEGAN;

        return veganFromRecipes.getOrDefault(itemStack.getItem(), VeganStatus.UNKNOWN);
    }

    private static void scanRecipesRecursively(final Item item, final List<Item> alreadyTraversed) {
        alreadyTraversed.add(item);
        final var recipes = RecipeModification.getRecipesByResult(item);

        var veganRecipes = 0;
        var nonVeganRecipes = 0;

        for (RecipeHolder<?> recipeHolder : recipes) {
            RecipeModification.modifyResultItem(recipeHolder.value(), resultModifier);
            if (recipeNotVegan(alreadyTraversed, recipeHolder.value())) nonVeganRecipes++;
            else veganRecipes++;
        }

        var result = VeganStatus.UNKNOWN;
        if (veganRecipes > nonVeganRecipes) result = VeganStatus.VEGAN;
        if (veganRecipes < nonVeganRecipes) result = VeganStatus.NOT_VEGAN;

        var differencePercentage = Math.abs(((float) veganRecipes / (float) nonVeganRecipes) - 1);
        if (veganRecipes + nonVeganRecipes > 1 && differencePercentage < 20)
            result = VeganStatus.UNKNOWN;

        veganFromRecipes.put(item, result);
    }

    private static boolean recipeNotVegan(List<Item> alreadyTraversed, Recipe<?> recipe) {
        for (var ingredient : recipe.getIngredients()) {
            for (var itemStack : ingredient.getItems()) {
                var vegan = isVegan(itemStack);

                if (vegan == VeganStatus.UNKNOWN) {
                    var unknownItem = itemStack.getItem();
                    if (!alreadyTraversed.contains(unknownItem))
                        scanRecipesRecursively(unknownItem, alreadyTraversed);
                    vegan = veganFromRecipes.getOrDefault(unknownItem, VeganStatus.UNKNOWN);
                }

                if (vegan == VeganStatus.NOT_VEGAN) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean shouldRenderTooltip(ItemStack itemStack) {
        return !Boolean.FALSE.equals(itemStack.get(VeganDataComponents.is_vegan.value())) &&
            !itemStack.is(VeganTags.vegan_alternative) &&
            itemStack.getComponents().has(VeganDataComponents.contains_substitutes.value());
    }

    public static void addTooltipLines(ItemStack itemStack, List<Component> tooltip) {
        if (!shouldRenderTooltip(itemStack)) return;
        tooltip.add(1, Component.literal("Vegan").setStyle(
            Style.EMPTY
                .withColor(0x008c44)
                .withItalic(true)
                .withBold(true)
        ));
    }

    public enum VeganStatus {
        VEGAN, NOT_VEGAN, UNKNOWN;

        static VeganStatus fromBoolean(@Nullable Boolean b) {
            if (b == null) return UNKNOWN;
            return b ? VEGAN : NOT_VEGAN;
        }
    }

    private static void modifyRecipeResult(RecipeInput recipeInput, ItemStack result) {
        for (var i = 0; i < recipeInput.size(); i++) {
            var item = recipeInput.getItem(i);
            if (isVegan(item) == VeganStatus.NOT_VEGAN) {
                result.applyComponents(VeganDataComponents.setIsNotVegan.get());
            }
            if (item.is(VeganTags.vegan_alternative)) {
                result.applyComponents(VeganDataComponents.setContainsSubstitutes.get());
            }
        }
        if (!result.has(VeganDataComponents.is_vegan.value()))
            result.applyComponents(VeganDataComponents.setIsVegan.get());
    }
}
