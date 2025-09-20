package net.player005.vegandelightfabric.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.player005.vegandelightfabric.fabric.SimpleFlowableFluid;
import net.player005.vegandelightfabric.VeganBlocks;
import net.player005.vegandelightfabric.VeganDelightMod;

public class VeganDelightClient implements ClientModInitializer {

    public static void registerFluidRenderers(String name, SimpleFlowableFluid.Still still,
                                              FlowingFluid flowing) {
        FluidRenderHandlerRegistry.INSTANCE.register(
            still, flowing, new SimpleFluidRenderHandler(
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "block/" + name + "_still"),
                ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "block/" + name + "_flowing")
            )
        );

        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), flowing, still);
    }

    @Override
    public void onInitializeClient() {
        // Add crops to cutout render layer to make transparency work
        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderType.cutout(),
            VeganBlocks.WILD_SOYBEAN.value(), VeganBlocks.SOYBEAN_CROP.value(), VeganBlocks.POTTED_WILD_SOYBEAN.value()
        );
    }
}
