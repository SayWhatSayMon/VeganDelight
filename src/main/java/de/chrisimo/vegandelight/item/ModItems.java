package de.chrisimo.vegandelight.item;

import de.chrisimo.vegandelight.VeganDelight;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VeganDelight.MODID);

    public static final RegistryObject<Item> TOFU = ITEMS.register("tofu",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.4f).build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
