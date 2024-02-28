package de.chrisimo.vegandelight.block;

import de.chrisimo.vegandelight.VeganDelight;
import de.chrisimo.vegandelight.block.custom.SoybeanCropBlock;
import de.chrisimo.vegandelight.item.ModItems;
import de.chrisimo.vegandelight.fluid.ModFluids;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VeganDelight.MODID);

    public static final RegistryObject<Block> SOYBEAN_CROP = BLOCKS.register("soybean_crop",
            () -> new SoybeanCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    public static final RegistryObject<Block> WILD_SOYBEAN = registerBlock("wild_soybean",
            () -> new FlowerBlock(() -> MobEffects.LUCK, 5,
                    BlockBehaviour.Properties.copy(Blocks.ALLIUM).noCollission().noOcclusion()));

    public static final RegistryObject<Block> POTTED_WILD_SOYBEAN = BLOCKS.register("potted_wild_soybean",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.WILD_SOYBEAN,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM).noOcclusion()));

    public static final RegistryObject<Block> SOYBEAN_BAG = registerBlock("soybean_bag",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<LiquidBlock> SOYMILK_FLUID_BLOCK = BLOCKS.register("soymilk_fluid_block",
            () -> new LiquidBlock(ModFluids.SOYMILK_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
