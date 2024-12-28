package net.player005.vegandelightfabric.recipe_manipulation;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The main class for recipe modifications, containing some utility methods.
 *
 * @see #registerModifier(IRecipeModifier)
 * @see #removeRecipe(RecipeHolder)
 * @see #forAllRecipes(Consumer)
 */
public abstract class RecipeModification {

    private static final NonNullList<Consumer<RecipeManager>> recipeManagerCallbacks = NonNullList.create();
    private static final NonNullList<Consumer<RecipeHolder<?>>> recipeIterationCallbacks = NonNullList.create();
    private static final Map<RecipeFilter, Consumer<RecipeHolder<?>>> filteredRecipeCallbacks = new HashMap<>();

    private static final NonNullList<ResourceLocation> toRemove = NonNullList.create();
    private static final NonNullList<IRecipeModifier> modifiers = NonNullList.create();

    /**
     * This method can be used to have some code be executed when the server is starting, right before
     * we apply recipe modifiers. It is also an easy way to access the {@link RecipeManager}.
     * <p>
     * The given consumer will be executed on every datapack reload on dedicated servers,
     * or everytime a singleplayer world is loaded on the Client.
     * </p>
     */
    public static void onRecipeInit(Consumer<RecipeManager> consumer) {
        recipeManagerCallbacks.add(consumer);
    }

    /**
     * The given lambda will be called once for EVERY loaded recipe.
     * Using this method is cheaper than looping through all recipes yourself since
     * this library already iterates all recipes anyway.
     */
    public static void forAllRecipes(Consumer<RecipeHolder<?>> recipeConsumer) {
        recipeIterationCallbacks.add(recipeConsumer);
    }

    /**
     * The given lambda will be called once for every loaded recipe matching the given filter.
     * Using this method is cheaper than looping through all recipes yourself since
     * this library already iterates all recipes anyway.
     */
    public static void forAllRecipes(Consumer<RecipeHolder<?>> recipeConsumer, RecipeFilter filter) {
        filteredRecipeCallbacks.put(filter, recipeConsumer);
    }

    /**
     * Removes the given recipe from the game
     *
     * @param recipeHolder The recipe to remove
     */
    public static void removeRecipe(RecipeHolder<?> recipeHolder) {
        toRemove.add(recipeHolder.id());
    }

    /**
     * Removes the given recipe from the game
     *
     * @param id The ResourceLocation of the recipe to remove
     */
    public static void removeRecipe(ResourceLocation id) {
        toRemove.add(id);
    }

    /**
     * Registers a {@link IRecipeModifier} to be applied when loading recipes.
     */
    public static void registerModifier(IRecipeModifier recipeModifier) {
        modifiers.add(recipeModifier);
    }

    /**
     * Internal method that should be called on every datapack reload.
     * Initialises all registered {@link IRecipeModifier}s, calls all {@link #onRecipeInit(Consumer)}
     * callbacks and removes recipes registered for removal using {@link #removeRecipe(RecipeHolder)}
     */
    static void init(RecipeManager recipeManager) {
        for (Consumer<RecipeManager> recipeManagerCallback : recipeManagerCallbacks) {
            recipeManagerCallback.accept(recipeManager);
        }

        for (RecipeHolder<?> recipeHolder : recipeManager.getRecipes()) {
            mainRecipeLoop(recipeHolder, recipeManager);
        }
    }

    // Internal function that's called for all recipes
    private static void mainRecipeLoop(final RecipeHolder<?> recipeHolder,
                                       final RecipeManager recipeManager) {
        var registryAccess = recipeManager.registries;

        // call registered callbacks
        for (Consumer<RecipeHolder<?>> recipeIterationCallback : recipeIterationCallbacks) {
            recipeIterationCallback.accept(recipeHolder);
        }

        for (Map.Entry<RecipeFilter, Consumer<RecipeHolder<?>>> entry : filteredRecipeCallbacks.entrySet()) {
            if (entry.getKey().shouldApply(recipeHolder, registryAccess)) entry.getValue().accept(recipeHolder);
        }

        // apply recipe modifiers
        for (IRecipeModifier modifier : modifiers) {
            if (!modifier.getFilter().shouldApply(recipeHolder, registryAccess)) continue;
            var helper = new ModificationHelper(recipeHolder);
            modifier.apply(recipeHolder.value(), helper);
        }

        for (ResourceLocation id : toRemove) {
            if (recipeHolder.id().equals(id)) {
                // remove recipe from both maps stored in RecipeManager
                recipeManager.getRecipes().remove(recipeHolder); // remove from RecipeManager#byName
                recipeManager.getOrderedRecipes().remove(recipeHolder); // remove from RecipeManager#byType
            }
        }
    }
}
