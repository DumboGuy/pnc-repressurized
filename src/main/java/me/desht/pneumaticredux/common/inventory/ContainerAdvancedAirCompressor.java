package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityAirCompressor;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAdvancedAirCompressor extends ContainerAirCompressor {

    public ContainerAdvancedAirCompressor(InventoryPlayer inventoryPlayer, TileEntityAirCompressor te) {
        super(inventoryPlayer, te);
    }

    @Override
    protected int getFuelSlotXOffset() {
        return 69;
    }
}
