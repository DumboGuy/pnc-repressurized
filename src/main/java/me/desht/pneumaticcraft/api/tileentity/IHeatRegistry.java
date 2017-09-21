package me.desht.pneumaticcraft.api.tileentity;

import me.desht.pneumaticcraft.api.heat.HeatBehaviour;
import me.desht.pneumaticcraft.api.heat.IHeatExchangerLogic;
import net.minecraft.block.Block;

public interface IHeatRegistry {
    public IHeatExchangerLogic getHeatExchangerLogic();

    public void registerBlockExchanger(Block block, double temperature, double thermalResistance);

    public void registerHeatBehaviour(Class<? extends HeatBehaviour> heatBehaviour);
}
