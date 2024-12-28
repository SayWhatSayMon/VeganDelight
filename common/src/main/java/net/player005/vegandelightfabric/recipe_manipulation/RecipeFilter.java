package net.player005.vegandelightfabric.recipe_manipulation;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

/**
 * A simple functional interface to filter recipes.
 * There are some simple filters listed below, but you can easily implement this on your own
 *
 * @see #ALWAYS_APPLY
 * @see #resultItemIs(Item)
 * @see #acceptsIngredient(ItemStack)
 * @see #or(RecipeFilter...)
 * @see #and(RecipeFilter...)
 */
public interface RecipeFilter {

    /**
     * The main (and only) method of a RecipeFilter
     *
     * @param recipe         the given recipe to test
     * @param registryAccess
     * @return if the test was successful
     */
    boolean shouldApply(RecipeHolder<?> recipe, HolderLookup.Provider registryAccess);

    /**
     * A simple recipe filter that always returns {@code true}.
     */
    RecipeFilter ALWAYS_APPLY = (recipe, registryAccess) -> true;

    /**
     * Returns a recipe filter that filters for recipes that use the given ItemStack as an ingredient
     */
    static RecipeFilter acceptsIngredient(ItemStack item) {
        return (recipe, registryAccess) -> {
            for (var ingredient : recipe.value().getIngredients())
                if (ingredient.test(item)) return true;
            return false;
        };
    }

    /**
     * Returns a recipe filter that filters for recipes that create the given result item.
     */
    static RecipeFilter resultItemIs(Item item) { // TODO registry access
        return (recipe, registryAccess) -> recipe.value().getResultItem(registryAccess).is(item);
    }

    /**
     * Returns a recipe filter that filters for recipes that create a result item contained in the given tag.
     */
    static RecipeFilter resultItemIs(TagKey<Item> itemTag) { // TODO registry access
        return (recipe, registryAccess) -> recipe.value().getResultItem(registryAccess).is(itemTag);
    }

    /**
     * Concatenates multiple given filters with a logical and.
     */
    static RecipeFilter and(RecipeFilter... filters) {
        return (recipe, registryAccess) -> {
            for (var filter : filters) if (!filter.shouldApply(recipe, registryAccess)) return false;
            return true;
        };
    }

    /**
     * Concatenates multiple given filters with a logical or.
     */
    static RecipeFilter or(RecipeFilter... filters) {
        return (recipe, registryAccess) -> {
            for (var filter : filters) if (filter.shouldApply(recipe, registryAccess)) return true;
            return false;
        };
    }
}
