package net.player005.vegandelightfabric.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.player005.recipe_modification.api.RecipeModification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.refabricated.inventory.RecipeWrapper;

@Mixin(CookingPotRecipe.class)
public class CookingPotRecipeMixin {
    @SuppressWarnings("UnstableApiUsage")
    @Inject(method = "assemble", at = @At("RETURN"), cancellable = true)
    public void onAssemble(RecipeWrapper inv, HolderLookup.Provider provider, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(RecipeModification.getRecipeResult((CookingPotRecipe)(Object)this, cir.getReturnValue(), inv));
    }
}
