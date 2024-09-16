package net.player005.vegandelightfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                VeganBlocks.WILD_SOYBEAN, VeganBlocks.SOYBEAN_CROP, VeganBlocks.POTTED_WILD_SOYBEAN
        );
    }
}
