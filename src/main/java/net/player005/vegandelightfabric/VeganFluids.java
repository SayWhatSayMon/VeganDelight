package net.player005.vegandelightfabric;

import io.github.fabricators_of_create.porting_lib.util.SimpleFlowableFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

public class VeganFluids {

//    public static final Fluid SOYMILK_FLUID = RecipeFluidTypeNew.createMilky("soymilk", VeganItems.SOYMILK_BUCKET);
//    public static final Fluid APPLESAUCE_FLUID = RecipeFluidTypeNew.createGloppy("applesauce", VeganItems.APPLESAUCE_BUCKET);

    public static final SimpleFlowableFluid.Properties SOYMILK_FLUID_PROPERTIES =
            new SimpleFlowableFluid.Properties(() -> VeganFluids.SOYMILK_FLUID, () -> VeganFluids.SOYMILK_FLOWING_FLUID);
    public static final Fluid SOYMILK_FLUID =
            register(new SimpleFlowableFluid.Still(VeganFluids.SOYMILK_FLUID_PROPERTIES),"soymilk");
    public static final FlowingFluid SOYMILK_FLOWING_FLUID =
            register(new SimpleFlowableFluid.Flowing(VeganFluids.SOYMILK_FLUID_PROPERTIES), "soymilk_fluid");

    public static final SimpleFlowableFluid.Properties APPLESAUCE_FLUID_PROPERTIES =
            new SimpleFlowableFluid.Properties(() -> VeganFluids.APPLESAUCE_FLUID, () -> VeganFluids.APPLESAUCE_FLOWING_FLUID);
    public static final Fluid APPLESAUCE_FLUID =
            register(new SimpleFlowableFluid.Still(VeganFluids.APPLESAUCE_FLUID_PROPERTIES),"applesauce");
    public static final Fluid APPLESAUCE_FLOWING_FLUID =
            register(new SimpleFlowableFluid.Flowing(VeganFluids.APPLESAUCE_FLUID_PROPERTIES), "applesauce_fluid");


    public static void initialise() {}

    public static @NotNull FlowingFluid register(Fluid fluid, String name) {
        ResourceLocation id = ResourceLocation.tryBuild(VeganDelight.modID, name);
        assert id != null;

        return (FlowingFluid) Registry.register(BuiltInRegistries.FLUID, id, fluid);
    }
}
