package de.chrisimo.vegandelight.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final CreativeModeTab VEGAN_TAB = new CreativeModeTab("vegantab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SMOKED_TOFISH_ROLL.get());
        }
    };
}
