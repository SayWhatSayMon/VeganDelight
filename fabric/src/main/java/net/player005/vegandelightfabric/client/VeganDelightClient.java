package net.player005.vegandelightfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Add crops to cutout render layer to make transparency work
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                VeganBlocks.WILD_SOYBEAN, VeganBlocks.SOYBEAN_CROP, VeganBlocks.POTTED_WILD_SOYBEAN
        );

//        BlockRenderLayerMap.INSTANCE.putFluids(
//                RenderType.translucent(),
//                VeganFluids.SOYMILK, VeganFluids.SOYMILK,
//                VeganFluids.APPLESAUCE, VeganFluids.APPLESAUCE
//        );

//        FluidRenderHandlerRegistry.INSTANCE.register(
//                VeganFluids.APPLESAUCE,
//                VeganFluids.APPLESAUCE,
//                new SimpleFluidRenderHandler(
//                        ResourceLocation.parse("vegandelight:block/applesauce_still"),
//                        ResourceLocation.parse("vegandelight:block/applesauce_flow")
//                )
//        );
//
//        FluidRenderHandlerRegistry.INSTANCE.register(
//                VeganFluids.SOYMILK,
//                VeganFluids.SOYMILK,
//                new SimpleFluidRenderHandler(
//                        ResourceLocation.parse("vegandelight:block/milky_still"),
//                        ResourceLocation.parse("vegandelight:block/milky_flow")
//                )
//        );

    }
}
