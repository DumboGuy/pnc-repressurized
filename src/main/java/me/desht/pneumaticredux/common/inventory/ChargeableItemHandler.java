package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.NBTUtil;
import me.desht.pneumaticredux.common.tileentity.TileEntityChargingStation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class ChargeableItemHandler extends ItemStackHandler {
    private static final int INVENTORY_SIZE = 9;

    private final ItemStack armorStack;

    public ChargeableItemHandler(TileEntityChargingStation te) {
        super(INVENTORY_SIZE);

        this.armorStack = te.getChargingItem();

        if (!hasInventory()) {
            createInventory();
        }
        loadInventory();
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        saveInventory();
    }

    private boolean hasInventory() {
        return NBTUtil.hasTag(armorStack, "UpgradeInventory");
    }

    private void createInventory() {
        writeToNBT();
    }

    private void loadInventory() {
        readFromNBT();
    }

    public void saveInventory() {
        writeToNBT();
    }

    public void writeToNBT() {
        NBTTagCompound inv = new NBTTagCompound();
        inv.setTag("Items", serializeNBT());
        NBTUtil.setCompoundTag(armorStack, "UpgradeInventory", inv);
    }

    private void readFromNBT() {
        deserializeNBT(NBTUtil.getCompoundTag(armorStack, "UpgradeInventory"));
    }

}
