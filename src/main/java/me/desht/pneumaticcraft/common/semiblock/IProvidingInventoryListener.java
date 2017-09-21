package me.desht.pneumaticcraft.common.semiblock;

import net.minecraft.tileentity.TileEntity;

public interface IProvidingInventoryListener {
    public void notify(TileEntity te);
}
