package me.desht.pneumaticredux.common.fluid;

import net.minecraftforge.fluids.Fluid;

import static me.desht.pneumaticredux.common.util.PneumaticCraftUtils.RL;

public class FluidPneumaticCraft extends Fluid {
    public FluidPneumaticCraft(String fluidName) {
        super(fluidName, RL("blocks/" + fluidName + "_still"), RL("blocks/" + fluidName + "_flow"));
    }
}
