package me.desht.pneumaticredux.common.block.tubes;

import me.desht.pneumaticredux.common.pressure.AirHandler;
import me.desht.pneumaticredux.common.thirdparty.ModInteractionUtils;
import me.desht.pneumaticredux.common.tileentity.TileEntityPressureTube;
import me.desht.pneumaticredux.common.util.TileEntityCache;
import net.minecraft.util.EnumFacing;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ModuleNetworkManager {
    private static ModuleNetworkManager INSTANCE = new ModuleNetworkManager();

    // private final List<Set<TubeModule>> connectedModules = new ArrayList<Set<TubeModule>>(); Not cached due to the performance being ok

    public static ModuleNetworkManager getInstance() {
        return INSTANCE;
    }

    public void addModule(TubeModule module) {
    }

    Set<TubeModule> getConnectedModules(TubeModule module) {
        Set<TubeModule> modules = new HashSet<>();
        Set<TileEntityPressureTube> traversedTubes = new HashSet<>();
        Stack<TileEntityPressureTube> pendingTubes = new Stack<>();
        pendingTubes.push((TileEntityPressureTube) module.getTube());
        while (!pendingTubes.isEmpty()) {
            TileEntityPressureTube tube = pendingTubes.pop();
            for (TubeModule m : tube.modules) {
                if (m != null) modules.add(m);
            }
            TileEntityCache[] cache = ((AirHandler) tube.getAirHandler(null)).getTileCache();
            for (EnumFacing d : EnumFacing.VALUES) {
                if (tube.sidesConnected[d.ordinal()]) {
                    TileEntityPressureTube newTube = ModInteractionUtils.getInstance().getTube(cache[d.ordinal()].getTileEntity());
                    if (newTube != null && !traversedTubes.contains(newTube)) {
                        pendingTubes.add(newTube);
                        traversedTubes.add(newTube);
                    }
                }
            }
        }
        return modules;
    }
}
