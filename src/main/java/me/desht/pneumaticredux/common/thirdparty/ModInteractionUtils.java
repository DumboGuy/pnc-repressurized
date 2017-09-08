package me.desht.pneumaticredux.common.thirdparty;

import me.desht.pneumaticredux.api.block.IPneumaticWrenchable;
import me.desht.pneumaticredux.api.tileentity.IPneumaticMachine;
import me.desht.pneumaticredux.common.block.tubes.IPneumaticPosProvider;
import me.desht.pneumaticredux.common.item.ItemTubeModule;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.tileentity.TileEntityPressureTube;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class ModInteractionUtils {
    private static final ModInteractionUtils INSTANCE = new ModInteractionUtilImplementation();

    public static ModInteractionUtils getInstance() {
        return INSTANCE;
    }

    public boolean isModdedWrench(Item item) {
        return item != Itemss.PNEUMATIC_WRENCH && (isBCWrench(item) || isTEWrench(item));
    }

    protected boolean isBCWrench(Item item) {
        return false;
    }

    protected boolean isTEWrench(Item item) {
        return false;
    }

    /**
     * Used to get a IPneumaticMachine if the TE is one. When FMP is installed this will also look for a multipart containing IPneumaticMachine.
     *
     * @param te
     * @return
     */
    public IPneumaticMachine getMachine(TileEntity te) {
        return te instanceof IPneumaticMachine ? (IPneumaticMachine) te : null;
    }

    public IPneumaticWrenchable getWrenchable(TileEntity te) {
        return null;
    }

    public Item getModuleItem(String moduleName) {
        return new ItemTubeModule(moduleName);
    }

    public void registerModulePart(String partName) {
    }

    public boolean isMultipart(TileEntity te) {
        return false;
    }

    public ItemStack exportStackToTEPipe(TileEntity te, ItemStack stack, EnumFacing side) {
        return stack;
    }

    public ItemStack exportStackToBCPipe(TileEntity te, ItemStack stack, EnumFacing side) {
        return stack;
    }

    public boolean isBCPipe(TileEntity te) {
        return false;
    }

    public boolean isTEPipe(TileEntity te) {
        return false;
    }

    public boolean isMultipartWiseConnected(Object part, EnumFacing dir) {
        return false;
    }

    /**
     * @param potentialTube Either a TileMultipart, PartPressureTube or TileEntityPressureTube
     * @return
     */
    public TileEntityPressureTube getTube(Object potentialTube) {
        return potentialTube instanceof TileEntityPressureTube ? (TileEntityPressureTube) potentialTube : null;
    }

    public void sendDescriptionPacket(IPneumaticPosProvider te) {
        ((TileEntityPressureTube) te).sendDescriptionPacket();
    }

    public void removeTube(TileEntity te) {
        te.getWorld().setBlockToAir(te.getPos());
    }

    public boolean occlusionTest(AxisAlignedBB aabb, TileEntity te) {
        return true;
    }
}