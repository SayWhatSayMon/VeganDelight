package net.player005.vegandelightfabric.labels;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.player005.vegandelightfabric.VeganConfig;
import net.player005.vegandelightfabric.labels.VeganItemDetection.VeganStatus;

public abstract class VeganNBT {

    public static final String IS_VEGAN_TAG = "vegandelight:is_vegan";
    public static final String CONTAINS_SUBSTITUTE_TAG = "vegandelight:contains_substitute";

    public static boolean hasVeganNBTTag(ItemStack stack) {
        if (stack.getTag() == null) return false;
        return stack.getTag().contains(IS_VEGAN_TAG, Tag.TAG_BYTE);
    }

    public static void setVeganInNBT(ItemStack stack, boolean isVegan) {
        stack.getOrCreateTag().put(IS_VEGAN_TAG, ByteTag.valueOf(isVegan));
    }

    public static Boolean getVeganFromNBT(ItemStack stack) {
        if (stack.getTag() == null) return null;
        var tag = stack.getTag().get(IS_VEGAN_TAG);
        if (tag == null) return null;
        return tag.equals(ByteTag.ONE);
    }

    public static void setContainsSubstitute(ItemStack stack) {
        stack.addTagElement(CONTAINS_SUBSTITUTE_TAG, new CompoundTag());
    }

    public static boolean containsSubstitute(ItemStack stack) {
        if (stack.getTag() == null) return false;
        var tag = stack.getTag().get(CONTAINS_SUBSTITUTE_TAG);
        return tag != null;
    }


    /**
     * Sets the is_vegan data component of an item stack, if the config allows components for that stack
     * and the stack doesn't have a value yet.
     */
    public static void setIsVegan(ItemStack stack, boolean isVegan) {
        if (!shouldUseVeganComponents(stack)) return;

        var currentStatus = VeganItemDetection.isVegan(stack);
        if (hasVeganNBTTag(stack))
            return;
        if (isVegan && (currentStatus == VeganStatus.VEGAN || currentStatus == VeganStatus.UNKNOWN))
            return;
        if (!isVegan && currentStatus == VeganStatus.NOT_VEGAN)
            return;

        setVeganInNBT(stack, isVegan);
    }

    public static boolean shouldUseVeganComponents(ItemStack stack) {
        return switch (VeganConfig.useComponents) {
            case ONLY_FOODS -> isFoodRelated(stack) || containsSubstitute(stack);
            case ALL_ITEMS -> true;
            case NONE -> false;
        };
    }

    public static boolean isFoodRelated(ItemStack stack) {
        if (stack.getUseAnimation() == UseAnim.EAT || stack.getUseAnimation() == UseAnim.DRINK)
            return true;

        return stack.getTags().anyMatch(key -> key.location().toString().startsWith("c:foods"));
    }
}
