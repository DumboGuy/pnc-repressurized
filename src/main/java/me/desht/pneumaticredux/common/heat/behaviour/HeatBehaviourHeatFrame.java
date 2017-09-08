package me.desht.pneumaticredux.common.heat.behaviour;

import me.desht.pneumaticredux.api.heat.HeatBehaviour;
import me.desht.pneumaticredux.api.heat.IHeatExchangerLogic;
import me.desht.pneumaticredux.common.heat.HeatExchangerLogic;
import me.desht.pneumaticredux.common.semiblock.ISemiBlock;
import me.desht.pneumaticredux.common.semiblock.SemiBlockHeatFrame;
import me.desht.pneumaticredux.common.semiblock.SemiBlockManager;
import me.desht.pneumaticredux.lib.Names;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HeatBehaviourHeatFrame extends HeatBehaviour {
    private ISemiBlock semiBlock;

    @Override
    public void initialize(IHeatExchangerLogic connectedHeatLogic, World world, BlockPos pos) {
        super.initialize(connectedHeatLogic, world, pos);
        semiBlock = null;
    }

    @Override
    public String getId() {
        return Names.MOD_ID + ":heatFrame";
    }

    private ISemiBlock getSemiBlock() {
        if (semiBlock == null) {
            semiBlock = SemiBlockManager.getInstance(getWorld()).getSemiBlock(getWorld(), getPos());
        }
        return semiBlock;
    }

    @Override
    public boolean isApplicable() {
        return getSemiBlock() instanceof SemiBlockHeatFrame;
    }

    @Override
    public void update() {
        SemiBlockHeatFrame frame = (SemiBlockHeatFrame) getSemiBlock();
        HeatExchangerLogic.exchange(frame.getHeatExchangerLogic(null), getHeatExchanger());
    }

}
