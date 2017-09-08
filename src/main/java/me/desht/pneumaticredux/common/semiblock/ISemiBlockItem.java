package me.desht.pneumaticredux.common.semiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISemiBlockItem {
    public ISemiBlock getSemiBlock(World world, BlockPos pos, ItemStack stack);
}
