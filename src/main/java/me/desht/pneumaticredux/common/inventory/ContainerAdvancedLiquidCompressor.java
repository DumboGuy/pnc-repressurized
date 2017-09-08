package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityAdvancedLiquidCompressor;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAdvancedLiquidCompressor extends ContainerLiquidCompressor {

    public ContainerAdvancedLiquidCompressor(InventoryPlayer inventoryPlayer, TileEntityAdvancedLiquidCompressor te) {
        super(inventoryPlayer, te);
    }

    @Override
    protected int getFluidContainerOffset() {
        return 52;
    }

}
