package me.desht.pneumaticredux.common;

import me.desht.pneumaticredux.api.PneumaticRegistry.IPneumaticCraftInterface;
import me.desht.pneumaticredux.api.client.IClientRegistry;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IPneumaticHelmetRegistry;
import me.desht.pneumaticredux.api.drone.IDroneRegistry;
import me.desht.pneumaticredux.api.item.IItemRegistry;
import me.desht.pneumaticredux.api.recipe.IPneumaticRecipeRegistry;
import me.desht.pneumaticredux.api.tileentity.IAirHandlerSupplier;
import me.desht.pneumaticredux.api.tileentity.IHeatRegistry;
import me.desht.pneumaticredux.api.universalSensor.ISensorRegistry;
import me.desht.pneumaticredux.client.GuiRegistry;
import me.desht.pneumaticredux.client.render.pneumaticArmor.PneumaticHelmetRegistry;
import me.desht.pneumaticredux.common.heat.HeatExchangerManager;
import me.desht.pneumaticredux.common.item.ItemRegistry;
import me.desht.pneumaticredux.common.pressure.AirHandlerSupplier;
import me.desht.pneumaticredux.common.recipes.PneumaticRecipeRegistry;
import me.desht.pneumaticredux.common.sensor.SensorHandler;
import me.desht.pneumaticredux.common.util.PneumaticCraftUtils;
import me.desht.pneumaticredux.lib.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

/**
 * With this class you can register your entities to give more info in the tooltip of the Entity Tracker.
 */
public class PneumaticCraftAPIHandler implements IPneumaticCraftInterface {
    private final static PneumaticCraftAPIHandler INSTANCE = new PneumaticCraftAPIHandler();
    public final Map<Fluid, Integer> liquidXPs = new HashMap<>();
    public final Map<String, Integer> liquidFuels = new HashMap<>();

    public static PneumaticCraftAPIHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public IPneumaticRecipeRegistry getRecipeRegistry() {
        return PneumaticRecipeRegistry.getInstance();
    }

    @Override
    public IAirHandlerSupplier getAirHandlerSupplier() {
        return AirHandlerSupplier.getInstance();
    }

    @Override
    public IPneumaticHelmetRegistry getHelmetRegistry() {
        return PneumaticHelmetRegistry.getInstance();
    }

    @Override
    public IDroneRegistry getDroneRegistry() {
        return DroneRegistry.getInstance();
    }

    @Override
    public IHeatRegistry getHeatRegistry() {
        return HeatExchangerManager.getInstance();
    }

    @Override
    public int getProtectingSecurityStations(World world, BlockPos pos, EntityPlayer player, boolean showRangeLines) {
        if (world.isRemote) throw new IllegalArgumentException("This method can only be called from the server side!");
        return PneumaticCraftUtils.getProtectingSecurityStations(world, pos, player, showRangeLines, false);
    }

    @Override
    public void registerXPLiquid(Fluid fluid, int liquidToPointRatio) {
        if (fluid == null) throw new NullPointerException("Fluid can't be null!");
        if (liquidToPointRatio <= 0) throw new IllegalArgumentException("liquidToPointRatio can't be <= 0");
        liquidXPs.put(fluid, liquidToPointRatio);
    }

    @Override
    public void registerFuel(Fluid fluid, int mLPerBucket) {
        if (fluid == null) throw new NullPointerException("Fluid can't be null!");
        if (mLPerBucket < 0) throw new IllegalArgumentException("mLPerBucket can't be < 0");
        if (liquidFuels.containsKey(fluid.getName())) {
            Log.info("Overriding liquid fuel entry " + fluid.getLocalizedName(new FluidStack(fluid, 1)) + " (" + fluid.getName() + ") with a fuel value of " + mLPerBucket + " (previously " + liquidFuels.get(fluid.getName()) + ")");
            if (mLPerBucket == 0) liquidFuels.remove(fluid.getName());
        }
        if (mLPerBucket > 0) liquidFuels.put(fluid.getName(), mLPerBucket);
    }

    @Override
    public IClientRegistry getGuiRegistry() {
        return GuiRegistry.getInstance();
    }

    @Override
    public ISensorRegistry getSensorRegistry() {
        return SensorHandler.getInstance();
    }

    @Override
    public IItemRegistry getItemRegistry() {
        return ItemRegistry.getInstance();
    }
}
