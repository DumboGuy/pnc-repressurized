package me.desht.pneumaticredux.common.pressure;

import me.desht.pneumaticredux.api.tileentity.IAirHandler;
import me.desht.pneumaticredux.api.tileentity.IAirHandlerSupplier;
import me.desht.pneumaticredux.lib.PneumaticValues;

public class AirHandlerSupplier implements IAirHandlerSupplier {
    private static final AirHandlerSupplier INSTANCE = new AirHandlerSupplier();

    public static AirHandlerSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public IAirHandler createTierOneAirHandler(int volume) {
        return createAirHandler(PneumaticValues.DANGER_PRESSURE_TIER_ONE, PneumaticValues.MAX_PRESSURE_TIER_ONE, volume);
    }

    @Override
    public IAirHandler createTierTwoAirHandler(int volume) {
        return createAirHandler(PneumaticValues.DANGER_PRESSURE_TIER_TWO, PneumaticValues.MAX_PRESSURE_TIER_TWO, volume);
    }

    @Override
    public IAirHandler createAirHandler(float dangerPressure, float criticalPressure, int volume) {
        return new AirHandler(dangerPressure, criticalPressure, volume);
    }
}
