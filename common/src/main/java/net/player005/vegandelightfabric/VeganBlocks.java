package net.player005.vegandelightfabric;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import vectorwing.farmersdelight.common.block.WildCropBlock;

import java.util.function.Function;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganBlocks {

    public static final Holder<Block> SOYBEAN_CROP =
        register(props -> new CropBlock(props) {
            @Override
            protected ItemLike getBaseSeedId() {
                return VeganItems.SOYBEAN.value();
            }
        }, "soybean_crop", false, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));

    public static final Holder<Block> WILD_SOYBEAN = register(
        props -> new WildCropBlock(MobEffects.STRENGTH, 12, props),
        "wild_soybean", true, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)
    );

    public static final Holder<Block> POTTED_WILD_SOYBEAN = register(
        props -> new FlowerPotBlock(WILD_SOYBEAN.value(), props),
        "potted_wild_soybean", false, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)
    );

    public static final Holder<Block> SOYBEAN_BAG = register(
        Block::new,
        "soybean_bag", true, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)
    );

    public static final Holder<LiquidBlock> SOYMILK = register(
        props -> new LiquidBlock(VeganFluids.SOYMILK.get(), props) { },
        "soymilk", false, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
    );

    public static final Holder<LiquidBlock> APPLESAUCE = register(
        props -> new LiquidBlock(VeganFluids.APPLESAUCE.get(), props) { },
        "applesauce_fluid", false, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER));

    public static <T extends Block> Holder<T> register(Function<BlockBehaviour.Properties, T> blockFactory, String name,
                                                       boolean registerItem, BlockBehaviour.Properties baseProperties) {
        Identifier id = Identifier.tryBuild(VeganDelightMod.modID, name);
        assert id != null;
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);

        var holder = getPlatform().register(BuiltInRegistries.BLOCK, blockKey, () -> blockFactory.apply(baseProperties.setId(blockKey)));

        if (registerItem) {
            ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
            getPlatform().register(BuiltInRegistries.ITEM, itemKey, () ->
                new BlockItem(holder.value(), new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()));
        }

        return holder;
    }

    public static Block[] getAllBlockItems() {
        return new Block[] {
            SOYBEAN_BAG.value(), WILD_SOYBEAN.value()
        };
    }

    static void initialize() { }
}
