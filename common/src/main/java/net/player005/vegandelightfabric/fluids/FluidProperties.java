package net.player005.vegandelightfabric.fluids;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.function.Supplier;

public record FluidProperties(
        Supplier<? extends LiquidBlock> block,
        Supplier<? extends Item> bucket,
        int levelDecreasePerBlock,
        int explosionResistance,
        int tickRate,
        int slopeFindDistance
) {
}
