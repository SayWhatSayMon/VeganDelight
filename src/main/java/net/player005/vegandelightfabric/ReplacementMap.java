package net.player005.vegandelightfabric;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains a map of which items are used to replace which.
 */
public abstract class ReplacementMap {

    private static final Map<Item, Item> map = new HashMap<>();

    static {
        registerReplacement(VeganItems.LEATHER_SUBSTITUTE, Items.LEATHER);
        registerReplacement(VeganItems.SOYMILK_BUCKET, Items.MILK_BUCKET);
        registerReplacement(VeganItems.SOYMILK_BOTTLE, ModItems.MILK_BOTTLE.get());
    }

    /**
     * Registers the given item as a replacement for the other item, causing a tooltip to show on the replacement item.
     */
    public static void registerReplacement(Item replacement, Item original) {
        map.put(replacement, original);
    }

    /**
     * Returns the item that can be replaced using the given item, or null if the given item isn't registered as a
     * replacement
     */
    public static @Nullable Item replaces(Item item) {
        return map.get(item);
    }

    private static final Style textStyle = Style.EMPTY
        .withColor(0x555555).withItalic(true);
    private static final Style itemNameStyle = Style.EMPTY
        .withColor(0x999999).withItalic(true);

    @ApiStatus.Internal
    public static void addTooltipLines(Item item, List<Component> tooltip) {
        var replacedItem = replaces(item);
        if (replacedItem == null) return;
        tooltip.add(1, Component.translatable("tooltip.vegandelight.replaces",
            Component.translatable(replacedItem.getDescriptionId()).setStyle(itemNameStyle)
        ).setStyle(textStyle));
    }
}