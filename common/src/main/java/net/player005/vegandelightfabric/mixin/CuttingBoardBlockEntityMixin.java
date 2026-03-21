package net.player005.vegandelightfabric.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.player005.vegandelightfabric.labels.CuttingBoardContext;
import net.player005.vegandelightfabric.labels.VeganDataComponents;
import net.player005.vegandelightfabric.labels.VeganLabels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(CuttingBoardBlockEntity.class)
public abstract class CuttingBoardBlockEntityMixin {

    @Shadow public abstract ItemStack getStoredItem();

    @Inject(method = "processStoredItemUsingTool", at = @At("HEAD"))
    public void captureVeganStatus(ItemStack tool, Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stored = getStoredItem();
        Boolean isVegan = stored.get(VeganDataComponents.is_vegan.value());
        // contains_substitutes means item was crafted with vegan alternatives → treat as vegan
        if (isVegan == null && stored.has(VeganDataComponents.contains_substitutes.value())) {
            isVegan = true;
        }
        // Fall back to recipe/tag scan for items like apple pie where setIsVegan skips UNKNOWN items
        if (isVegan == null) {
            var status = VeganLabels.isVegan(stored);
            if (status == VeganLabels.VeganStatus.VEGAN) isVegan = true;
        }
        CuttingBoardContext.inputIsVegan.set(isVegan);
        CuttingBoardContext.inputHasSubstitutes.set(
            stored.has(VeganDataComponents.contains_substitutes.value()) ? true : null
        );
    }

    @Inject(method = "processStoredItemUsingTool", at = @At("RETURN"))
    public void clearVeganStatus(ItemStack tool, Player player, CallbackInfoReturnable<Boolean> cir) {
        CuttingBoardContext.inputIsVegan.remove();
        CuttingBoardContext.inputHasSubstitutes.remove();
    }
}
