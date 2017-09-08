package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityOmnidirectionalHopper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerOmnidirectionalHopper extends ContainerPneumaticBase<TileEntityOmnidirectionalHopper> {

    public ContainerOmnidirectionalHopper(InventoryPlayer inventoryPlayer, TileEntityOmnidirectionalHopper te) {
        super(te);

        addUpgradeSlots(23, 29);

        for (int i = 0; i < 5; i++)
            addSlotToContainer(new SlotItemHandler(te.getPrimaryInventory(), i, 68 + i * 18, 36));

        addPlayerSlots(inventoryPlayer, 84);
    }

//    @Override
//    public boolean canInteractWith(EntityPlayer player) {
//        return te.isUseableByPlayer(player);
//    }
}
