package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.tileentity.TileEntityAdvancedLiquidCompressor;
import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockAdvancedLiquidCompressor extends BlockPneumaticCraftModeled {

    BlockAdvancedLiquidCompressor() {
        super(Material.IRON, "advanced_liquid_compressor");
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityAdvancedLiquidCompressor.class;
    }

    @Override
    public boolean isRotatable() {
        return true;
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.ADVANCED_LIQUID_COMPRESSOR;
    }

}
