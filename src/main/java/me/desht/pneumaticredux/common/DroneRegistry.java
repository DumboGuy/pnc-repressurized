package me.desht.pneumaticredux.common;

import me.desht.pneumaticredux.api.drone.ICustomBlockInteract;
import me.desht.pneumaticredux.api.drone.IDroneRegistry;
import me.desht.pneumaticredux.api.drone.IPathfindHandler;
import me.desht.pneumaticredux.common.progwidgets.ProgWidgetCustomBlockInteract;
import me.desht.pneumaticredux.common.progwidgets.WidgetRegistrator;
import me.desht.pneumaticredux.common.util.ProgrammedDroneUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class DroneRegistry implements IDroneRegistry {
    private static final DroneRegistry INSTANCE = new DroneRegistry();
    public final Map<Block, IPathfindHandler> pathfindableBlocks = new HashMap<Block, IPathfindHandler>();

    public static DroneRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void addPathfindableBlock(Block block, IPathfindHandler handler) {
        if (block == null) throw new IllegalArgumentException("Block can't be null!");
        pathfindableBlocks.put(block, handler);
    }

    @Override
    public void registerCustomBlockInteractor(ICustomBlockInteract interactor) {
        WidgetRegistrator.register(new ProgWidgetCustomBlockInteract().setInteractor(interactor));
    }

    @Override
    public EntityCreature deliverItemsAmazonStyle(World world, BlockPos pos, ItemStack... deliveredStacks) {
        return ProgrammedDroneUtils.deliverItemsAmazonStyle(world, pos, deliveredStacks);
    }

    @Override
    public EntityCreature retrieveItemsAmazonStyle(World world, BlockPos pos, ItemStack... queriedStacks) {
        return ProgrammedDroneUtils.retrieveItemsAmazonStyle(world, pos, queriedStacks);
    }

    @Override
    public EntityCreature deliverFluidAmazonStyle(World world, BlockPos pos, FluidStack deliveredFluid) {
        return ProgrammedDroneUtils.deliverFluidAmazonStyle(world, pos, deliveredFluid);
    }

    @Override
    public EntityCreature retrieveFluidAmazonStyle(World world, BlockPos pos, FluidStack queriedFluid) {
        return ProgrammedDroneUtils.retrieveFluidAmazonStyle(world, pos, queriedFluid);
    }
}
