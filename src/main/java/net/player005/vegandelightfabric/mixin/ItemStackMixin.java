package net.player005.vegandelightfabric.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.player005.vegandelightfabric.ReplacementMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    public void injectTooltips(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir) {
        ReplacementMap.addTooltipLines(getItem(), cir.getReturnValue());
    }
}
