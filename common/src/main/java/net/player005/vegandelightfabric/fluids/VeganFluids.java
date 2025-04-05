package net.player005.vegandelightfabric.fluids;

import net.minecraft.world.level.material.FlowingFluid;
import net.player005.vegandelightfabric.VeganBlocks;
import net.player005.vegandelightfabric.VeganItems;

import java.util.function.Supplier;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

@SuppressWarnings("Convert2MethodRef")
public class VeganFluids {

    public static final FluidProperties APPLESAUCE_PROPERTIES = new FluidProperties(
        () -> VeganBlocks.APPLESAUCE.value(),
        () -> VeganItems.APPLESAUCE_BUCKET.value(),
        2,
        100,
        50,
        2
    );

    public static final Supplier<FlowingFluid> APPLESAUCE = getPlatform().registerFluids("applesauce", APPLESAUCE_PROPERTIES);

    public static final FluidProperties SOYMILK_PROPERTIES = new FluidProperties(
        () -> VeganBlocks.SOYMILK.value(),
        () -> VeganItems.SOYMILK_BUCKET.value(),
        1,
        100,
        5,
        5
    );

    public static final Supplier<FlowingFluid> SOYMILK = getPlatform().registerFluids("soymilk", SOYMILK_PROPERTIES);

    public static void initialise() { }
}
