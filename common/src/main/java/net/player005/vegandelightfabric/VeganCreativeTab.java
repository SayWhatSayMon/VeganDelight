package net.player005.vegandelightfabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VeganCreativeTab {
    public static final List<ItemLike> allItemLike = new ArrayList<>();

    static {
        Collections.addAll(allItemLike, VeganBlocks.allBlocks);
        Collections.addAll(allItemLike, VeganItems.allItems);
    }

    public static final ResourceKey<CreativeModeTab> VEGAN_ITEMS_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "vegan_ingredients")
    );

    public static void register() {
        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                VEGAN_ITEMS_KEY,
                CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                        .icon(VeganItems.SMOKED_TOFISH_ROLL::getDefaultInstance)
                        .title(Component.translatable("itemGroup.vegan_delight"))
                        .displayItems((parameters, output) -> {
                            for (ItemLike item : allItemLike) {
                                output.accept(item);
                            }
                        })
                        .build()
        );
    }
}
