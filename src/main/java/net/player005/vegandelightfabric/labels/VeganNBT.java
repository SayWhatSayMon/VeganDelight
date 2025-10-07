package net.player005.vegandelightfabric.labels;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

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
}
