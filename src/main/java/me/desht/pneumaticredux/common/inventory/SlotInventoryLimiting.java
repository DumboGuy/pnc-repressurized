package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityPneumaticBase;
import net.minecraftforge.items.IItemHandler;

public class SlotInventoryLimiting extends SlotBase {

    public SlotInventoryLimiting(IItemHandler handler, int slotIndex, int posX, int posY) {
        super(handler, slotIndex, posX, posY);
    }

    public SlotInventoryLimiting(TileEntityPneumaticBase te, int slotIndex, int posX, int posY) {
        this(te.getPrimaryInventory(), slotIndex, posX, posY);
    }
}
