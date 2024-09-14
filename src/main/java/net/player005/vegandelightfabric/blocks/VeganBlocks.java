package net.player005.vegandelightfabric.blocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.player005.vegandelightfabric.VeganDelight;
import org.jetbrains.annotations.NotNull;

public class VeganBlocks {

    public static final Block SOYBEAN_CROP =
            register(new Block(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()), "soybean_crop", true);

    public static final Block WILD_SOYBEAN =
            register(new Block(BlockBehaviour.Properties.copy(Blocks.ALLIUM).noCollission().noOcclusion()), "wild_soybean", true);

    public static final Block POTTET_WILD_SOYBEAN =
            register(new Block(BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM).noOcclusion()),"potted_wild_soybean", true);

    public static final Block SOYBEAN_BAG =
            register(new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)), "soybean_bag", true);

    public static @NotNull Block register(Block block, String name, boolean registerItem) {
        ResourceLocation id = ResourceLocation.tryBuild(VeganDelight.modID, name);

        assert id != null;

        if (registerItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

}
