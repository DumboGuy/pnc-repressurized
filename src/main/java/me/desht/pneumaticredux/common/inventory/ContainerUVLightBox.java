package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.tileentity.TileEntityUVLightBox;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerUVLightBox extends ContainerPneumaticBase<TileEntityUVLightBox> {

    public ContainerUVLightBox(InventoryPlayer inventoryPlayer, TileEntityUVLightBox te) {
        super(te);

        // Add the burn slot.
        addSlotToContainer(new SlotItemSpecific(te.getPrimaryInventory(), Itemss.EMPTY_PCB, 0, 71, 36));

        addUpgradeSlots(21, 29);

        addPlayerSlots(inventoryPlayer, 84);

    }

}
