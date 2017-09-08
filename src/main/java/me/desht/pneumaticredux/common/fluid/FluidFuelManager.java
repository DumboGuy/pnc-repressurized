package me.desht.pneumaticredux.common.fluid;

import me.desht.pneumaticredux.api.PneumaticRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidFuelManager {
    public static void registerFuels() {
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid.getTemperature() > 305) {
                PneumaticRegistry.getInstance().registerFuel(fluid, (fluid.getTemperature() - 295) * 40);
            }
        }
    }
}
