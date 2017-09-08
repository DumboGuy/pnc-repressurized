package me.desht.pneumaticredux.common.fluid;

public class FluidEtchingAcid extends FluidPneumaticCraft {
    public FluidEtchingAcid(String fluidName) {
        super(fluidName);
    }

    @Override
    public int getColor() {
        return 0xFF501c00;
    }
}
