package net.player005.vegandelightfabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class VeganCreativeTab {
    @SuppressWarnings("DataFlowIssue")
    public static final ResourceKey<CreativeModeTab> VEGAN_ITEMS_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.tryBuild(VeganDelightMod.modID, "vegan_ingredients")
    );
    public static final CreativeModeTab VEGAN_ITEM_TAB = VeganDelightMod.platform
            .registerItemTab(new ItemStack(VeganItems.SMOKED_TOFISH_ROLL), Component.translatable("itemGroup.vegan_delight"), VeganItems.all);

    public static void initialise() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VEGAN_ITEMS_KEY, VEGAN_ITEM_TAB);
    }
}
