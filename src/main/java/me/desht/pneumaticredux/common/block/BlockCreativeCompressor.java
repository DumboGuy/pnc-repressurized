package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.tileentity.TileEntityCreativeCompressor;
import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockCreativeCompressor extends BlockPneumaticCraftModeled {

    BlockCreativeCompressor() {
        super(Material.IRON, "creative_compressor");
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityCreativeCompressor.class;
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.CREATIVE_COMPRESSOR;
    }

}
