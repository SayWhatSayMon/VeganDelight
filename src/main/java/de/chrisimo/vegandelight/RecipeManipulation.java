package de.chrisimo.vegandelight;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RecipeManipulation {

    private static final Logger logger = LoggerFactory.getLogger("Vegan Delight/recipe modifiers");
    private static final Map<Item, Ingredient.Value> registeredSubstitutes = new HashMap<>();

    static void load(@NotNull RecipeManager recipeManager) {
        var allRecipes = recipeManager.getRecipes();

        logger.info("Scanning {} recipes for modification", allRecipes.size());
        AtomicInteger ingredientsChanged = new AtomicInteger();

        for (Recipe<?> recipe : allRecipes) {
            NonNullList<Ingredient> ingredients = recipe.getIngredients();

            for (Ingredient ingredient : ingredients) {
                registeredSubstitutes.forEach((item, substitute) -> {
                    var wasChanged = modifyIngredient(ingredient, item, substitute);
                    if (wasChanged) ingredientsChanged.getAndIncrement();
                });
            }
        }

        logger.info("Modified {} recipe ingredients", ingredientsChanged);
    }

    /**
     * checks if a substitute should be registered for an ingredient.
     * returns true if the ingredient was changed
     */
    private static boolean modifyIngredient(@NotNull Ingredient ingredient, Item item, Ingredient.Value substitute) {
        for (ItemStack stack : ingredient.getItems()) {
            if (stack.is(item) && !isSubstituteAlreadyUsable(ingredient, substitute)) {
                addSubstitute(ingredient, substitute);
                return true;
            }
        }
        return false;
    }

    /**
     * returns if the substitute can already be used for an ingredient
     */
    private static boolean isSubstituteAlreadyUsable(Ingredient ingredient, @NotNull Ingredient.Value substitute) {
        for (ItemStack item : substitute.getItems()) {
            if (ingredient.test(item)) return true;
        }
        return false;
    }

    private static void addSubstitute(@NotNull Ingredient ingredient, Ingredient.Value substitute) {
        ingredient.values = Arrays.copyOf(ingredient.values, ingredient.values.length + 1);
        ingredient.values[ingredient.values.length - 1] = substitute;

        // set minecraft's cached values to null to recalculate the values
        ingredient.stackingIds = null;
        ingredient.itemStacks = null;
    }

    public static void registerSubstitute(Item item, Ingredient.Value substitute) {
        registeredSubstitutes.put(item, substitute);
    }

    public static void registerSubstitute(Item item, Item substitute) {
        registerSubstitute(item, new Ingredient.ItemValue(new ItemStack(substitute)));
    }

}
