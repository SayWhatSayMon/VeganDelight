package de.chrisimo.vegandelight.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.player005.vegandelightfabric.VeganConfig;
import net.player005.vegandelightfabric.labels.VeganNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @SuppressWarnings("ConstantValue")
    @Inject(method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"))
    private void markEntityDroppedItemsAsNotVegan(ItemStack stack, CallbackInfoReturnable<ItemEntity> cir) {
        if (!VeganConfig.markEntityDroppedItemsAsNonVegan) return;
        if ((Object) this instanceof Player) return;
        VeganNBT.setIsVegan(stack, false);
    }
}
