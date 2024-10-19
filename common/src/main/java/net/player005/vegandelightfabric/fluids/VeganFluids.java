package net.player005.vegandelightfabric.fluids;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.player005.vegandelightfabric.VeganDelightMod;
import net.player005.vegandelightfabric.VeganItems;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import org.jetbrains.annotations.NotNull;

public class VeganFluids {

    public static final SimpleFlowableFluid.Properties APPLESAUCE_FLUID_PROPERTIES =
            new SimpleFlowableFluid
                    .Properties(() -> VeganFluids.APPLESAUCE, () -> VeganFluids.FLOWING_APPLESAUCE)
                    .blastResistance(100)
                    .flowSpeed(2)
                    .levelDecreasePerBlock(2)
                    .tickRate(50)
                    .bucket(() -> VeganItems.APPLESAUCE_BUCKET)
                    .block(() -> (LiquidBlock) VeganBlocks.APPLESAUCE);

    public static final Fluid APPLESAUCE =
            register(new SimpleFlowableFluid.Still(VeganFluids.APPLESAUCE_FLUID_PROPERTIES), "applesauce");
    public static final FlowingFluid FLOWING_APPLESAUCE = (FlowingFluid)
            register(new SimpleFlowableFluid.Flowing(VeganFluids.APPLESAUCE_FLUID_PROPERTIES), "flowing_applesauce");


    public static final SimpleFlowableFluid.Properties SOYMILK_FLUID_PROPERTIES =
            new SimpleFlowableFluid
                    .Properties(() -> VeganFluids.SOYMILK, () -> VeganFluids.FLOWING_SOYMILK)
                    .blastResistance(100)
                    .flowSpeed(5)
                    .levelDecreasePerBlock(1)
                    .tickRate(5)
                    .bucket(() -> VeganItems.SOYMILK_BUCKET)
                    .block(() -> (LiquidBlock) VeganBlocks.SOYMILK);

    public static final Fluid SOYMILK =
            register(new SimpleFlowableFluid.Still(VeganFluids.SOYMILK_FLUID_PROPERTIES), "soymilk");
    public static final FlowingFluid FLOWING_SOYMILK = (FlowingFluid)
            register(new SimpleFlowableFluid.Flowing(VeganFluids.SOYMILK_FLUID_PROPERTIES), "flowing_soymilk");


    public static void initialise() {
    }

    public static @NotNull Fluid register(Fluid fluid, String name) {
        ResourceLocation id = ResourceLocation.tryBuild(VeganDelightMod.modID, name);
        assert id != null;

        return Registry.register(BuiltInRegistries.FLUID, id, fluid);
    }
}
