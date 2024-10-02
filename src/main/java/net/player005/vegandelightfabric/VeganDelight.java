package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.ComposterBlock;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelight implements ModInitializer {
    public static String modID = "vegandelight";

    @Override
    public void onInitialize() {
        VeganItems.initialize();
        VeganFluids.initialise();
        VeganBlocks.initialise();

        ComposterBlock.COMPOSTABLES.put(VeganItems.SOYBEAN, 0.45f);
        ComposterBlock.COMPOSTABLES.put(VeganBlocks.WILD_SOYBEAN, 0.65f);
    }
}
