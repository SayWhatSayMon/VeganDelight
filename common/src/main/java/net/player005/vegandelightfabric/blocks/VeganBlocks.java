package net.player005.vegandelightfabric.blocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.player005.vegandelightfabric.VeganDelightMod;
import net.player005.vegandelightfabric.fluids.VeganFluids;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.WildCropBlock;

public class VeganBlocks {

    public static final Block SOYBEAN_CROP =
            register(new SoybeanCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)), "soybean_crop", false);

    public static final Block WILD_SOYBEAN =
            register(
                    new WildCropBlock(MobEffects.DAMAGE_BOOST, 12, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)),
                    "wild_soybean", true
            );

    public static final Block POTTED_WILD_SOYBEAN =
            register(
                    new FlowerPotBlock(WILD_SOYBEAN, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)),
                    "potted_wild_soybean", false
            );

    public static final Block SOYBEAN_BAG = register(new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)), "soybean_bag", true);

    public static final Block SOYMILK =
            register(
                    new LiquidBlock(VeganFluids.FLOWING_SOYMILK, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)) {},
                    "soymilk", false
            );

    public static final Block APPLESAUCE =
            register(
                    new LiquidBlock(VeganFluids.FLOWING_APPLESAUCE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)) {},
                    "applesauce", false
            );

    public static @NotNull Block register(Block block, String name, boolean registerItem) {
        ResourceLocation id = ResourceLocation.tryBuild(VeganDelightMod.modID, name);

        assert id != null;

        if (registerItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void initialise() {
    }

    public static final Block[] allBlocks = {SOYBEAN_BAG, WILD_SOYBEAN};
}
