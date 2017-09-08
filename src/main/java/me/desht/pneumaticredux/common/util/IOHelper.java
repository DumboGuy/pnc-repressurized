package me.desht.pneumaticredux.common.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

/*
 * This file is part of Blue Power.
 *
 *     Blue Power is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Blue Power is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Blue Power.  If not, see <http://www.gnu.org/licenses/>
 */

/**
 * @author MineMaarten
 * @author Dynious
 */
public class IOHelper {
    public static IItemHandler getInventoryForTE(TileEntity te, EnumFacing facing) {
        if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
            return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
        } else {
            return null;
        }
    }
    public static IItemHandler getInventoryForTE(TileEntity te) {
        return getInventoryForTE(te, null);
    }

    public static TileEntity getNeighbor(TileEntity te, EnumFacing dir) {
        return te.getWorld().getTileEntity(te.getPos().offset(dir));
    }

    /**
     * Extracts an exact amount on all sides
     *
     * @param tile
     * @param itemStack
     * @param simulate
     * @return
     */
    @Nonnull
    public static ItemStack extract(TileEntity tile, ItemStack itemStack, boolean simulate) {
        for (EnumFacing d : EnumFacing.VALUES) {
            ItemStack extracted = extract(tile, d, itemStack, true, simulate);
            if (!extracted.isEmpty()) return extracted;
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    public static ItemStack extract(TileEntity inventory, EnumFacing direction, boolean simulate) {
        IItemHandler handler = getInventoryForTE(inventory, direction);
        return handler == null ? ItemStack.EMPTY : extract(handler, simulate);
    }

    @Nonnull
    public static ItemStack extract(IItemHandler handler, boolean simulate) {
        for (int slot = 0; slot < handler.getSlots(); ++slot) {
            ItemStack stack = extract(handler, slot, simulate);
            if (!stack.isEmpty()) return stack;
        }
        return ItemStack.EMPTY;
    }


    @Nonnull
    public static ItemStack extract(IItemHandler handler, int slot, boolean simulate) {
        ItemStack stack = handler.getStackInSlot(slot);
        return handler.extractItem(slot, stack.getCount(), simulate);
    }

    @Nonnull
    public static ItemStack extract(TileEntity tile, EnumFacing direction, ItemStack requestedStack, boolean useItemCount, boolean simulate) {
        return extract(tile, direction, requestedStack, useItemCount, simulate, 0);
    }

//    @Nonnull
//    public static int[] getAccessibleSlotsForInventory(IInventory inv, EnumFacing side) {
//        int[] accessibleSlots;
//        if (inv != null) {
//            if (inv instanceof ISidedInventory) {
//                accessibleSlots = ((ISidedInventory) inv).getSlotsForFace(side);
//            } else {
//                accessibleSlots = new int[inv.getSizeInventory()];
//                for (int i = 0; i < accessibleSlots.length; i++)
//                    accessibleSlots[i] = i;
//            }
//            return accessibleSlots;
//        } else {
//            return new int[0];
//        }
//    }

    /**
     * Retrieves an item from the specified inventory. This item can be specified.
     *
     * @param tile
     * @param direction
     * @param requestedStack
     * @param useItemCount   if true, it'll only retrieve the stack of the exact item count given. it'll look in multiple slots of the inventory. if false, the
     *                       first matching stack, ignoring item count, will be returned.
     * @param simulate
     * @param fuzzySetting   ,
     * @return
     */
    @Nonnull
    public static ItemStack extract(TileEntity tile, EnumFacing direction, ItemStack requestedStack, boolean useItemCount, boolean simulate, int fuzzySetting) {
        if (requestedStack.isEmpty()) return requestedStack;

        IItemHandler handler = getInventoryForTE(tile, direction);
        if (handler != null) {
            int itemsFound = 0;
            for (int slot = 0; slot < handler.getSlots(); slot++) {
                ItemStack stack = handler.getStackInSlot(slot);
                if (!stack.isEmpty() && stack.isItemEqual(requestedStack)) {
                    if (!useItemCount) {
                        return handler.extractItem(slot, stack.getCount(), simulate);
                    }
                    itemsFound += stack.getCount();
                }
            }
            if (itemsFound >= requestedStack.getCount()) {
                ItemStack exportedStack = ItemStack.EMPTY;
                int itemsNeeded = requestedStack.getCount();
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    ItemStack stack = handler.getStackInSlot(slot);
                    if (stack.isItemEqual(requestedStack)) {
                        int itemsSubtracted = Math.min(itemsNeeded, stack.getCount());
                        if (itemsSubtracted > 0) {
                            exportedStack = stack;
                        }
                        itemsNeeded -= itemsSubtracted;
                        if (itemsNeeded <= 0) break;
                        handler.extractItem(slot, itemsSubtracted, simulate);
                        if (!simulate) tile.markDirty();
                    }
                }
                exportedStack = exportedStack.copy();
                exportedStack.setCount(requestedStack.getCount());
                return exportedStack;
            }
        }
        return ItemStack.EMPTY;

    }

    @Nonnull
    public static ItemStack extractOneItem(TileEntity tile, EnumFacing dir, boolean simulate) {
        IItemHandler handler = getInventoryForTE(tile, dir);
        if (handler != null) {
            for (int slot = 0; slot < handler.getSlots(); slot++) {
                ItemStack stack = handler.getStackInSlot(slot);
                if (stack.getCount() > 0) {
                    ItemStack ret = handler.extractItem(slot, 1, simulate);
                    if (!simulate) tile.markDirty();
                    return ret;
                }
            }
        }
        return null;
    }

    /**
     * Inserts on all sides
     *
     * @param tile
     * @param itemStack
     * @param simulate
     * @return excess items which couldn't be inserted
     */
    @Nonnull
    public static ItemStack insert(TileEntity tile, ItemStack itemStack, boolean simulate) {
        ItemStack insertingStack = itemStack.copy();
        for (EnumFacing side : EnumFacing.VALUES) {
            IItemHandler inv = getInventoryForTE(tile, side);
            insertingStack = ItemHandlerHelper.insertItem(inv, insertingStack, simulate);
            if (insertingStack.isEmpty()) return ItemStack.EMPTY;
        }
        return insertingStack;
    }

    @Nonnull
    public static ItemStack insert(TileEntity tile, ItemStack itemStack, EnumFacing side, boolean simulate) {
        IItemHandler inv = getInventoryForTE(tile, side);
        if (inv != null) return ItemHandlerHelper.insertItem(inv, itemStack, simulate);
        return itemStack;
    }

    @Nonnull
    public static ItemStack insert(ICapabilityProvider provider, ItemStack itemStack, EnumFacing side, boolean simulate) {
        if (provider.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            return ItemHandlerHelper.insertItem(handler, itemStack, simulate);
        }
        return ItemStack.EMPTY;
    }

//    public static boolean canInsertItemToInventory(IInventory inventory, ItemStack itemStack, int slot, boolean[] sides) {
//        for (int i = 0; i < sides.length; i++) {
//            if (sides[i] && canInsertItemToInventory(inventory, itemStack, slot, EnumFacing.getFront(i))) return true;
//        }
//        return false;
//    }
//
//    public static boolean canExtractItemFromInventory(IItemHandler inventory, ItemStack itemStack, int slot, boolean[] sides) {
//        for (int i = 0; i < sides.length; i++) {
//            if (sides[i] && canExtractItemFromInventory(inventory, itemStack, slot, EnumFacing.getFront(i)))
//                return true;
//        }
//        return false;
//    }

//    public static boolean canInsertItemToInventory(IInventory inventory, ItemStack itemStack, int slot, EnumFacing side) {
//
//        return inventory.isItemValidForSlot(slot, itemStack) && (!(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canInsertItem(slot, itemStack, side));
//    }
//
//    public static boolean canExtractItemFromInventory(IInventory inventory, ItemStack itemStack, int slot, EnumFacing side) {
//
//        return !(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canExtractItem(slot, itemStack, side);
//    }

//    public static void dropInventory(World world, BlockPos pos) {
//
//        TileEntity tileEntity = world.getTileEntity(pos);
//
//        if (!(tileEntity instanceof IInventory)) {
//            return;
//        }
//
//        IInventory inventory = (IInventory) tileEntity;
//
//        for (int i = 0; i < inventory.getSizeInventory(); i++) {
//            ItemStack itemStack = inventory.getStackInSlot(i);
//
//            if (itemStack.getCount() > 0) {
//                spawnItemInWorld(world, itemStack, pos.getX(), pos.getY(), pos.getZ());
//            }
//        }
//    }

    public static void spawnItemInWorld(World world, ItemStack itemStack, double x, double y, double z) {

        if (world.isRemote) return;
        float dX = world.rand.nextFloat() * 0.8F + 0.1F;
        float dY = world.rand.nextFloat() * 0.8F + 0.1F;
        float dZ = world.rand.nextFloat() * 0.8F + 0.1F;

        EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.getItem(), itemStack.getCount(), itemStack.getItemDamage()));

        if (itemStack.hasTagCompound()) {
            entityItem.getItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
        }

        float factor = 0.05F;
        entityItem.motionX = world.rand.nextGaussian() * factor;
        entityItem.motionY = world.rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = world.rand.nextGaussian() * factor;
        world.spawnEntity(entityItem);
        itemStack.setCount(0);
    }

//    public static boolean canInterfaceWith(TileEntity tile, EnumFacing direction) {
//        if (tile instanceof IInventory) {
//            return !(tile instanceof ISidedInventory) || ((ISidedInventory) tile).getSlotsForFace(direction).length > 0;
//        }
//        return false;
//    }
}
