package me.desht.pneumaticcraft.common.block;

import me.desht.pneumaticcraft.common.tileentity.TileEntityAssemblyController;
import me.desht.pneumaticcraft.lib.BBConstants;
import me.desht.pneumaticcraft.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlockAssemblyController extends BlockPneumaticCraftModeled {

    private static final AxisAlignedBB BLOCK_BOUNDS = new AxisAlignedBB(
            BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, 0F, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS,
            BBConstants.ASSEMBLY_CONTROLLER_MAX_POS, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS_TOP, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS
    );
    private static final AxisAlignedBB COLLISION_BOUNDS = new AxisAlignedBB(
            BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS,
            BBConstants.ASSEMBLY_CONTROLLER_MAX_POS, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS_TOP, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS
    );

    BlockAssemblyController() {
        super(Material.IRON, "assembly_controller");
        setBlockBounds(BLOCK_BOUNDS);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_BOUNDS;
    }

//    @Override
//    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, BlockPos pos) {
//        setBlockBounds(BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, 0F, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS_TOP, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS);
//    }
//
//    @Override
//    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
//        setBlockBounds(BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MIN_POS, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS_TOP, BBConstants.ASSEMBLY_CONTROLLER_MAX_POS);
//        super.addCollisionBoxesToList(world, pos, state, axisalignedbb, arraylist, par7Entity);
//        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityAssemblyController.class;
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.ASSEMBLY_CONTROLLER;
    }
}
