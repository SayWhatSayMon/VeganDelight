package net.player005.vegandelightfabric.mixin;

import net.minecraft.util.Unit;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.player005.vegandelightfabric.labels.VeganDataComponents;
import net.player005.vegandelightfabric.labels.VeganLabels;
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
    public void applyVeganToResults(RandomSource rand, int fortuneLevel, CallbackInfoReturnable<List<ItemStack>> cir) {
        VeganLabels.VeganStatus inputStatus = CuttingBoardContext.inputStatus.get();
        Boolean hasSubstitutes = CuttingBoardContext.inputHasSubstitutes.get();
        if (inputStatus == VeganLabels.VeganStatus.NOT_VEGAN) return;
        for (ItemStack result : cir.getReturnValue()) {
            if (inputStatus == VeganLabels.VeganStatus.VEGAN) result.set(VeganDataComponents.is_vegan.value(), true);
            if (hasSubstitutes) result.set(VeganDataComponents.contains_substitutes.value(), Unit.INSTANCE);
        }
    }
}
