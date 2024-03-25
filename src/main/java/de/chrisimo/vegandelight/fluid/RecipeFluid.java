package de.chrisimo.vegandelight.fluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

/**
 * A fluid that offers very little in the way of customization because it is meant to
 * be used as a recipe-only fluid (ie. one without a bucket or block).
 */
public class RecipeFluid extends ForgeFlowingFluid {

    // Here we're just collecting all the recipe-only fluids for any batch handling.
    public static final Collection<Fluid> RECIPE_FLUIDS = new HashSet<>();

    /**
     * Create a new recipe-only fluid with the supplied properties.
     * @param properties Properties for this fluid.
     * @return           A new recipe-only fluid.
     */
    public static RecipeFluid create(Properties properties) {
        var newFluid = new RecipeFluid(properties);
        RECIPE_FLUIDS.add(newFluid.getSource());
        return newFluid;
    }

    /**
     * Creates a new recipe-only fluid with the supplied properties.
     * @param properties Properties for this fluid.
     */
    public RecipeFluid(Properties properties) {
        super(properties);
    }

    // Below are some overrides that keep this fluid from being placed in the world.

    @Override
    public Fluid getFlowing() {
        return this;
    }

    @Override
    public boolean isSource(@NotNull FluidState state) {
        return false;
    }

    @Override
    public int getAmount(@NotNull FluidState state) {
        return 0;
    }
}
