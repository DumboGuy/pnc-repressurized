package me.desht.pneumaticredux.api.tileentity;

import me.desht.pneumaticredux.api.heat.HeatBehaviour;
import me.desht.pneumaticredux.api.heat.IHeatExchangerLogic;
import net.minecraft.block.Block;

public interface IHeatRegistry {
    public IHeatExchangerLogic getHeatExchangerLogic();

    public void registerBlockExchanger(Block block, double temperature, double thermalResistance);

    public void registerHeatBehaviour(Class<? extends HeatBehaviour> heatBehaviour);
}
