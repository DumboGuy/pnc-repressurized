package me.desht.pneumaticredux.common.semiblock;

import net.minecraft.tileentity.TileEntity;

public interface IProvidingInventoryListener {
    public void notify(TileEntity te);
}
