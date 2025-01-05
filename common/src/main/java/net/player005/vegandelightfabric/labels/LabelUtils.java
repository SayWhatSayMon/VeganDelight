package net.player005.vegandelightfabric.labels;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.player005.vegandelightfabric.recipe_manipulation.RecipeModification;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelUtils {

    public static final Map<Item, VeganStatus> veganFromRecipes = new HashMap<>();

    public static VeganStatus isVegan(ItemStack itemStack) {
        var component = itemStack.getComponents().get(VeganDataComponents.vegan);
        if (component != null) return VeganStatus.fromBoolean(component);

        if (itemStack.is(VeganTags.vegan)) return VeganStatus.VEGAN;
        if (itemStack.is(VeganTags.not_vegan)) return VeganStatus.NOT_VEGAN;

        return veganFromRecipes.getOrDefault(itemStack.getItem(), VeganStatus.UNKNOWN);
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
    private static VeganStatus scanRecipeRecursively(final Item item, final RecipeHolder<?> recipeHolder,
                                                     final List<Recipe<?>> alreadyTraversed) {
        final var recipe = recipeHolder.value();
        alreadyTraversed.add(recipe);

        var hadNonVeganIngredients = false;
        var hadUnknownIngredients = false;
        var hadVeganIngredients = false;

        for (var ingredient : recipe.getIngredients()) {
            for (var itemStack : ingredient.getItems()) {
                var vegan = isVegan(itemStack);

                if (vegan == VeganStatus.UNKNOWN) {
                    for (RecipeHolder<?> otherRecipe : RecipeModification.getRecipesByResult(itemStack.getItem())) {
                        if (!alreadyTraversed.contains(otherRecipe.value()))
                            vegan = scanRecipeRecursively(itemStack.getItem(), otherRecipe, alreadyTraversed);
                    }
                }

                switch (vegan) {
                    case NOT_VEGAN -> hadNonVeganIngredients = true;
                    case UNKNOWN -> hadUnknownIngredients = true;
                    case VEGAN -> hadVeganIngredients = true;
                }
            }
        }

        var status = VeganStatus.UNKNOWN;
        if (hadVeganIngredients && !hadNonVeganIngredients && !hadUnknownIngredients)
            status = VeganStatus.VEGAN;
        if (hadNonVeganIngredients)
            status = VeganStatus.NOT_VEGAN;

        veganFromRecipes.put(item, status);
        return status;
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
