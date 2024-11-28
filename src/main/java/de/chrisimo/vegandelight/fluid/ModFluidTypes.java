package de.chrisimo.vegandelight.fluid;

import de.chrisimo.vegandelight.VeganDelight;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, VeganDelight.MODID);

    public static final RegistryObject<FluidType> SOYMILK_FLUID_TYPE = FLUID_TYPES.register(
            "soymilk", RecipeFluidType::createMilky);
    public static final RegistryObject<FluidType> APPLESAUCE_FLUID_TYPE = FLUID_TYPES.register(
            "applesauce", () -> RecipeFluidType.createGloppy().tint(0xF0EBC065));

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
