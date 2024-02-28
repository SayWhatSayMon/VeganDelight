package de.chrisimo.vegandelight.fluid;

import de.chrisimo.vegandelight.VeganDelight;
import de.chrisimo.vegandelight.item.ModItems;
import de.chrisimo.vegandelight.block.ModBlocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, VeganDelight.MODID);

    public static final RegistryObject<FlowingFluid> SOYMILK_FLUID = FLUIDS.register("soymilk_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MILK_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SOYMILK_FLUID = FLUIDS.register("flowing_soymilk_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MILK_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MILK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.SOYMILK_FLUID_TYPE, SOYMILK_FLUID, FLOWING_SOYMILK_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.SOYMILK_FLUID_BLOCK)
            .bucket(ModItems.SOYMILK_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}