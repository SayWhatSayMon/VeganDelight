package net.player005.vegandelightfabric.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.player005.vegandelightfabric.labels.LabelUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
        AbstractCookingRecipe.class,
        FireworkRocketRecipe.class, FireworkStarRecipe.class,
        ShapedRecipe.class, ShapelessRecipe.class,
        SingleItemRecipe.class,
        SmithingTransformRecipe.class, SmithingTrimRecipe.class,
})
public abstract class RecipeMixin {

    @Inject(method = "getResultItem", at = @At("RETURN"))
    public void resultItemPreview(CallbackInfoReturnable<ItemStack> cir) {
        LabelUtils.modifyRecipeResult((Recipe<?>) this, cir.getReturnValue());
    }
}
