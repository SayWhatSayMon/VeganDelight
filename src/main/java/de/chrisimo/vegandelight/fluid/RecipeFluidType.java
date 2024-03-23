package de.chrisimo.vegandelight.fluid;

import de.chrisimo.vegandelight.VeganDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

/**
 * A fluid type that offers very little in the way of customization because it is meant to
 * be used as a recipe-only fluid (ie. one without a bucket or block).
 */
public class RecipeFluidType extends FluidType {
    // The vanilla grayscale water texture for fluids that are quick-moving and more translucent.
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");

    // The "gloppy" texture is grayscale like water, but it also has a few differences:
    //
    //  1) the animation is a bit slower,
    //  2) texture is sloppier/noisier,
    //  3) it's somewhat more opaque, and
    //  3) it has "lowlights" rather than "highlights."
    public static final ResourceLocation GLOPPY_STILL = new ResourceLocation(VeganDelight.MODID, "block/fluid/gloppy_still");
    public static final ResourceLocation GLOPPY_FLOW = new ResourceLocation(VeganDelight.MODID, "block/fluid/gloppy_flow");

    /**
     * Creates a new recipe-only fluid type based on the vanilla water texture and the supplied tint.
     *
     * @param tintColor RGB tint of the new fluid type.
     * @return          A new watery fluid type.
     */
    public static RecipeFluidType createWatery(final int tintColor) {
        return new RecipeFluidType(WATER_STILL, WATER_FLOW, 0xFF000000 | tintColor);
    }

    /**
     * Creates a new recipe-only fluid type based on our custom "gloppy" texture and the supplied tint.
     *
     * @param tintColor RGB tint of the new fluid type.
     * @return          A new gloppy fluid type.
     */
    public static RecipeFluidType createGloppy(final int tintColor) {
        return new RecipeFluidType(GLOPPY_STILL, GLOPPY_FLOW, 0xF0000000 | tintColor);
    }

    /**
     * Private constructor for recipe-only fluid types.
     *
     * @param still     Resource for the still fluid texture.
     * @param flow      Resource for the flowing fluid texture.
     * @param tintColor RGB tint of the new fluid type.
     */
    private RecipeFluidType(ResourceLocation still, ResourceLocation flow, int tintColor) {
        super(FluidType.Properties.create());
        this.stillTexture = still;
        this.flowTexture = flow;
        this.tintColor = tintColor;
    }

    private final ResourceLocation stillTexture;
    private final ResourceLocation flowTexture;
    private final int tintColor;

    /**
     * Inherited method to configure the new fluid type.
     *
     * @param consumer Consumer that will accept the client extensions that configure this fluid type.
     */
    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() { return stillTexture; }
            @Override
            public ResourceLocation getFlowingTexture() { return flowTexture; }
            @Override
            public int getTintColor() { return tintColor; }
        });
    }
}
