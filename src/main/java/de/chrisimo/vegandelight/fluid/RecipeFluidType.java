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

    // The "milky" texture is pre-colored per the existing whitish-orangish soymilk textures.
    // It is also much more opaque than water.
    public static final ResourceLocation MILKY_STILL = new ResourceLocation(VeganDelight.MODID, "block/fluid/milky_still");
    public static final ResourceLocation MILKY_FLOW = new ResourceLocation(VeganDelight.MODID, "block/fluid/milky_flow");

    // The "gloppy" texture is grayscale like water, but it also has a few differences:
    //
    //  1) the animation is a bit slower,
    //  2) texture is sloppier/noisier,
    //  3) it's somewhat more opaque, and
    //  3) it has "lowlights" rather than "highlights."
    public static final ResourceLocation GLOPPY_STILL = new ResourceLocation(VeganDelight.MODID, "block/fluid/gloppy_still");
    public static final ResourceLocation GLOPPY_FLOW = new ResourceLocation(VeganDelight.MODID, "block/fluid/gloppy_flow");

    /**
     * Creates a new recipe-only fluid type based on the vanilla water texture.
     *
     * @return          A new watery fluid type.
     */
    public static RecipeFluidType createWatery() {
        return new RecipeFluidType(WATER_STILL, WATER_FLOW);
    }

    /**
     * Creates a new recipe-only fluid type based on our custom "milky" texture.
     *
     * @return          A new milky fluid type.
     */
    public static RecipeFluidType createMilky() {
        return new RecipeFluidType(MILKY_STILL, MILKY_FLOW);
    }
e
    /**
     * Creates a new recipe-only fluid type based on our custom "gloppy" texture.
     *
     * @return          A new gloppy fluid type.
     */
    public static RecipeFluidType createGloppy() {
        return new RecipeFluidType(GLOPPY_STILL, GLOPPY_FLOW);
    }

    /**
     * Private constructor for recipe-only fluid types.
     *
     * @param still     Resource for the still fluid texture.
     * @param flow      Resource for the flowing fluid texture.
     */
    private RecipeFluidType(ResourceLocation still, ResourceLocation flow) {
        super(FluidType.Properties.create());
        this.stillTexture = still;
        this.flowTexture = flow;
    }

    private final ResourceLocation stillTexture;
    private final ResourceLocation flowTexture;

    // Contains the ARGB tint color, or -1 if the texture is not to be tinted.
    private int tintColor = -1;

    /**
     * Sets an optional tint color on this recipe type's textures.
     *
     * @param tintColor The ARGB tint color to set.
     * @return          This recipe type, for further building.
     */
    public RecipeFluidType tint(int tintColor) {
        this.tintColor = tintColor;
        return this;
    }

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
