package net.player005.vegandelightfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Add crops to cutout render layer to make transparency work
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                VeganBlocks.WILD_SOYBEAN, VeganBlocks.SOYBEAN_CROP, VeganBlocks.POTTED_WILD_SOYBEAN
        );

        // allow fluids to be translucent
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.translucent(),
                VeganBlocks.SOYMILK, VeganBlocks.APPLESAUCE
        );
        BlockRenderLayerMap.INSTANCE.putFluids(
                RenderType.translucent(),
                VeganFluids.SOYMILK, VeganFluids.FLOWING_SOYMILK,
                VeganFluids.APPLESAUCE, VeganFluids.FLOWING_APPLESAUCE
        );

        FluidRenderHandlerRegistry.INSTANCE.register(
                VeganFluids.APPLESAUCE,
                VeganFluids.FLOWING_APPLESAUCE,
                new SimpleFluidRenderHandler(
                        ResourceLocation.parse("vegandelight:block/applesauce_still"),
                        ResourceLocation.parse("vegandelight:block/applesauce_flow")
                )
        );

        FluidRenderHandlerRegistry.INSTANCE.register(
                VeganFluids.SOYMILK,
                VeganFluids.FLOWING_SOYMILK,
                new SimpleFluidRenderHandler(
                        ResourceLocation.parse("vegandelight:block/milky_still"),
                        ResourceLocation.parse("vegandelight:block/milky_flow")
                )
        );

    }
}
