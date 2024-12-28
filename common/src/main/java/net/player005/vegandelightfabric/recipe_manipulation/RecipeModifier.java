package net.player005.vegandelightfabric.recipe_manipulation;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RecipeModifier implements IRecipeModifier {

    private final @Nullable ResourceLocation id;
    private final RecipeFilter filter;

    protected RecipeModifier(RecipeFilter filter, @Nullable ResourceLocation id) {
        this.id = id;
        this.filter = filter;
    }

    protected RecipeModifier(RecipeFilter filter) {
        this.id = null;
        this.filter = filter;
    }

    @Override
    public @NotNull RecipeFilter getFilter() {
        return filter;
    }

    @Override
    public @Nullable ResourceLocation id() {
        return id;
    }
}
