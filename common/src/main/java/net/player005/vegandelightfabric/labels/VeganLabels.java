package net.player005.vegandelightfabric.labels;

import com.google.common.base.Stopwatch;
import com.mojang.datafixers.util.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.player005.recipe_modification.api.RecipeModification;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeganLabels {

    private static final Map<Item, VeganStatus> veganFromRecipes = new HashMap<>();

    @ApiStatus.Internal
    public static void initialize() {
        RecipeModification.registerGlobalResultModifier((recipe, result, recipeInput) -> {
            if (recipeInput != null) modifyRecipeResult(recipeInput, result);
            return result;
        });
        var timer = Stopwatch.createStarted();
        var traversedList = new ArrayList<Item>(BuiltInRegistries.ITEM.size());
        for (Item item : BuiltInRegistries.ITEM) {
            if (!traversedList.contains(item))
                scanRecipesRecursively(item, traversedList);
        }
        LoggerFactory.getLogger(VeganLabels.class)
            .info("Scanned {} items for vegan recipes in {}", BuiltInRegistries.ITEM.size(), timer);
    }

    public static VeganStatus isVegan(ItemStack itemStack) {
        var fromComponent = itemStack.get(VeganDataComponents.is_vegan.value());
        if (fromComponent != null) return VeganStatus.fromBoolean(fromComponent);

        if (itemStack.is(VeganTags.VEGAN)) return VeganStatus.VEGAN;
        if (itemStack.is(VeganTags.NOT_VEGAN)) return VeganStatus.NOT_VEGAN;

        return veganFromRecipes.getOrDefault(itemStack.getItem(), VeganStatus.UNKNOWN);
    }

    public static void setIsVegan(ItemStack stack, boolean isVegan) {
        var isFood = stack.is(VeganTags.SHOULD_HAVE_DATA_COMPONENTS_ADDED) ||
            stack.getUseAnimation() == UseAnim.EAT || stack.getUseAnimation() == UseAnim.DRINK;

        if (!isFood && !stack.has(VeganDataComponents.contains_substitutes.value())) return;

        var currentStatus = isVegan(stack);
        if (stack.has(VeganDataComponents.is_vegan.value()))
            return;
        if (isVegan && (currentStatus == VeganStatus.VEGAN || currentStatus == VeganStatus.UNKNOWN))
            return;
        if (!isVegan && currentStatus == VeganStatus.NOT_VEGAN)
            return;

        stack.set(VeganDataComponents.is_vegan.value(), isVegan);
    }

    private static void scanRecipesRecursively(final Item item, final List<Item> alreadyTraversed) {
        alreadyTraversed.add(item);
        final var recipes = RecipeModification.getRecipesByResult(item);

        var veganRecipes = 0;
        var nonVeganRecipes = 0;

        for (RecipeHolder<?> recipeHolder : recipes) {
            if (recipeNotVegan(alreadyTraversed, recipeHolder.value())) nonVeganRecipes++;
            else veganRecipes++;
        }

        var result = VeganStatus.UNKNOWN;
        if (veganRecipes > nonVeganRecipes) result = VeganStatus.VEGAN;
        if (veganRecipes < nonVeganRecipes) result = VeganStatus.NOT_VEGAN;

        var differencePercentage = (int)
            (100f * (float) Math.abs(veganRecipes - nonVeganRecipes) / (float) recipes.size());
        if (recipes.size() > 1 && differencePercentage < 20)
            result = VeganStatus.UNKNOWN;

        veganFromRecipes.put(item, result);
    }

    private static boolean recipeNotVegan(List<Item> alreadyTraversed, Recipe<?> recipe) {
        for (var ingredient : recipe.getIngredients()) {

            var nonVeganItems = 0;
            var total = 0;

            for (var itemStack : ingredient.getItems()) {
                if (itemStack.is(VeganTags.VEGAN_ALTERNATIVE))
                    continue;
                var vegan = isVegan(itemStack);

                if (vegan == VeganStatus.UNKNOWN) {
                    var unknownItem = itemStack.getItem();
                    if (!alreadyTraversed.contains(unknownItem))
                        scanRecipesRecursively(unknownItem, alreadyTraversed);
                    vegan = veganFromRecipes.getOrDefault(unknownItem, VeganStatus.UNKNOWN);
                }

                if (vegan == VeganStatus.NOT_VEGAN) nonVeganItems++;
                total++;
            }
            if ((float) nonVeganItems / total >= 0.5f)
                return true;
        }
        return false;
    }

    private static boolean shouldRenderTooltip(ItemStack itemStack) {
        return !Boolean.FALSE.equals(itemStack.get(VeganDataComponents.is_vegan.value())) &&
            !itemStack.is(VeganTags.VEGAN_ALTERNATIVE) &&
            itemStack.getComponents().has(VeganDataComponents.contains_substitutes.value());
    }

    public static void addTooltipLines(ItemStack stack, List<Component> tooltip) {
        if (Boolean.FALSE.equals(stack.get(VeganDataComponents.is_vegan.value()))) {
            tooltip.add(1, Component.literal("Not vegan").setStyle(
                Style.EMPTY
                    .withColor(ChatFormatting.GRAY)
                    .withItalic(true)
            ));
        }
        var debugMode = true;

        if (debugMode) {
            tooltip.add(1, Component.literal("Result: " + isVegan(stack).name()));
            tooltip.add(1, Component.literal("Default from recipes: " + veganFromRecipes.get(stack.getItem())));
            tooltip.add(1, Component.literal("Component: " + stack.get(VeganDataComponents.is_vegan.value())));
        }

        if (!shouldRenderTooltip(stack)) return;
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
        var vegan = true;
        for (var i = 0; i < recipeInput.size(); i++) {
            var item = recipeInput.getItem(i);
            if (isVegan(item) == VeganStatus.NOT_VEGAN) {
                vegan = false;
            }
            if (item.is(VeganTags.VEGAN_ALTERNATIVE) || item.has(VeganDataComponents.contains_substitutes.value())) {
                result.set(VeganDataComponents.contains_substitutes.value(), Unit.INSTANCE);
            }
        }
        setIsVegan(result, vegan);
    }
}
