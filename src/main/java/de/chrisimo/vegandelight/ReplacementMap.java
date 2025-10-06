package de.chrisimo.vegandelight;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import de.chrisimo.vegandelight.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.List;

/**
 * Contains a map of which items are used to replace which.
 */
public abstract class ReplacementMap {

    private static final Multimap<Item, Item> map = MultimapBuilder.hashKeys().arrayListValues().build();

    static {
        registerReplacement(ModItems.LEATHER_SUBSTITUTE.get(), Items.LEATHER);
        registerReplacement(ModItems.SOYMILK_BUCKET.get(), Items.MILK_BUCKET);
        registerReplacement(ModItems.SOYMILK_BOTTLE.get(), vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get());
        registerReplacement(ModItems.SILKEN_TOFU.get(), Items.EGG);
        registerReplacement(ModItems.MINCED_TOFU.get(),
            vectorwing.farmersdelight.common.registry.ModItems.MINCED_BEEF.get());
        registerReplacement(ModItems.TOFU_PATTY.get(), vectorwing.farmersdelight.common.registry.ModItems.BEEF_PATTY.get());

        registerReplacement(ModItems.TOFISH.get(), Items.COD);
        registerReplacement(ModItems.SMOKED_TOFISH.get(), Items.SALMON);
        registerReplacement(ModItems.COOKED_TOFISH.get(), Items.COOKED_COD);
        registerReplacement(ModItems.COOKED_SMOKED_TOFISH.get(), Items.COOKED_SALMON);
        registerReplacement(ModItems.TOFISH_ROLL.get(), vectorwing.farmersdelight.common.registry.ModItems.COD_ROLL.get());
        registerReplacement(ModItems.SMOKED_TOFISH_ROLL.get(),
            vectorwing.farmersdelight.common.registry.ModItems.SALMON_ROLL.get());

        registerReplacement(ModItems.TOFU_SLICES.get(), Items.MUTTON);
        registerReplacement(ModItems.TOFU_SLICES.get(), Items.CHICKEN);
        registerReplacement(ModItems.SMOKED_TOFU_SLICES.get(), Items.PORKCHOP);
        registerReplacement(ModItems.COOKED_TOFU_SLICES.get(), Items.COOKED_MUTTON);
        registerReplacement(ModItems.COOKED_TOFU_SLICES.get(), Items.COOKED_CHICKEN);
        registerReplacement(ModItems.COOKED_SMOKED_TOFU_SLICES.get(), Items.COOKED_PORKCHOP);
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