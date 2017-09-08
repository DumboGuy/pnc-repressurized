package me.desht.pneumaticredux.common.block;

import me.desht.pneumaticredux.common.DamageSourcePneumaticCraft;
import me.desht.pneumaticredux.common.fluid.Fluids;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFluidEtchingAcid extends BlockFluidPneumaticCraft {

    public BlockFluidEtchingAcid() {
        super(Fluids.ETCHING_ACID, new MaterialLiquid(MapColor.WATER) {
            @Override
            public EnumPushReaction getMobilityFlag() {
                return EnumPushReaction.DESTROY;
            }
        });
        setUnlocalizedName("etchingAcid");
    }

//    @Override
//    //TODO 1.8 test verify renderpass
//    public int colorMultiplier(IBlockAccess iblockaccess, BlockPos pos, int renderPass) {
//        return 0x501c00;
//    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityLivingBase && entity.ticksExisted % 10 == 0) {
            entity.attackEntityFrom(DamageSourcePneumaticCraft.ETCHING_ACID, 1);
        }
    }

}
