/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package de.chrisimo.vegandelight.item;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VeganFluidHandler implements IFluidHandlerItem, ICapabilityProvider {

    private ItemStack current;
    private final ItemStack full;
    private final Item empty;
    private final FluidStack fluidStack;
    private final FluidType type;
    private final int capacity;

    public VeganFluidHandler(ItemStack full, Item empty, boolean defaultFull, Fluid fluid, int capacity) {
        if (defaultFull) {
            current = full.copy();
            fluidStack = new FluidStack(fluid, capacity);
        } else {
            current = new ItemStack(empty);
            fluidStack = FluidStack.EMPTY;
        }
        this.full = full;
        this.empty = empty;
        this.type = fluid.getFluidType();
        this.capacity = capacity;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return current;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return fluidStack;
    }

    @Override
    public int getTankCapacity(int tank) {
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.isFluidEqual(fluidStack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (current.getCount() != 1 || resource.getAmount() < capacity || !fluidStack.isEmpty() || resource.getFluid().getFluidType() != type) {
            return 0;
        }

        if (action.execute()) {
            current = full.copy();
        }

        return capacity;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        if (current.getCount() != 1 || resource.getAmount() < capacity) {
            return FluidStack.EMPTY;
        }

        if (!fluidStack.isEmpty() && FluidStack.areFluidStackTagsEqual(fluidStack, resource)) {
            if (action.execute()) {
                current = new ItemStack(empty);
            }
            return fluidStack;
        }

        return FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        if (current.getCount() != 1 || maxDrain < capacity) {
            return FluidStack.EMPTY;
        }

        if (!fluidStack.isEmpty()) {
            if (action.execute()) {
                current = new ItemStack(empty);
            }
            return fluidStack;
        }

        return FluidStack.EMPTY;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, LazyOptional.of(() -> this));
    }
}
