// This class was copied from porting lib (https://github.com/Fabricators-of-Create/Porting-Lib)
// and is licensed under LGPL v2.1

package net.player005.vegandelightfabric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.player005.vegandelightfabric.fluids.FluidProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class SimpleFlowableFluid extends FlowingFluid {
    private final Supplier<? extends Fluid> flowing;
    private final Supplier<? extends Fluid> still;
    @Nullable
    private final Supplier<? extends Item> bucket;
    @Nullable
    private final Supplier<? extends LiquidBlock> block;
    private final int flowSpeed;
    private final int levelDecreasePerBlock;
    private final float blastResistance;
    private final int tickRate;

    protected SimpleFlowableFluid(@NotNull FluidProperties properties,
                                  Supplier<? extends Fluid> flowing,
                                  Supplier<? extends Fluid> still) {
        this.flowing = flowing;
        this.still = still;
        this.bucket = properties.bucket();
        this.block = properties.block();
        this.flowSpeed = properties.slopeFindDistance();
        this.levelDecreasePerBlock = properties.levelDecreasePerBlock();
        this.blastResistance = properties.explosionResistance();
        this.tickRate = properties.tickRate();
    }

    @Override
    public @NotNull Fluid getFlowing() {
        return flowing.get();
    }

    @Override
    public @NotNull Fluid getSource() {
        return still.get();
    }

    @Override
    protected boolean canConvertToSource(@NotNull Level level) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    @Override
    protected int getSlopeFindDistance(@NotNull LevelReader world) {
        return flowSpeed;
    }

    @Override
    protected int getDropOff(@NotNull LevelReader worldIn) {
        return levelDecreasePerBlock;
    }

    @Override
    public @NotNull Item getBucket() {
        return bucket != null ? bucket.get() : Items.AIR;
    }

    @Override
    protected boolean canBeReplacedWith(@NotNull FluidState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Fluid fluid, @NotNull Direction direction) {
        return direction == Direction.DOWN && !isSame(fluid);
    }

    @Override
    public int getTickDelay(@NotNull LevelReader world) {
        return tickRate;
    }

    @Override
    protected float getExplosionResistance() {
        return blastResistance;
    }

    @Override
    protected @NotNull BlockState createLegacyBlock(@NotNull FluidState state) {
        if (block != null) {
            return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSame(@NotNull Fluid fluid) {
        return fluid == still.get() || fluid == flowing.get();
    }

    public static class Flowing extends SimpleFlowableFluid {
        public Flowing(FluidProperties properties, Supplier<? extends Fluid> flowing, Supplier<? extends Fluid> still) {
            super(properties, flowing, still);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(@NotNull FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return false;
        }
    }

    public static class Still extends SimpleFlowableFluid {
        public Still(FluidProperties properties, Supplier<? extends Fluid> flowing, Supplier<? extends Fluid> still) {
            super(properties, flowing, still);
        }

        @Override
        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return true;
        }
    }
}
