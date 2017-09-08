package me.desht.pneumaticredux.common.network;

import me.desht.pneumaticredux.common.inventory.SyncedField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IDescSynced {
    public static enum Type {
        TILE_ENTITY, SEMI_BLOCK;
    }

    public Type getSyncType();

    public List<SyncedField> getDescriptionFields();

    public void writeToPacket(NBTTagCompound tag);

    public void readFromPacket(NBTTagCompound tag);

    public BlockPos getPosition();

    public void onDescUpdate();
}
