package net.player005.vegandelightfabric.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.player005.vegandelightfabric.labels.LabelUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin implements FeatureElement, ItemLike {

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void addVeganLabels(ItemStack stack, Item.TooltipContext context, @NotNull List<Component> tooltipComponents,
                               TooltipFlag tooltipFlag, CallbackInfo ci) {
        tooltipComponents.add(
                Component.literal(LabelUtils.isVegan(stack).name()).setStyle(Style.EMPTY
                        .withColor(0x008c44)
                        .withItalic(true)
                        .withBold(true)
                )
        );
        tooltipComponents.add( // TODO: remove
                Component.literal("Show Label: " + LabelUtils.shouldRenderTooltip(stack)).setStyle(Style.EMPTY
                        .withColor(0x008c44)
                )
        );
    }
}
