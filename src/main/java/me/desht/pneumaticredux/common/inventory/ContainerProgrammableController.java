package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityProgrammableController;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerProgrammableController extends ContainerPneumaticBase<TileEntityProgrammableController> {

    public ContainerProgrammableController(InventoryPlayer inventoryPlayer, final TileEntityProgrammableController te) {
        super(te);

        addSlotToContainer(new SlotInventoryLimiting(te, 0, 71, 36));

        addUpgradeSlots(21, 29);

        addPlayerSlots(inventoryPlayer, 84);

    }

}
