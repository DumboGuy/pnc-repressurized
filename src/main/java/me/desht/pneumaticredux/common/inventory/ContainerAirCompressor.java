package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityAirCompressor;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAirCompressor extends ContainerPneumaticBase<TileEntityAirCompressor> {

    public ContainerAirCompressor(InventoryPlayer inventoryPlayer, TileEntityAirCompressor te) {
        super(te);

        // Add the burn slot.
        addSlotToContainer(new SlotInventoryLimiting(te, 0, getFuelSlotXOffset(), 54));

        addUpgradeSlots(23, 29);
        addPlayerSlots(inventoryPlayer, 84);
    }

    protected int getFuelSlotXOffset() {
        return 80;
    }

}
