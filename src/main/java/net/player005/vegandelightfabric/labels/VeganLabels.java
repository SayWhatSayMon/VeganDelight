package net.player005.vegandelightfabric.labels;

import com.google.common.base.Stopwatch;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Recipe;
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

    private static boolean debugMode = false;

    @ApiStatus.Internal
    public static void initialize() {
        RecipeModification.registerGlobalResultModifier((recipe, result, recipeInput) -> {
            if (recipeInput != null) {
                var list = new ArrayList<ItemStack>();
                for (var i = 0; i < recipeInput.getContainerSize(); i++) {
                    var stack = recipeInput.getItem(i);
                    if (!stack.isEmpty()) list.add(stack);
                }
                modifyRecipeResult(list, result);
            }
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
        var fromComponent = VeganNBT.getVeganFromNBT(itemStack);
        if (fromComponent != null) return VeganStatus.fromBoolean(fromComponent);

        if (itemStack.is(VeganTags.VEGAN)) return VeganStatus.VEGAN;
        if (itemStack.is(VeganTags.NOT_VEGAN)) return VeganStatus.NOT_VEGAN;

        return veganFromRecipes.getOrDefault(itemStack.getItem(), VeganStatus.UNKNOWN);
    }

    public static void setIsVegan(ItemStack stack, boolean isVegan) {
        var isFood = stack.is(VeganTags.SHOULD_HAVE_DATA_COMPONENTS_ADDED) ||
            stack.getUseAnimation() == UseAnim.EAT || stack.getUseAnimation() == UseAnim.DRINK;

        if (!isFood && !VeganNBT.containsSubstitute(stack)) return;

        var currentStatus = isVegan(stack);
        if (VeganNBT.hasVeganNBTTag(stack))
            return;
        if (isVegan && (currentStatus == VeganStatus.VEGAN || currentStatus == VeganStatus.UNKNOWN))
            return;
        if (!isVegan && currentStatus == VeganStatus.NOT_VEGAN)
            return;

        VeganNBT.setVeganInNBT(stack, isVegan);
    }

    private static void scanRecipesRecursively(final Item item, final List<Item> alreadyTraversed) {
        alreadyTraversed.add(item);
        final var recipes = RecipeModification.getRecipesByResult(item);

        var veganRecipes = 0;
        var nonVeganRecipes = 0;

        for (Recipe<?> recipe : recipes) {
            if (recipeNotVegan(alreadyTraversed, recipe)) nonVeganRecipes++;
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
        return !Boolean.FALSE.equals(VeganNBT.getVeganFromNBT(itemStack)) &&
            !itemStack.is(VeganTags.VEGAN_ALTERNATIVE) &&
            VeganNBT.containsSubstitute(itemStack);
    }

    public static void addTooltipLines(ItemStack stack, List<Component> tooltip) {
        if (Boolean.FALSE.equals(VeganNBT.getVeganFromNBT(stack))) {
            tooltip.add(1, Component.literal("Not vegan").setStyle(
                Style.EMPTY
                    .withColor(ChatFormatting.GRAY)
                    .withItalic(true)
            ));
        }

        if (debugMode) {
            tooltip.add(1, Component.literal("Result: " + isVegan(stack).name()));
            tooltip.add(1, Component.literal("Default from recipes: " + veganFromRecipes.get(stack.getItem())));
            tooltip.add(1, Component.literal("Component: " + VeganNBT.getVeganFromNBT(stack)));
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

    private static void modifyRecipeResult(List<ItemStack> recipeInput, ItemStack result) {
        var vegan = true;
        for (var item : recipeInput) {
            if (isVegan(item) == VeganStatus.NOT_VEGAN) {
                vegan = false;
            }
            if (item.is(VeganTags.VEGAN_ALTERNATIVE) || VeganNBT.containsSubstitute(item)) {
                VeganNBT.setContainsSubstitute(result);
            }
        }
        setIsVegan(result, vegan);
    }
}
