package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.tileentity.TileEntityUVLightBox;
import me.desht.pneumaticredux.lib.BBConstants;
import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlockUVLightBox extends BlockPneumaticCraftModeled {

    private static final AxisAlignedBB BLOCK_BOUNDS_NS = new AxisAlignedBB(
            BBConstants.UV_LIGHT_BOX_LENGTH_MIN, 0, BBConstants.UV_LIGHT_BOX_WIDTH_MIN,
            1 - BBConstants.UV_LIGHT_BOX_LENGTH_MIN, BBConstants.UV_LIGHT_BOX_TOP_MAX, 1 - BBConstants.UV_LIGHT_BOX_WIDTH_MIN);
    private static final AxisAlignedBB BLOCK_BOUNDS_EW = new AxisAlignedBB(
            BBConstants.UV_LIGHT_BOX_WIDTH_MIN, 0, BBConstants.UV_LIGHT_BOX_LENGTH_MIN,
            1 - BBConstants.UV_LIGHT_BOX_WIDTH_MIN, BBConstants.UV_LIGHT_BOX_TOP_MAX, 1 - BBConstants.UV_LIGHT_BOX_LENGTH_MIN
    );

    BlockUVLightBox() {
        super(Material.IRON, "uv_light_box");

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = getRotation(source, pos);
        return facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? BLOCK_BOUNDS_NS : BLOCK_BOUNDS_EW;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return getBoundingBox(blockState, worldIn, pos);
    }

//    @Override
//    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos) {
//        EnumFacing facing = getRotation(blockAccess, pos);
//        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
//            setBlockBounds(BBConstants.UV_LIGHT_BOX_LENGTH_MIN, 0, BBConstants.UV_LIGHT_BOX_WIDTH_MIN, 1 - BBConstants.UV_LIGHT_BOX_LENGTH_MIN, BBConstants.UV_LIGHT_BOX_TOP_MAX, 1 - BBConstants.UV_LIGHT_BOX_WIDTH_MIN);
//        } else {
//            setBlockBounds(BBConstants.UV_LIGHT_BOX_WIDTH_MIN, 0, BBConstants.UV_LIGHT_BOX_LENGTH_MIN, 1 - BBConstants.UV_LIGHT_BOX_WIDTH_MIN, BBConstants.UV_LIGHT_BOX_TOP_MAX, 1 - BBConstants.UV_LIGHT_BOX_LENGTH_MIN);
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
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityUVLightBox.class;
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.UV_LIGHT_BOX;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block != this) {
            return block.getLightValue(state, world, pos);
        }
        TileEntity te = world.getTileEntity(pos);
        return te instanceof TileEntityUVLightBox ? ((TileEntityUVLightBox) te).getLightLevel() : 0;
    }

    @Override
    public boolean isRotatable() {
        return true;
    }
}
