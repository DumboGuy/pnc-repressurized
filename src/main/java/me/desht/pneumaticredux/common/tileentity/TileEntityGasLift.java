package me.desht.pneumaticredux.common.tileentity;

import me.desht.pneumaticredux.api.item.IItemRegistry.EnumUpgrade;
import me.desht.pneumaticredux.api.tileentity.IAirHandler;
import me.desht.pneumaticredux.common.ai.ChunkPositionSorter;
import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.network.DescSynced;
import me.desht.pneumaticredux.common.network.GuiSynced;
import me.desht.pneumaticredux.common.util.FluidUtils;
import me.desht.pneumaticredux.common.util.IOHelper;
import me.desht.pneumaticredux.common.util.PneumaticCraftUtils;
import me.desht.pneumaticredux.lib.PneumaticValues;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;

public class TileEntityGasLift extends TileEntityPneumaticBase implements IMinWorkingPressure, IRedstoneControlled {
    private static final int INVENTORY_SIZE = 1;

    @GuiSynced
    private final FluidTank tank = new FluidTank(PneumaticValues.NORMAL_TANK_CAPACITY);
    private ItemStackHandler inventory = new FilteredItemStackHandler(INVENTORY_SIZE) {
        @Override
        public boolean test(Integer integer, ItemStack itemStack) {
            return itemStack.isItemEqual(new ItemStack(Blockss.PRESSURE_TUBE));
        }
    };
    @GuiSynced
    public int currentDepth;
    @GuiSynced
    public int redstoneMode, status, mode;
    @DescSynced
    public boolean[] sidesConnected = new boolean[6];
    private int workTimer;
    private int ticker;
    private List<BlockPos> pumpingLake;
    private static final int MAX_PUMP_RANGE = 15;

    public TileEntityGasLift() {
        super(5, 7, 3000, 4);
        addApplicableUpgrade(EnumUpgrade.SPEED);
    }

    @Override
    public boolean isConnectedTo(EnumFacing d) {
        return d != EnumFacing.DOWN;
    }

