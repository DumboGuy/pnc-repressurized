package me.desht.pneumaticcraft.common.block;

import me.desht.pneumaticcraft.common.tileentity.TileEntityThermopneumaticProcessingPlant;
import me.desht.pneumaticcraft.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockThermopneumaticProcessingPlant extends BlockPneumaticCraftModeled {
    private static final AxisAlignedBB BLOCK_BOUNDS = new AxisAlignedBB(0, 0, 0, 1, 10 / 16F, 1);

    BlockThermopneumaticProcessingPlant() {
        super(Material.IRON, "thermopneumatic_processing_plant");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BLOCK_BOUNDS;
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityThermopneumaticProcessingPlant.class;
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.THERMOPNEUMATIC_PROCESSING_PLANT;
    }

    @Override
    public boolean isRotatable() {
        return true;
    }
}
