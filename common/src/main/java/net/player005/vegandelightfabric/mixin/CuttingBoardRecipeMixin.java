package net.player005.vegandelightfabric.mixin;

import net.minecraft.util.Unit;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.player005.vegandelightfabric.labels.VeganDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.player005.vegandelightfabric.labels.CuttingBoardContext;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.List;

@Mixin(CuttingBoardRecipe.class)
public class CuttingBoardRecipeMixin {
    @Inject(method = "rollResults", at = @At("RETURN"))
    public void applyVeganToResults(RandomSource random, int luckLevel, CallbackInfoReturnable<List<ItemStack>> cir) {
        Boolean isVegan = CuttingBoardContext.inputIsVegan.get();
        Boolean hasSubstitutes = CuttingBoardContext.inputHasSubstitutes.get();
        if (isVegan == null) return;
        for (ItemStack result : cir.getReturnValue()) {
            if (isVegan) result.set(VeganDataComponents.is_vegan.value(), true);
            if (Boolean.TRUE.equals(hasSubstitutes))
                result.set(VeganDataComponents.contains_substitutes.value(), Unit.INSTANCE);
        }
    }
}
