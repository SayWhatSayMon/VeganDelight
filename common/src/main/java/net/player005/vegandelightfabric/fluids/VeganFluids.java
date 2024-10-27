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

import static net.player005.vegandelightfabric.VeganDelightMod.platform;

public class VeganFluids {

    public static final SimpleFlowableFluid.Properties APPLESAUCE_FLUID_PROPERTIES =
            new SimpleFlowableFluid
                    .Properties(() -> VeganFluids.APPLESAUCE, () -> VeganFluids.FLOWING_APPLESAUCE)
                    .blastResistance(100)
                    .flowSpeed(2)
                    .levelDecreasePerBlock(2)
                    .tickRate(50)
                    .block(() -> (LiquidBlock) VeganBlocks.APPLESAUCE)
                    .bucket(() -> VeganItems.APPLESAUCE_BUCKET);

    public static final Fluid APPLESAUCE =
            register(platform.createStillFluid(VeganFluids.APPLESAUCE_FLUID_PROPERTIES), "applesauce");
    public static final FlowingFluid FLOWING_APPLESAUCE = (FlowingFluid)
            register(platform.createFlowingFluid(VeganFluids.APPLESAUCE_FLUID_PROPERTIES), "flowing_applesauce");


    public static final SimpleFlowableFluid.Properties SOYMILK_FLUID_PROPERTIES =
            new SimpleFlowableFluid
                    .Properties(() -> VeganFluids.SOYMILK, () -> VeganFluids.FLOWING_SOYMILK)
                    .blastResistance(100)
                    .flowSpeed(5)
                    .levelDecreasePerBlock(1)
                    .tickRate(5)
                    .block(() -> (LiquidBlock) VeganBlocks.SOYMILK)
                    .bucket(() -> VeganItems.SOYMILK_BUCKET);

    public static final Fluid SOYMILK =
            register(platform.createStillFluid(VeganFluids.SOYMILK_FLUID_PROPERTIES), "soymilk");
    public static final FlowingFluid FLOWING_SOYMILK = (FlowingFluid)
            register(platform.createFlowingFluid(VeganFluids.SOYMILK_FLUID_PROPERTIES), "flowing_soymilk");


    public static void initialise() {
    }

    public static @NotNull Fluid register(Fluid fluid, String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, name);

        return Registry.register(BuiltInRegistries.FLUID, id, fluid);
    }
}
