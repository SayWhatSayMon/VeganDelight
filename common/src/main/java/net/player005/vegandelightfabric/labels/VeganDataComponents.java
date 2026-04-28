package net.player005.vegandelightfabric.labels;

import net.minecraft.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.player005.vegandelightfabric.VeganConfig;
import net.player005.vegandelightfabric.VeganDelightMod;
import net.player005.vegandelightfabric.labels.VeganItemDetection.VeganStatus;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganDataComponents {

    public static Holder<DataComponentType<Boolean>> is_vegan = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(VeganDelightMod.modID, "is_vegan"),
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .build()
    );

    public static final Holder<DataComponentType<Unit>> contains_substitutes = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(VeganDelightMod.modID, "contains_substitutes"),
        () -> DataComponentType.<Unit>builder()
            .persistent(Unit.CODEC)
            .networkSynchronized(Unit.STREAM_CODEC)
            .build()
    );

    public static void initialize() { }

    /**
     * Sets the is_vegan data component of an item stack, if the config allows components for that stack
     * and the stack doesn't have a value yet.
     */
    public static void setIsVegan(ItemStack stack, boolean isVegan) {
        if (!shouldUseVeganComponents(stack)) return;

        var currentStatus = VeganItemDetection.isVegan(stack);
        if (stack.has(is_vegan.value()))
            return;
        if (isVegan && (currentStatus == VeganStatus.VEGAN || currentStatus == VeganStatus.UNKNOWN))
            return;
        if (!isVegan && currentStatus == VeganStatus.NOT_VEGAN)
            return;

        stack.set(is_vegan.value(), isVegan);
    }

    public static boolean shouldUseVeganComponents(ItemStack stack) {
        return switch (VeganConfig.useComponents) {
            case ALL_ITEMS -> true;
            case ONLY_FOODS -> isFoodRelated(stack) || stack.has(contains_substitutes.value());
            case NONE -> false;
        };
    }

    public static boolean isFoodRelated(ItemStack stack) {
        if (stack.getUseAnimation() == ItemUseAnimation.EAT || stack.getUseAnimation() == ItemUseAnimation.DRINK)
            return true;

        return stack.getTags().anyMatch(key -> key.location().toString().startsWith("c:foods"));
    }
}
