package net.player005.vegandelightfabric;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import vectorwing.farmersdelight.common.block.WildCropBlock;

import java.util.function.Supplier;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganBlocks {

    public static final Holder<Block> SOYBEAN_CROP =
        register(() -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)) {
            @Override
            protected ItemLike getBaseSeedId() {
                return VeganItems.SOYBEAN.value();
            }
        }, "soybean_crop", false);

    public static final Holder<Block> WILD_SOYBEAN = register(
        () -> new WildCropBlock(MobEffects.DAMAGE_BOOST, 12, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)),
        "wild_soybean", true
    );

    public static final Holder<Block> POTTED_WILD_SOYBEAN = register(
        () -> new FlowerPotBlock(WILD_SOYBEAN.value(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)),
        "potted_wild_soybean", false
    );

    public static final Holder<Block> SOYBEAN_BAG = register(
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)),
        "soybean_bag", true
    );

    public static final Holder<LiquidBlock> SOYMILK = register(
        () -> new LiquidBlock(VeganFluids.SOYMILK.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)) { },
        "soymilk", false
    );

    public static final Holder<LiquidBlock> APPLESAUCE = register(
        () -> new LiquidBlock(VeganFluids.APPLESAUCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)) { },
        "applesauce", false);

    public static <T extends Block> Holder<T> register(Supplier<T> block, String name, boolean registerItem) {
        ResourceLocation id = ResourceLocation.tryBuild(VeganDelightMod.modID, name);
        assert id != null;

        var holder = getPlatform().register(BuiltInRegistries.BLOCK, id, block);

        if (registerItem)
            getPlatform().register(BuiltInRegistries.ITEM, id, () -> new BlockItem(holder.value(), new Item.Properties()));

        return holder;
    }

    public static Block[] getAllBlockItems() {
        return new Block[] {
            SOYBEAN_BAG.value(), WILD_SOYBEAN.value()
        };
    }

    static void initialize() { }
}
