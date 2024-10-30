package net.player005.vegandelightfabric.fluids;

import net.minecraft.world.level.material.FlowingFluid;
import net.player005.vegandelightfabric.VeganItems;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

import static net.player005.vegandelightfabric.VeganDelightMod.platform;

public class VeganFluids {

    public static final FluidProperties APPLESAUCE_PROPERTIES = new FluidProperties(
            () -> VeganBlocks.APPLESAUCE,
            () -> VeganItems.APPLESAUCE_BUCKET,
            2,
            100,
            50,
            2
    );

    public static final FlowingFluid APPLESAUCE = platform.registerFluids("applesauce", APPLESAUCE_PROPERTIES);

    public static final FluidProperties SOYMILK_PROPERTIES = new FluidProperties(
            () -> VeganBlocks.SOYMILK,
            () -> VeganItems.SOYMILK_BUCKET,
            1,
            100,
            5,
            5
    );

    public static final FlowingFluid SOYMILK = platform.registerFluids("soymilk", SOYMILK_PROPERTIES);

    public static void initialise() {
    }

}
