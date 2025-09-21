/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.player005.vegandelightfabric.neoforge;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public final class VeganFluidHandler implements IFluidHandlerItem {

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
    public ItemStack getContainer() {
        return current;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluidStack;
    }

    @Override
    public int getTankCapacity(int tank) {
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.is(fluidStack.getFluidType());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (current.getCount() != 1 || resource.getAmount() < capacity || !fluidStack.isEmpty() || !resource.is(type)) {
            return 0;
        }

        if (action.execute()) {
            current = full.copy();
        }

        return capacity;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (current.getCount() != 1 || resource.getAmount() < capacity) {
            return FluidStack.EMPTY;
        }

        if (!fluidStack.isEmpty() && FluidStack.isSameFluidSameComponents(fluidStack, resource)) {
            if (action.execute()) {
                current = new ItemStack(empty);
            }
            return fluidStack;
        }

        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
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
}
