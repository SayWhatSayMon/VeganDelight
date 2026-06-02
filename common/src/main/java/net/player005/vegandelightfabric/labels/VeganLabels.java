package net.player005.vegandelightfabric.labels;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.player005.vegandelightfabric.VeganConfig;

import java.util.List;

public class VeganLabels {

    @SuppressWarnings("FieldCanBeLocal")
    private static final boolean debugMode = false;

    public static void addTooltipLines(ItemStack stack, List<Component> tooltip) {
        var isVegan = VeganItemDetection.isVegan(stack);
        switch (isVegan) {
            case NOT_VEGAN -> {
                if (shouldShowNonVeganTooltip(stack)) addNonVeganTooltip(tooltip);
            }
            case VEGAN, UNKNOWN -> {
                if (shouldShowVeganTooltip(stack)) addVeganTooltip(tooltip);
            }
        }

        addDebugTooltip(stack, tooltip);
    }

    private static boolean shouldShowNonVeganTooltip(ItemStack stack) {
        return switch (VeganConfig.notVeganLabelMode) {
            case ALL_NON_VEGAN_FOODS -> VeganDataComponents.isFoodRelated(stack);
            case ALL_NON_VEGAN_ITEMS -> true;
            case ONLY_EXCEPTIONS -> stack.has(VeganDataComponents.is_vegan.value());
            case NONE -> false;
        };
    }

    private static boolean shouldShowVeganTooltip(ItemStack stack) {
        return switch (VeganConfig.veganLabelMode) {
            case ALL_VEGAN_ITEMS -> true;
            case ONLY_WHEN_REPLACEMENT_USED -> stack.has(VeganDataComponents.contains_substitutes.value())
                && !stack.is(VeganTags.VEGAN_ALTERNATIVE);
            case NONE -> false;
        };
    }

    private static void addVeganTooltip(List<Component> tooltip) {
        tooltip.add(1, Component.literal("Vegan").setStyle(
            Style.EMPTY
                .withColor(0x008c44)
                .withItalic(true)
                .withBold(true)
        ));
    }

    private static void addDebugTooltip(ItemStack stack, List<Component> tooltip) {
        if (!debugMode) return;

        tooltip.add(1, Component.literal("Result: " + VeganItemDetection.isVegan(stack).name()));
        tooltip.add(1, Component.literal("Default from recipes: " + VeganItemDetection.veganFromRecipes.get(stack.getItem())));
        tooltip.add(1, Component.literal("Component: " + stack.get(VeganDataComponents.is_vegan.value())));
    }

    private static void addNonVeganTooltip(List<Component> tooltip) {
        tooltip.add(1, Component.literal("Not vegan").setStyle(
            Style.EMPTY
                .withColor(ChatFormatting.GRAY)
                .withItalic(true)
        ));
    }
}
