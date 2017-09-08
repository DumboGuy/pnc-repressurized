package me.desht.pneumaticredux.common.tileentity;

import me.desht.pneumaticredux.api.item.IItemRegistry.EnumUpgrade;
import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.network.DescSynced;
import me.desht.pneumaticredux.common.network.GuiSynced;
import me.desht.pneumaticredux.common.util.IOHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class TileEntityOmnidirectionalHopper extends TileEntityBase implements IRedstoneControlled {
    public static final int INVENTORY_SIZE = 5;
    @DescSynced
    protected EnumFacing inputDir = EnumFacing.UP;
    @DescSynced
    protected EnumFacing outputDir = EnumFacing.UP;
    private ItemStackHandler inventory = new ItemStackHandler(getInvSize());
    @GuiSynced
    public int redstoneMode;
    private int cooldown;
    @GuiSynced
    protected boolean leaveMaterial;//leave items/liquids (used as filter)

    public TileEntityOmnidirectionalHopper() {
        this(4);
    }

    public TileEntityOmnidirectionalHopper(int upgradeSlots) {
        super(upgradeSlots);
        addApplicableUpgrade(EnumUpgrade.SPEED);
    }

    protected int getInvSize() {
        return INVENTORY_SIZE;
    }

    @Override
    protected boolean shouldRerenderChunkOnDescUpdate() {
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && --cooldown <= 0 && redstoneAllows()) {
            int maxItems = getMaxItems();
            boolean success = suckInItem(maxItems);
            success |= exportItem(maxItems);

            if (!success) {
                cooldown = 8;//When insertion failed, do a long cooldown as penalty for performance.
            } else {
                cooldown = getItemTransferInterval();
            }
        }
    }

    protected boolean exportItem(int maxItems) {
        EnumFacing dir = getRotation();
        TileEntity neighbor = IOHelper.getNeighbor(this, dir);
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && (!leaveMaterial || stack.getCount() > 1)) {
                ItemStack exportedStack = stack.copy();
                if (leaveMaterial) exportedStack.shrink(1);
                if (exportedStack.getCount() > maxItems) exportedStack.setCount(maxItems);
                int count = exportedStack.getCount();

                ItemStack remainder = IOHelper.insert(neighbor, exportedStack, dir.getOpposite(), false);
                int exportedItems = count - remainder.getCount();

                stack.shrink(exportedItems);
                if (stack.getCount() <= 0) {
                    inventory.setStackInSlot(i, ItemStack.EMPTY);
                }
                maxItems -= exportedItems;
                if (maxItems <= 0) return true;
            }
        }
        return false;
    }

    protected boolean suckInItem(int maxItems) {
        TileEntity inputInv = IOHelper.getNeighbor(this, inputDir);
        boolean success = false;

        //Suck from input inventory.
        for (int i = 0; i < maxItems; i++) {
            if (hasEmptySlot()) {
                ItemStack extracted = IOHelper.extractOneItem(inputInv, inputDir.getOpposite(), true); // simulate extraction from the neighbor.
                if (!extracted.isEmpty()) {
                    ItemStack inserted = IOHelper.insert(this, extracted, null, false); // if we can insert the item in this hopper.
                    if (inserted.isEmpty()) {
                        IOHelper.extractOneItem(inputInv, inputDir.getOpposite(), false); // actually retrieve it from the neighbor.
                        success = true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                for (int slot = 0; slot < inventory.getSlots(); slot++) {
                    ItemStack stack = inventory.getStackInSlot(slot);
                    stack = stack.copy();
                    stack.setCount(1);
                    ItemStack extracted = IOHelper.extract(inputInv, inputDir.getOpposite(), stack, true, true); // simulate extraction from the neighbor.
                    if (!extracted.isEmpty()) {
                        ItemStack inserted = IOHelper.insert(this, extracted, null, false); // if we can insert the item in this hopper.
                        if (inserted.isEmpty()) {
                            IOHelper.extract(inputInv, inputDir.getOpposite(), stack, true, false); //actually retrieve it from the neighbor.
                            success = true;
                            break;
                        }
                    }
                }
                if (!success) break;
            }
        }

        for (EntityItem entity : getNeighborItems(this, inputDir)) {
            if (!entity.isDead) {
                ItemStack remainder = IOHelper.insert(this, entity.getItem(), null, false);
                if (remainder.isEmpty()) {
                    entity.setDead();
                    success = true; //Don't set true when the stack could not be fully consumes, as that means next insertion there won't be any room.
                }
            }
        }

        return success;
    }

    private boolean hasEmptySlot() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    public static List<EntityItem> getNeighborItems(TileEntity te, EnumFacing inputDir) {
        AxisAlignedBB box = new AxisAlignedBB(te.getPos().offset(inputDir), te.getPos().offset(inputDir).add(1, 1, 1));
        return te.getWorld().getEntitiesWithinAABB(EntityItem.class, box);
    }

    public int getMaxItems() {
        int upgrades = getUpgrades(EnumUpgrade.SPEED);
        if (upgrades > 3) {
            return Math.min((int) Math.pow(2, upgrades - 3), 256);
        } else {
            return 1;
        }
    }

    public int getItemTransferInterval() {
        return 8 / (1 << getUpgrades(EnumUpgrade.SPEED)); // Math.pow(2, getUpgrades(EnumUpgrade.SPEED));
    }

    public void setDirection(EnumFacing dir) {
        inputDir = dir;
    }

    public EnumFacing getDirection() {
        return inputDir;
    }

    @Override
    public EnumFacing getRotation() {
        return outputDir;
    }

    public void setRotation(EnumFacing rotation) {
        outputDir = rotation;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("inputDir", inputDir.ordinal());
        tag.setInteger("outputDir", outputDir.ordinal());
        tag.setInteger("redstoneMode", redstoneMode);
        tag.setBoolean("leaveMaterial", leaveMaterial);
        tag.setTag("Items", inventory.serializeNBT());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inputDir = EnumFacing.getFront(tag.getInteger("inputDir"));
        outputDir = EnumFacing.getFront(tag.getInteger("outputDir"));
        redstoneMode = tag.getInteger("redstoneMode");
        leaveMaterial = tag.getBoolean("leaveMaterial");
        inventory.deserializeNBT(tag.getCompoundTag("Items"));
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getName() {
        return Blockss.omnidirectionalHopper.getUnlocalizedName();
    }

    @Override
    public void handleGUIButtonPress(int buttonID, EntityPlayer player) {
        if (buttonID == 0) {
            redstoneMode++;
            if (redstoneMode > 2) redstoneMode = 0;
        } else if (buttonID == 1) {
            leaveMaterial = false;
        } else if (buttonID == 2) {
            leaveMaterial = true;
        }
    }

    @Override
    public int getRedstoneMode() {
        return redstoneMode;
    }

    public boolean doesLeaveMaterial() {
        return leaveMaterial;
    }
}
