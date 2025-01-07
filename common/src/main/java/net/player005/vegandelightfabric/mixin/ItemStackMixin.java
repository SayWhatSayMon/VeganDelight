package net.player005.vegandelightfabric.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.player005.vegandelightfabric.labels.LabelUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void injectLabels(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag,
                              CallbackInfoReturnable<List<Component>> cir) {
        LabelUtils.addTooltipLines((ItemStack) (Object) this, cir.getReturnValue());
    }
}