    @Override
    public void onNeighborTileUpdate() {
        super.onNeighborTileUpdate();
        List<Pair<EnumFacing, IAirHandler>> connections = getAirHandler(null).getConnectedPneumatics();
        Arrays.fill(sidesConnected, false);
        for (Pair<EnumFacing, IAirHandler> entry : connections) {
            sidesConnected[entry.getKey().ordinal()] = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            ticker++;
            if (currentDepth > 0) {
                int curCheckingPipe = ticker % currentDepth;
                if (curCheckingPipe > 0 && !isPipe(getPos().offset(EnumFacing.DOWN, curCheckingPipe))) {
                    currentDepth = curCheckingPipe - 1;
                }
            }
            if (ticker % 400 == 0) pumpingLake = null;

            if (redstoneAllows() && getPressure() >= getMinWorkingPressure()) {
                workTimer += this.getSpeedMultiplierFromUpgrades();
                while (workTimer > 20) {
                    workTimer -= 20;
                    status = 0;
                    if (mode == 2) {
                        if (currentDepth > 0) {
                            status = 3;
                            if (isPipe(getPos().add(0, -currentDepth, 0))) {
                                if (IOHelper.insert(this, new ItemStack(Blockss.PRESSURE_TUBE), null, false).isEmpty()) {
                                    world.destroyBlock(getPos().offset(EnumFacing.DOWN, currentDepth), false);
                                    addAir(-100);
                                    currentDepth--;
                                } else {
                                    status = 0;
                                }
                            } else {
                                currentDepth--;
                            }
                        }
                    } else {
                        if (!suckLiquid()) {
                            if (getPos().getY() - currentDepth >= 0 && !isUnbreakable(getPos().offset(EnumFacing.DOWN, currentDepth))) {
                                status = 2;
                                currentDepth++;
                                BlockPos pos1 = getPos().offset(EnumFacing.DOWN, currentDepth);
                                if (!isPipe(pos1)) {
                                    if (!inventory.getStackInSlot(0).isEmpty()) {
                                        inventory.extractItem(0, 1, false);
                                        world.destroyBlock(pos1, false);
                                        world.setBlockState(pos1, Blockss.PRESSURE_TUBE.getDefaultState());
                                        addAir(-100);
                                    } else {
                                        status = 0;
                                        currentDepth--;
                                        break;
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            } else {
                status = 0;
            }
            if (getUpgrades(EnumUpgrade.DISPENSER) > 0) {
                autoExportLiquid();
            }
        }
    }

    private boolean isPipe(BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blockss.PRESSURE_TUBE;
    }

    private boolean isUnbreakable(BlockPos pos) {
        return world().getBlockState(pos).getBlockHardness(world, pos) < 0;
    }

    private boolean suckLiquid() {
        Block block = world.getBlockState(getPos().offset(EnumFacing.DOWN)).getBlock();
//        Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
        IFluidHandler worldFluidHandler = FluidUtil.getFluidHandler(world, getPos().offset(EnumFacing.DOWN), EnumFacing.UP);
        if (worldFluidHandler != null) {
            FluidStack fluidStack = FluidUtil.tryFluidTransfer(tank, worldFluidHandler, 1000, false);
            if (fluidStack != null && fluidStack.amount == 1000) {
                if (pumpingLake == null) {
                    pumpingLake = new ArrayList<>();
                    Stack<BlockPos> pendingPositions = new Stack<>();
                    BlockPos thisPos = getPos().offset(EnumFacing.DOWN, currentDepth + 1);// new BlockPos(getPos().getX(), getPos().getY() - currentDepth - 1, getPos().getZ());
                    pendingPositions.add(thisPos);
                    pumpingLake.add(thisPos);
                    while (!pendingPositions.empty()) {
                        BlockPos checkingPos = pendingPositions.pop();
                        for (EnumFacing d : EnumFacing.VALUES) {
                            if (d == EnumFacing.DOWN) continue;
                            BlockPos newPos = checkingPos.offset(d); // new BlockPos(checkingPos.getX() + d.getFrontOffsetX(), checkingPos.getY() + d.getFrontOffsetY(), checkingPos.getZ() + d.getFrontOffsetZ());
                            if (PneumaticCraftUtils.distBetween(newPos, getPos().getX() + 0.5, getPos().getY() - currentDepth - 1, getPos().getZ() + 0.5) <= MAX_PUMP_RANGE && getWorld().getBlockState(newPos).getBlock() == block && !pumpingLake.contains(newPos)) {
                                pendingPositions.add(newPos);
                                pumpingLake.add(newPos);
                            }
                        }
                    }
                    pumpingLake.sort(new ChunkPositionSorter(getPos().getX() + 0.5, getPos().getY() - currentDepth - 1, getPos().getZ() + 0.5));
                    Collections.reverse(pumpingLake);
                }
                BlockPos curPos = null;
                boolean foundSource = false;
                while (pumpingLake.size() > 0) {
                    curPos = pumpingLake.get(0);
                    if (getWorld().getBlockState(curPos).getBlock() == block && FluidUtils.isSourceBlock(getWorld(), curPos)) {
                        foundSource = true;
                        break;
                    }
                    pumpingLake.remove(0);
                }
                if (pumpingLake.size() == 0) {
                    pumpingLake = null;
                } else if (foundSource) {
                    getWorld().setBlockToAir(curPos);
                    FluidUtil.tryFluidTransfer(tank, worldFluidHandler, 1000, true);
                    addAir(-100);
                    status = 1;
                }
            }
            return true;
        } else {
            pumpingLake = null;
            return false;
        }
    }

    @Override
    public void handleGUIButtonPress(int buttonID, EntityPlayer player) {
        if (buttonID == 0) {
            redstoneMode++;
            if (redstoneMode > 2) redstoneMode = 0;
        } else if (buttonID > 0 && buttonID < 4) {
            mode = buttonID - 1;
        }
    }

    @Override
    public int getRedstoneMode() {
        return redstoneMode;
    }

    @Override
    public float getMinWorkingPressure() {
        return 0.5F + currentDepth * 0.05F;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("Items", inventory.serializeNBT());
        tag.setByte("redstoneMode", (byte) redstoneMode);
        tag.setByte("mode", (byte) mode);

        NBTTagCompound tankTag = new NBTTagCompound();
        tank.writeToNBT(tankTag);
        tag.setTag("tank", tankTag);
        tag.setInteger("currentDepth", currentDepth);

        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag.getCompoundTag("Items"));
        redstoneMode = tag.getByte("redstoneMode");
        mode = tag.getByte("mode");
        tank.readFromNBT(tag.getCompoundTag("tank"));
        currentDepth = tag.getInteger("currentDepth");
    }

    @Override
    public IItemHandlerModifiable getPrimaryInventory() {
        return inventory;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        } else {
            return super.getCapability(capability, facing);
        }
    }

    @SideOnly(Side.CLIENT)
    public FluidTank getTank() {
        return tank;
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getName() {
        return Blockss.GAS_LIFT.getUnlocalizedName();
    }


}
