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
                    for (ItemStack stack : ingredient.getItems()) {
                        if (stack.is(item) && !substitute.getItems().contains(item.getDefaultInstance())) {
                            addSubstitute(ingredient, substitute);
                            ingredientsChanged.getAndIncrement();
                        }
                    }
                });
            }
        }

        logger.info("Modified {} recipe ingredients", ingredientsChanged);
    }

    private static void addSubstitute(@NotNull Ingredient ingredient, Ingredient.Value substitute) {
        ingredient.values = Arrays.copyOf(ingredient.values, ingredient.values.length + 1);
        ingredient.values[ingredient.values.length - 1] = substitute;

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
