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
    public void captureVeganStatus(ItemStack toolStack, Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stored = getStoredItem();
        VeganLabels.VeganStatus isVegan = VeganLabels.isVegan(stored);
        CuttingBoardContext.inputStatus.set(isVegan);
        CuttingBoardContext.inputHasSubstitutes.set(
            stored.has(VeganDataComponents.contains_substitutes.value())
        );
    }

    @Inject(method = "processStoredItemUsingTool", at = @At("RETURN"))
    public void clearVeganStatus(ItemStack toolStack, Player player, CallbackInfoReturnable<Boolean> cir) {
        CuttingBoardContext.inputStatus.remove();
        CuttingBoardContext.inputHasSubstitutes.remove();
    }
}
