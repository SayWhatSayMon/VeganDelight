package net.player005.vegandelightfabric.labels;

import com.google.common.base.Stopwatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.player005.recipe_modification.api.RecipeModification;
import net.player005.vegandelightfabric.VeganConfig;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeganItemDetection {

    static final Map<Item, VeganStatus> veganFromRecipes = new HashMap<>();

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
        var fromComponent = VeganNBT.getVeganFromNBT(itemStack);
        if (fromComponent != null) return VeganStatus.fromBoolean(fromComponent);

        if (itemStack.is(VeganTags.VEGAN)) return VeganStatus.VEGAN;
        if (itemStack.is(VeganTags.NOT_VEGAN)) return VeganStatus.NOT_VEGAN;

        return veganFromRecipes.getOrDefault(itemStack.getItem(), VeganStatus.UNKNOWN);
    }

    private static void scanRecipesRecursively(final Item item, final List<Item> alreadyTraversed) {
        alreadyTraversed.add(item);
        final var recipes = RecipeModification.getRecipesByResult(item);

        var veganRecipes = 0;
        var nonVeganRecipes = 0;

        for (Recipe<?> recipe : recipes) {
            if (isRecipeVegan(alreadyTraversed, recipe)) veganRecipes++;
            else nonVeganRecipes++;
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

    private static boolean isRecipeVegan(List<Item> alreadyTraversed, Recipe<?> recipe) {
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
                return false;
        }
        return true;
    }

    private static void modifyRecipeResult(Container recipeInput, ItemStack result) {
        var vegan = true;
        for (var i = 0; i < recipeInput.getContainerSize(); i++) {
            var stack = recipeInput.getItem(i);
            if (isVegan(stack) == VeganStatus.NOT_VEGAN) {
                vegan = false;
            }
            if (stack.is(VeganTags.VEGAN_ALTERNATIVE) || VeganNBT.containsSubstitute(stack)) {
                if (VeganConfig.alwaysUseComponentsWhenSubstitutesIncluded || VeganNBT.shouldUseVeganComponents(stack))
                    VeganNBT.setContainsSubstitute(result);
            }
        }
        VeganNBT.setIsVegan(result, vegan);
    }

    public enum VeganStatus {
        VEGAN, NOT_VEGAN, UNKNOWN;

        static VeganStatus fromBoolean(@Nullable Boolean b) {
            if (b == null) return UNKNOWN;
            return b ? VEGAN : NOT_VEGAN;
        }
    }
}