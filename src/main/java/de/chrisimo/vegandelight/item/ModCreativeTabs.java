package de.chrisimo.vegandelight.item;

import de.chrisimo.vegandelight.VeganDelight;
import de.chrisimo.vegandelight.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VeganDelight.MODID);

    public static final RegistryObject<CreativeModeTab> VEGAN_TAB = CREATIVE_MODE_TABS.register("vegan_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SMOKED_TOFISH_ROLL.get()))
                    .title(Component.translatable("creativetab.vegan_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.TOFU.get());
                        pOutput.accept(ModItems.TOFU_SLICES.get());
                        pOutput.accept(ModItems.COOKED_TOFU_SLICES.get());
                        pOutput.accept(ModItems.SMOKED_TOFU.get());
                        pOutput.accept(ModItems.SMOKED_TOFU_SLICES.get());
                        pOutput.accept(ModItems.COOKED_SMOKED_TOFU_SLICES.get());
                        pOutput.accept(ModItems.MINCED_TOFU.get());
                        pOutput.accept(ModItems.TOFU_PATTY.get());
                        pOutput.accept(ModItems.TOFISH.get());
                        pOutput.accept(ModItems.COOKED_TOFISH.get());
                        pOutput.accept(ModItems.SMOKED_TOFISH.get());
                        pOutput.accept(ModItems.COOKED_SMOKED_TOFISH.get());
                        pOutput.accept(ModItems.TOFISH_ROLL.get());
                        pOutput.accept(ModItems.SMOKED_TOFISH_ROLL.get());
                        pOutput.accept(ModItems.SOYMILK_BUCKET.get());
                        pOutput.accept(ModItems.SOYMILK_BOTTLE.get());
                        pOutput.accept(ModItems.SOYBEAN.get());
                        pOutput.accept(ModItems.SALT.get());
                        pOutput.accept(ModBlocks.SOYBEAN_BAG.get());
                        pOutput.accept(ModBlocks.WILD_SOYBEAN.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
