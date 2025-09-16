package net.player005.vegandelightfabric;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganCreativeTab {

    public static List<ItemLike> getAllItemLike() {
        List<ItemLike> allItemLike = new ArrayList<>();
        Collections.addAll(allItemLike, VeganBlocks.getAllBlockItems());
        Collections.addAll(allItemLike, VeganItems.getAllItems());
        return allItemLike;
    }

    public static final ResourceKey<CreativeModeTab> VEGAN_ITEMS_KEY = ResourceKey.create(
        Registries.CREATIVE_MODE_TAB,
        ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "vegan_ingredients")
    );

    static void initialize() {
        getPlatform().register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            VEGAN_ITEMS_KEY,
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .icon(VeganItems.SMOKED_TOFISH_ROLL.value()::getDefaultInstance)
                .title(Component.translatable("itemGroup.vegan_delight"))
                .displayItems((parameters, output) -> {
                    for (ItemLike item : getAllItemLike()) {
                        output.accept(item);
                    }
                })
                .build()
        );
    }
}
