package me.desht.pneumaticredux.common.block.tubes;

import me.desht.pneumaticredux.api.tileentity.IPneumaticMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPneumaticPosProvider extends IPneumaticMachine {
    public World world();

    public BlockPos pos();
}
