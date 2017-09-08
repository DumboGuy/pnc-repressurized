package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.tileentity.TileEntitySecurityStation;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerSecurityStationInventory extends ContainerPneumaticBase<TileEntitySecurityStation> {

    public ContainerSecurityStationInventory(InventoryPlayer inventoryPlayer, TileEntitySecurityStation te) {
        super(te);

        //add the network slots
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                addSlotToContainer(new SlotItemSpecific(te.getPrimaryInventory(), Itemss.NETWORK_COMPONENT, j + i * 5, 17 + j * 18, 22 + i * 18));
            }
        }

        addUpgradeSlots(128, 62);

        addPlayerSlots(inventoryPlayer, 157);
    }

}
