package net.player005.vegandelightfabric.labels;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.player005.vegandelightfabric.recipe_manipulation.RecipeModification;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LabelUtils {

    /**
     * A map of items to whether they are vegan or not.
     */
    public static final Object2BooleanMap<Item> veganFromRecipes = new Object2BooleanOpenHashMap<>();

    public static VeganStatus isVegan(ItemStack itemStack) {
        var component = itemStack.getComponents().get(VeganDataComponents.vegan);
        if (component != null) return VeganStatus.fromBoolean(component);

        if (itemStack.is(VeganTags.vegan)) return VeganStatus.VEGAN;
        if (itemStack.is(VeganTags.not_vegan)) return VeganStatus.NOT_VEGAN;

        return veganFromRecipes.containsKey(itemStack.getItem()) ?
                VeganStatus.fromBoolean(veganFromRecipes.getBoolean(itemStack.getItem())) :
                VeganStatus.UNKNOWN;
    }

    public static void init(RecipeManager recipeManager) {
        var alreadyTraversed = new ArrayList<Recipe<?>>(recipeManager.getRecipes().size());
        RecipeModification.forAllRecipes(recipeHolder -> scanRecipeRecursively(
                recipeHolder.value().getResultItem(RecipeModification.getRegistryAccess()).getItem(),
                recipeHolder,
                alreadyTraversed)
        );
    }

    @Contract(mutates = "param3")
    private static boolean scanRecipeRecursively(final Item item, final RecipeHolder<?> recipeHolder,
                                                 final List<Recipe<?>> alreadyTraversed) {
        final var recipe = recipeHolder.value();
        alreadyTraversed.add(recipe);

        for (var ingredient : recipe.getIngredients()) {
            for (var itemStack : ingredient.getItems()) {
                var vegan = isVegan(itemStack);

                if (vegan == VeganStatus.UNKNOWN) {
                    for (RecipeHolder<?> otherRecipe : RecipeModification.getRecipesByResult(itemStack.getItem())) {
                        if (!alreadyTraversed.contains(otherRecipe.value()))
                            vegan = scanRecipeRecursively(itemStack.getItem(), otherRecipe, alreadyTraversed) ? VeganStatus.VEGAN : VeganStatus.NOT_VEGAN;
                    }
                }

                if (vegan == VeganStatus.NOT_VEGAN) {
                    veganFromRecipes.put(item, false);
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean shouldRenderTooltip(ItemStack itemStack) {
        return itemStack.has(VeganDataComponents.vegan) && !itemStack.is(VeganTags.vegan);
    }

    public enum VeganStatus {
        VEGAN, NOT_VEGAN, UNKNOWN;

        @Nullable
        public Boolean toOptionalBoolean() {
            return switch (this) {
                case VEGAN -> true;
                case NOT_VEGAN -> false;
                case UNKNOWN -> null;
            };
        }

        public static VeganStatus fromBoolean(@Nullable Boolean bool) {
            if (bool == null) return UNKNOWN;
            if (bool) return VEGAN;
            else return NOT_VEGAN;
        }
    }

    @ApiStatus.Internal
    public static void modifyRecipeResult(Recipe<?> recipe, ItemStack result) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            for (ItemStack item : ingredient.getItems()) {
                if (isVegan(item) == VeganStatus.NOT_VEGAN) {
                    result.applyComponents(VeganDataComponents.setIsNotVegan);
                    return;
                }
            }
        }
        result.applyComponents(VeganDataComponents.setIsVegan);
    }

    @ApiStatus.Internal
    public static void modifyRecipeResult(RecipeInput recipeInput, ItemStack result) {
        for (var i = 0; i < recipeInput.size(); i++) {
            var item = recipeInput.getItem(i);
            if (isVegan(item) == VeganStatus.NOT_VEGAN) {
                System.out.println(item);
                result.applyComponents(VeganDataComponents.setIsNotVegan);
                return;
            }
        }
        result.applyComponents(VeganDataComponents.setIsVegan);
    }
}