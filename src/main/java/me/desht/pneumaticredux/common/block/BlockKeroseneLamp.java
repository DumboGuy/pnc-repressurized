package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.tileentity.TileEntityKeroseneLamp;
import me.desht.pneumaticredux.lib.BBConstants;
import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockKeroseneLamp extends BlockPneumaticCraftModeled {
    private static final AxisAlignedBB BLOCK_BOUNDS_NS = new AxisAlignedBB(
            BBConstants.KEROSENE_LAMP_LENGTH_MIN, 0, BBConstants.KEROSENE_LAMP_WIDTH_MIN,
            1 - BBConstants.KEROSENE_LAMP_LENGTH_MIN, BBConstants.KEROSENE_LAMP_TOP_MAX, 1 - BBConstants.KEROSENE_LAMP_WIDTH_MIN
    );
    private static final AxisAlignedBB BLOCK_BOUNDS_EW = new AxisAlignedBB(
            BBConstants.KEROSENE_LAMP_WIDTH_MIN, 0, BBConstants.KEROSENE_LAMP_LENGTH_MIN,
            1 - BBConstants.KEROSENE_LAMP_WIDTH_MIN, BBConstants.KEROSENE_LAMP_TOP_MAX, 1 - BBConstants.KEROSENE_LAMP_LENGTH_MIN
    );

    BlockKeroseneLamp() {
        super(Material.IRON, "kerosene_lamp");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = getRotation(source, pos);
        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
            return BLOCK_BOUNDS_NS;
        } else {
            return BLOCK_BOUNDS_EW;
        }
    }

//    @Override
//    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos) {
//        EnumFacing facing = getRotation(blockAccess, pos);
//        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
//            setBlockBounds(BBConstants.KEROSENE_LAMP_LENGTH_MIN, 0, BBConstants.KEROSENE_LAMP_WIDTH_MIN, 1 - BBConstants.KEROSENE_LAMP_LENGTH_MIN, BBConstants.KEROSENE_LAMP_TOP_MAX, 1 - BBConstants.KEROSENE_LAMP_WIDTH_MIN);
//        } else {
//            setBlockBounds(BBConstants.KEROSENE_LAMP_WIDTH_MIN, 0, BBConstants.KEROSENE_LAMP_LENGTH_MIN, 1 - BBConstants.KEROSENE_LAMP_WIDTH_MIN, BBConstants.KEROSENE_LAMP_TOP_MAX, 1 - BBConstants.KEROSENE_LAMP_LENGTH_MIN);
//        }
//    }
//
//    @Override
//    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
//        setBlockBoundsBasedOnState(world, pos);
//        super.addCollisionBoxesToList(world, pos, state, axisalignedbb, arraylist, par7Entity);
//        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.KEROSENE_LAMP;
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityKeroseneLamp.class;
    }

    @Override
    public boolean isRotatable() {
        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityKeroseneLamp lamp = (TileEntityKeroseneLamp) world.getTileEntity(pos);
        return lamp != null && lamp.getRange() > 0 ? 15 : 0;
    }

}
