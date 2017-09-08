package me.desht.pneumaticredux.common.heat;

import me.desht.pneumaticredux.api.heat.IHeatExchangerLogic;
import me.desht.pneumaticredux.api.tileentity.IHeatExchanger;
import net.minecraft.util.EnumFacing;

public class SimpleHeatExchanger implements IHeatExchanger {
    private final IHeatExchangerLogic logic;

    public SimpleHeatExchanger(IHeatExchangerLogic logic) {
        this.logic = logic;
    }

    @Override
    public IHeatExchangerLogic getHeatExchangerLogic(EnumFacing side) {
        return logic;
    }

}
