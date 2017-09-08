package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.tileentity.TileEntityPlasticMixer;
import me.desht.pneumaticredux.lib.BBConstants;
import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlockPlasticMixer extends BlockPneumaticCraftModeled {

    private static final AxisAlignedBB BLOCK_BOUNDS = new AxisAlignedBB(
            BBConstants.PLASTIC_MIXER_MIN_POS, 0F, BBConstants.PLASTIC_MIXER_MIN_POS,
            BBConstants.PLASTIC_MIXER_MAX_POS, 1, BBConstants.PLASTIC_MIXER_MAX_POS
    );
    private static final AxisAlignedBB COLLISION_BOUNDS = new AxisAlignedBB(
            BBConstants.PLASTIC_MIXER_MIN_POS, 0F, BBConstants.PLASTIC_MIXER_MIN_POS,
            BBConstants.PLASTIC_MIXER_MAX_POS, 1, BBConstants.PLASTIC_MIXER_MAX_POS
    );

    BlockPlasticMixer() {
        super(Material.IRON, "plastic_mixer");
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityPlasticMixer.class;
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.PLASTIC_MIXER;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_BOUNDS;
    }

//    @Override
//    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, BlockPos pos) {
//        setBlockBounds(BBConstants.PLASTIC_MIXER_MIN_POS, 0F, BBConstants.PLASTIC_MIXER_MIN_POS, BBConstants.PLASTIC_MIXER_MAX_POS, 1, BBConstants.PLASTIC_MIXER_MAX_POS);
//    }
//
//    @Override
//    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
//        setBlockBounds(BBConstants.PLASTIC_MIXER_MIN_POS, 0F, BBConstants.PLASTIC_MIXER_MIN_POS, BBConstants.PLASTIC_MIXER_MAX_POS, 1, BBConstants.PLASTIC_MIXER_MAX_POS);
//        super.addCollisionBoxesToList(world, pos, state, axisalignedbb, arraylist, par7Entity);
//        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//    }
}
