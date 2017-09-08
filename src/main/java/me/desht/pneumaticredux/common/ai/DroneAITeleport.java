package me.desht.pneumaticredux.common.ai;

import me.desht.pneumaticredux.common.entity.living.EntityDrone;
import me.desht.pneumaticredux.common.progwidgets.ProgWidget;

public class DroneAITeleport extends DroneEntityAIGoToLocation {

    public DroneAITeleport(EntityDrone drone, ProgWidget gotoWidget) {
        super(drone, gotoWidget);
    }

    @Override
    public boolean shouldExecute() {
        EntityPathNavigateDrone navigator = (EntityPathNavigateDrone) drone.getPathNavigator();
        navigator.setForceTeleport(true);
        boolean result = super.shouldExecute();
        navigator.setForceTeleport(false);
        return result;
    }
}
