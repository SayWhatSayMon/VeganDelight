package net.player005.vegandelightfabric.labels;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.player005.vegandelightfabric.VeganItems;
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
        registerReplacement(VeganItems.LEATHER_SUBSTITUTE.value(), Items.LEATHER);
        registerReplacement(VeganItems.SOYMILK_BUCKET.value(), Items.MILK_BUCKET);
        registerReplacement(VeganItems.SOYMILK_BOTTLE.value(), ModItems.MILK_BOTTLE.get());
        registerReplacement(VeganItems.SILKEN_TOFU.value(), Items.EGG);
        registerReplacement(VeganItems.MINCED_TOFU.value(), ModItems.MINCED_BEEF.get());
        registerReplacement(VeganItems.TOFU_PATTY.value(), ModItems.BEEF_PATTY.get());

        registerReplacement(VeganItems.TOFISH.value(), Items.COD);
        registerReplacement(VeganItems.SMOKED_TOFISH.value(), Items.SALMON);
        registerReplacement(VeganItems.COOKED_TOFISH.value(), Items.COOKED_COD);
        registerReplacement(VeganItems.COOKED_SMOKED_TOFISH.value(), Items.COOKED_SALMON);
        registerReplacement(VeganItems.TOFISH_ROLL.value(), ModItems.COD_ROLL.get());
        registerReplacement(VeganItems.SMOKED_TOFISH_ROLL.value(), ModItems.SALMON_ROLL.get());

        registerReplacement(VeganItems.TOFU_SLICES.value(), Items.MUTTON);
        registerReplacement(VeganItems.TOFU_SLICES.value(), Items.CHICKEN);
        registerReplacement(VeganItems.SMOKED_TOFU_SLICES.value(), Items.PORKCHOP);
        registerReplacement(VeganItems.COOKED_TOFU_SLICES.value(), Items.COOKED_MUTTON);
        registerReplacement(VeganItems.COOKED_TOFU_SLICES.value(), Items.COOKED_CHICKEN);
        registerReplacement(VeganItems.COOKED_SMOKED_TOFU_SLICES.value(), Items.COOKED_PORKCHOP);
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
    public static void addTooltipLines(Item replacement, List<Component> tooltip) {
        var items = replaces(replacement);
        if (items.isEmpty()) return;
        var nameList = createItemList(items);
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
