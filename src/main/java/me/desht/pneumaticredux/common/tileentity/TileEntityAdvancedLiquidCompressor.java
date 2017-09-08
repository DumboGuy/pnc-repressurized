package me.desht.pneumaticredux.common.tileentity;

import me.desht.pneumaticredux.api.PneumaticRegistry;
import me.desht.pneumaticredux.api.heat.IHeatExchangerLogic;
import me.desht.pneumaticredux.api.tileentity.IHeatExchanger;
import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.network.GuiSynced;
import me.desht.pneumaticredux.lib.PneumaticValues;
import net.minecraft.util.EnumFacing;

public class TileEntityAdvancedLiquidCompressor extends TileEntityLiquidCompressor implements IHeatExchanger {

    @GuiSynced
    private final IHeatExchangerLogic heatExchanger = PneumaticRegistry.getInstance().getHeatRegistry().getHeatExchangerLogic();

    public TileEntityAdvancedLiquidCompressor() {
        super(20, 25, 10000);
        heatExchanger.setThermalCapacity(100);
    }

    @Override
    public IHeatExchangerLogic getHeatExchangerLogic(EnumFacing side) {
        return heatExchanger;
    }

    @Override
    protected void onFuelBurn(int burnedFuel) {
        heatExchanger.addHeat(burnedFuel / 20D);
    }

    @Override
    public int getBaseProduction() {
        return PneumaticValues.PRODUCTION_ADVANCED_COMPRESSOR;
    }

    @Override
    public int getEfficiency() {
        return TileEntityAdvancedAirCompressor.getEfficiency(heatExchanger.getTemperature());
    }

    @Override
    public String getName() {
        return Blockss.advancedLiquidCompressor.getUnlocalizedName();
    }
}
