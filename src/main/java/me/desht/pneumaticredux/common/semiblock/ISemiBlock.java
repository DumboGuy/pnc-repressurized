package me.desht.pneumaticredux.common.semiblock;

import me.desht.pneumaticredux.common.network.PacketDescription;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISemiBlock {

    public World getWorld();

    public BlockPos getPos();

    public void writeToNBT(NBTTagCompound tag);

    public void readFromNBT(NBTTagCompound tag);

    public void update();

    public void initialize(World world, BlockPos pos);

    public void invalidate();

    public boolean isInvalid();

    public void addDrops(NonNullList<ItemStack> drops);

    public boolean canPlace();

    public void onPlaced(EntityPlayer player, ItemStack stack);

    public boolean onRightClickWithConfigurator(EntityPlayer player);

    public PacketDescription getDescriptionPacket();
}
