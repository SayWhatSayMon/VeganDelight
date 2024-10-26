package net.player005.vegandelightfabric;

import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RecipeManipulation {
    private static final Map<Item, Ingredient.Value> registeredSubstitutes = new HashMap<>();

    static void load(RecipeManager recipeManager) {
        var allRecipes = recipeManager.getRecipes();

        VeganDelightMod.logger.info("Scanning {} recipes for modification", allRecipes.size());
        AtomicInteger ingredientsChanged = new AtomicInteger();

        for (RecipeHolder<?> holder : allRecipes) {
            NonNullList<Ingredient> ingredients = holder.value().getIngredients();

            for (Ingredient ingredient : ingredients) {
                registeredSubstitutes.forEach((item, substitute) -> {
                    for (ItemStack stack : ingredient.getItems()) {
                        if (stack.is(item)) {
                            addSubstitute(ingredient, substitute);
                            ingredientsChanged.getAndIncrement();
                        }
                    }
                });
            }
        }

        VeganDelightMod.logger.info("Modified {} recipe ingredients", ingredientsChanged);
    }

    private static void addSubstitute(Ingredient ingredient, Ingredient.Value substitute) {
        ingredient.values = Arrays.copyOf(ingredient.values, ingredient.values.length + 1);
        ingredient.values[ingredient.values.length - 1] = substitute;

        ingredient.stackingIds = null;
        // idea complains about this for some reason
        //noinspection DataFlowIssue
        ingredient.itemStacks = null;
    }

    public static void registerSubstitute(Item item, Ingredient.Value substitute) {
        registeredSubstitutes.put(item, substitute);
    }

    public static void registerSubstitute(Item item, Item substitute) {
        registerSubstitute(item, new Ingredient.ItemValue(new ItemStack(substitute)));
    }

    public static void registerSubstitute(Item item, TagKey<Item> substitute) {
        registerSubstitute(item, new Ingredient.TagValue(substitute));
    }
}
