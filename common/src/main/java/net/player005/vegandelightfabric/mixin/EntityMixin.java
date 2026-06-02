package net.player005.vegandelightfabric.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.player005.vegandelightfabric.labels.VeganDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @SuppressWarnings("ConstantValue")
    @Inject(method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"))
    private void markEntityDroppedItemsAsNotVegan(ItemStack stack, float offsetY, CallbackInfoReturnable<ItemEntity> cir) {
        if (!((Object) this instanceof Player))
            VeganDataComponents.setIsVegan(stack, false);
    }
}
