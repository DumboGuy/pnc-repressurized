package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityUniversalSensor;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerUniversalSensor extends ContainerPneumaticBase<TileEntityUniversalSensor> {

    public ContainerUniversalSensor(InventoryPlayer inventoryPlayer, TileEntityUniversalSensor te) {
        super(te);

        addUpgradeSlots(19, 108);

        addPlayerSlots(inventoryPlayer, 157);
    }

}
