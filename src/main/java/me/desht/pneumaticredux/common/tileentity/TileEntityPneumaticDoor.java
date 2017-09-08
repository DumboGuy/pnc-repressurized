package me.desht.pneumaticredux.common.tileentity;

import me.desht.pneumaticredux.common.block.BlockPneumaticDoor;
import me.desht.pneumaticredux.common.network.DescSynced;
import me.desht.pneumaticredux.common.network.LazySynced;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPneumaticDoor extends TileEntityBase {
    @DescSynced
    @LazySynced
    public float rotation;
    public float oldRotation;
    @DescSynced
    public boolean rightGoing;

    public void setRotation(float rotation) {
        oldRotation = this.rotation;
        this.rotation = rotation;
        TileEntity te;
        if (isTopDoor()) {
            te = getWorld().getTileEntity(getPos().offset(EnumFacing.DOWN));
        } else {
            te = getWorld().getTileEntity(getPos().offset(EnumFacing.UP));
        }
        if (te instanceof TileEntityPneumaticDoor) {
            TileEntityPneumaticDoor door = (TileEntityPneumaticDoor) te;
            door.rightGoing = rightGoing;
            if (rotation != door.rotation) {
                door.setRotation(rotation);
                //door.rotation = rotation;
                // door.oldRotation = oldRotation;
            }
        }
    }

    public boolean isTopDoor() {
        return BlockPneumaticDoor.isTopDoor(getWorld().getBlockState(getPos()));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("rightGoing", rightGoing);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        rightGoing = tag.getBoolean("rightGoing");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 2, getPos().getZ() + 1);
    }
}
