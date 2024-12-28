package net.player005.vegandelightfabric.recipe_manipulation;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A recipe modifier.
 * To use this, implement and call {@link RecipeModification#registerModifier(IRecipeModifier)}.
 */
public interface IRecipeModifier {
    /**
     * An optional identifier of this modifier.
     */
    default @Nullable ResourceLocation id() {
        return null;
    }

    /**
     * The filter to manage which recipes should be modified by this modifier.
     * Either implement your own filter or check the static members of the {@link RecipeFilter} class.
     */
    @NotNull RecipeFilter getFilter();

    /**
     * The main method to implement, which modifies the given recipe by using the methods of the given
     * {@link ModificationHelper}
     */
    void apply(Recipe<?> recipe, ModificationHelper batch);
}
