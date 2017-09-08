package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.tileentity.TileEntityAdvancedAirCompressor;
import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;
import net.minecraft.tileentity.TileEntity;

public class BlockAdvancedAirCompressor extends BlockAirCompressor {

    public BlockAdvancedAirCompressor() {
        super("advanced_air_compressor");
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.ADVANCED_AIR_COMPRESSOR;
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityAdvancedAirCompressor.class;
    }

}
