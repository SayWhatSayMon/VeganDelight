package net.player005.vegandelightfabric;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.ApiStatus;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.Collection;
import java.util.List;

/**
 * Contains a map of which items are used to replace which.
 */
public abstract class ReplacementMap {

    private static final Multimap<Item, Item> map = MultimapBuilder.hashKeys().arrayListValues().build();

    static {
        registerReplacement(VeganItems.LEATHER_SUBSTITUTE, Items.LEATHER);
        registerReplacement(VeganItems.SOYMILK_BUCKET, Items.MILK_BUCKET);
        registerReplacement(VeganItems.SOYMILK_BOTTLE, ModItems.MILK_BOTTLE.get());
        registerReplacement(VeganItems.SILKEN_TOFU, Items.EGG);
        registerReplacement(VeganItems.MINCED_TOFU, ModItems.MINCED_BEEF.get());
        registerReplacement(VeganItems.TOFU_PATTY, ModItems.BEEF_PATTY.get());

        registerReplacement(VeganItems.TOFISH, Items.COD);
        registerReplacement(VeganItems.SMOKED_TOFISH, Items.SALMON);
        registerReplacement(VeganItems.COOKED_TOFISH, Items.COOKED_COD);
        registerReplacement(VeganItems.COOKED_SMOKED_TOFISH, Items.COOKED_SALMON);
        registerReplacement(VeganItems.TOFISH_ROLL, ModItems.COD_ROLL.get());
        registerReplacement(VeganItems.SMOKED_TOFISH_ROLL, ModItems.SALMON_ROLL.get());

        registerReplacement(VeganItems.TOFU_SLICES, Items.MUTTON);
        registerReplacement(VeganItems.TOFU_SLICES, Items.CHICKEN);
        registerReplacement(VeganItems.SMOKED_TOFU_SLICES, Items.PORKCHOP);
        registerReplacement(VeganItems.COOKED_TOFU_SLICES, Items.COOKED_MUTTON);
        registerReplacement(VeganItems.COOKED_TOFU_SLICES, Items.COOKED_CHICKEN);
        registerReplacement(VeganItems.COOKED_SMOKED_TOFU_SLICES, Items.COOKED_PORKCHOP);
    }

    /**
     * Registers the given item as a replacement for the other item, causing a tooltip to show on the replacement item.
     */
    public static void registerReplacement(Item replacement, Item original) {
        map.put(replacement, original);
    }

    /**
     * Returns the items that can be replaced using the given item,
     * empty if the given item doesn't replace anything
     */
    public static Collection<Item> replaces(Item item) {
        return map.get(item);
    }

    private static final Style textStyle = Style.EMPTY
        .withColor(0x555555).withItalic(true);
    private static final Style itemNameStyle = Style.EMPTY
        .withColor(0x999999).withItalic(true);

    @ApiStatus.Internal
    public static void addTooltipLines(Item item, List<Component> tooltip) {
        var replacedItem = replaces(item);
        if (replacedItem.isEmpty()) return;
        var nameList = createItemList(replacedItem);
        tooltip.add(1, Component.translatable("tooltip.vegandelight.replaces",
            nameList.setStyle(itemNameStyle)
        ).setStyle(textStyle));
    }

    private static MutableComponent createItemList(Collection<Item> items) {
        var nameList = Component.empty();
        var iter = items.iterator();
        nameList.append(Component.translatable(iter.next().getDescriptionId()));
        iter.forEachRemaining(it -> nameList.append(", ").append(Component.translatable(it.getDescriptionId())));
        return nameList;
    }
}