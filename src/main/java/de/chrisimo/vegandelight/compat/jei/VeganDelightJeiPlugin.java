package de.chrisimo.vegandelight.compat.jei;

import de.chrisimo.vegandelight.VeganDelight;
import de.chrisimo.vegandelight.fluid.ModFluids;
import de.chrisimo.vegandelight.fluid.RecipeFluid;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * This class removes recipe-only items/fluids from the JEI interface.
 * It will only be found/executed when JEI is active by virtual of its @JeiPlugin annotation.
 *
 * @see <a href="https://github.com/mezz/JustEnoughItems/wiki/Creating-Plugins-%5B1.13-and-Up%5D"></a>
 */
@JeiPlugin
public class VeganDelightJeiPlugin implements IModPlugin {
    /**
     * Gets a unique ID for this plugin and identifies a location for custom JEI resources, if any.
     *
     * @return A unique ID for our little JEI plugin.
     */
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(VeganDelight.MODID, "jei");
    }

    /**
     * Removes recipe-only items/fluids from the JEI interface.
     *
     * @param registration JEI's recipe registration.
     */
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var stacks = RecipeFluid.RECIPE_FLUIDS.stream().map(f -> new FluidStack(f, 1000)).toList();
        registration.getIngredientManager().removeIngredientsAtRuntime(ForgeTypes.FLUID_STACK, stacks);
    }
}
