package me.desht.pneumaticcraft.api.drone;

import me.desht.pneumaticcraft.api.item.IPressurizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

//import net.minecraftforge.common.IExtendedEntityProperties;

public interface IDrone extends IPressurizable, ICapabilityProvider {
    /**
     * @return amount of inserted upgrades in the drone
     */
    public int getUpgrades(Item upgrade);

    public World world();

    public IFluidTank getTank();

//    public IInventory getInv();
    public IItemHandlerModifiable getInv();

    public Vec3d getDronePos();

    public IPathNavigator getPathNavigator();

    public void sendWireframeToClient(BlockPos pos);

    public EntityPlayerMP getFakePlayer();

    public boolean isBlockValidPathfindBlock(BlockPos pos);

    public void dropItem(ItemStack stack);

    public void setDugBlock(BlockPos pos);

    public EntityAITasks getTargetAI();

//    public IExtendedEntityProperties getProperty(String key);
//
//    public void setProperty(String key, IExtendedEntityProperties property);

    public void setEmittingRedstone(EnumFacing orientation, int emittingRedstone);

    public void setName(String string);

    public void setCarryingEntity(Entity entity);

    public List<Entity> getCarryingEntities();
//    public Entity getCarryingEntity();

    public boolean isAIOverriden();

    public void onItemPickupEvent(EntityItem curPickingUpEntity, int stackSize);
}
