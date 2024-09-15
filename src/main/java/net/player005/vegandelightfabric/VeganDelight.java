package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelight implements ModInitializer {
    public static String modID = "vegandelight";

    @Override
    public void onInitialize() {
        VeganItems.initialize();
        VeganFluids.initialise();
        VeganBlocks.initialise();
    }
}
