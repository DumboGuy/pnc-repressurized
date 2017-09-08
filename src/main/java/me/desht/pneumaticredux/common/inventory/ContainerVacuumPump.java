package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityVacuumPump;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerVacuumPump extends ContainerPneumaticBase<TileEntityVacuumPump> {

    public ContainerVacuumPump(InventoryPlayer inventoryPlayer, TileEntityVacuumPump te) {
        super(te);

        addUpgradeSlots(71, 29);

        addPlayerSlots(inventoryPlayer, 84);
    }
}
