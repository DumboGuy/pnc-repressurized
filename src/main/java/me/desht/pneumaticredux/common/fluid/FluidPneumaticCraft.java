package me.desht.pneumaticredux.common.fluid;

import me.desht.pneumaticredux.common.block.BlockFluidPneumaticCraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import static me.desht.pneumaticredux.common.util.PneumaticCraftUtils.RL;

public class FluidPneumaticCraft extends Fluid {

    public FluidPneumaticCraft(String fluidName) {
        this(fluidName, true);
    }

    public FluidPneumaticCraft(String fluidName, boolean registerBlock) {
        super(fluidName, RL("blocks/" + fluidName + "_still"), RL("blocks/" + fluidName + "_flow"));
        FluidRegistry.registerFluid(this);
        if (registerBlock) setBlock(new BlockFluidPneumaticCraft(this));

    }
}
