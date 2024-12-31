package net.player005.vegandelightfabric.recipe_manipulation;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class for modifying recipes easily.
 *
 * @see RecipeModification#registerModifier(RecipeModifier)
 * @see RecipeModifier
 */
public class ModificationHelper {

    private final RecipeHolder<?> recipeHolder;

    /**
     * Constructs a new recipe modification helper.
     */
    public ModificationHelper(RecipeHolder<?> recipe) {
        this.recipeHolder = recipe;
    }

    /**
     * Tries to remove the given ingredient from the recipe.
     * Doesn't work with all recipe types (like cooking/smelting and stonecutting)
     */
    public void removeIngredient(Ingredient ingredient) {
        recipeHolder.value().getIngredients().remove(ingredient);
    }

    /**
     * Tries to add another ingredient to the recipe.
     * Doesn't work with all recipe types (like cooking/smelting and stonecutting)
     */
    public void addIngredient(Ingredient ingredient) {
        recipeHolder.value().getIngredients().add(ingredient);
    }

    /**
     * Add an alternative to matching items.
     *
     * @param original    the item that can be substituted
     * @param alternative the substitute
     */
    public void addAlternative(Item original, Ingredient.Value alternative) {
        for (Ingredient ingredient : recipeHolder.value().getIngredients()) {
            for (ItemStack item : ingredient.getItems()) {
                if (item.is(original)) addIngredientValue(ingredient, alternative);
            }
        }
    }

    /**
     * Add an alternative to matching items.
     *
     * @param original    the item that can be substituted
     * @param alternative the substitute
     */
    public void addAlternative(Item original, Item alternative) {
        addAlternative(original, new Ingredient.ItemValue(alternative.getDefaultInstance()));
    }

    /**
     * Add an alternative to matching items.
     *
     * @param original    the item that can be substituted
     * @param alternative the substitute
     */
    public void addAlternative(Item original, TagKey<Item> alternative) {
        addAlternative(original, new Ingredient.TagValue(alternative));
    }

    /**
     * Completely replaces the {@link Ingredient#values} of the ingredient with the given one
     * (effectively replacing the entire ingredient)
     */
    public void replaceIngredientValues(Ingredient ingredient, Ingredient.Value[] ingredientValues) {
        if (ingredient.values == ingredientValues) return;
        ingredient.values = ingredientValues;
        updateIngredientValues(ingredient);
    }

    /**
     * Replaces the given old ingredient with another ingredient.
     * Under the hood, this just calls {@link #replaceIngredientValues(Ingredient, Ingredient.Value[])}
     * to copy the data from the new ingredient to the existing one.
     */
    public void replaceIngredient(Ingredient old, Ingredient neew) {
        replaceIngredientValues(old, neew.values);
    }

    /**
     * Removes a given alternative from the ingredient so that it can't be used for the ingredient/recipe anymore.
     * If the given value is the only one in the Ingredient, the recipe might become impossible to make.
     */
    public void removeIngredientValue(Ingredient ingredient, Ingredient.Value toRemove) {
        var values = new ArrayList<>(List.of(ingredient.values));
        values.remove(toRemove);
        //noinspection DataFlowIssue TODO: confirm this works
        replaceIngredientValues(ingredient, (Ingredient.Value[]) values.toArray());
    }

    /**
     * Adds a {@link Ingredient.Value} to the given ingredient, providing an
     * alternative item to use for the ingredient/recipe.
     */
    public void addIngredientValue(Ingredient ingredient, Ingredient.Value addedValue) {
        if (ArrayUtils.contains(ingredient.values, addedValue)) return;
        var values = Arrays.copyOf(ingredient.values, ingredient.values.length + 1);
        values[values.length - 1] = addedValue;
        replaceIngredientValues(ingredient, values);
    }

    /**
     * Resets some cached values from vanilla Ingredients.
     * Call this after modifying {@link Ingredient#values}.
     */
    private void updateIngredientValues(Ingredient ingredient) {
        ingredient.stackingIds = null;
        // idea complains about this for some reason
        // noinspection DataFlowIssue
        ingredient.itemStacks = null;
    }

    public RecipeHolder<?> getRecipeHolder() {
        return recipeHolder;
    }
}
