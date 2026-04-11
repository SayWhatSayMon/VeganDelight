package net.player005.vegandelightfabric.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;
import net.player005.vegandelightfabric.fabric.SimpleFlowableFluid;
import net.player005.vegandelightfabric.VeganBlocks;
import net.player005.vegandelightfabric.VeganDelightMod;

public class VeganDelightClient implements ClientModInitializer {

    public static void registerFluidRenderers(String name, SimpleFlowableFluid.Still still,
                                              FlowingFluid flowing) {
        FluidRenderHandlerRegistry.INSTANCE.register(
            still, flowing, new SimpleFluidRenderHandler(
                Identifier.fromNamespaceAndPath(VeganDelightMod.modID, "block/" + name + "_still"),
                Identifier.fromNamespaceAndPath(VeganDelightMod.modID, "block/" + name + "_flowing")
            )
        );

        BlockRenderLayerMap.putFluids(ChunkSectionLayer.TRANSLUCENT, flowing, still);
    }

    @Override
    public void onInitializeClient() {
        // Add crops to cutout render layer to make transparency work
        BlockRenderLayerMap.putBlocks(
            ChunkSectionLayer.CUTOUT,
            VeganBlocks.WILD_SOYBEAN.value(), VeganBlocks.SOYBEAN_CROP.value(), VeganBlocks.POTTED_WILD_SOYBEAN.value()
        );
    }
}
