package me.desht.pneumaticredux.client.render.pneumaticArmor.blockTracker;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IBlockTrackEntry;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IHackableBlock;
import me.desht.pneumaticredux.client.render.pneumaticArmor.BlockTrackUpgradeHandler;
import me.desht.pneumaticredux.client.render.pneumaticArmor.HUDHandler;
import me.desht.pneumaticredux.client.render.pneumaticArmor.HackUpgradeRenderHandler;
import me.desht.pneumaticredux.client.render.pneumaticArmor.hacking.HackableHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockTrackEntryHackable implements IBlockTrackEntry {

    @Override
    public boolean shouldTrackWithThisEntry(IBlockAccess world, BlockPos pos, IBlockState state, TileEntity te) {
        return HackUpgradeRenderHandler.enabledForPlayer(PneumaticRedux.proxy.getPlayer()) && HackableHandler.getHackableForCoord(world, pos, PneumaticRedux.proxy.getPlayer()) != null;
    }

    @Override
    public boolean shouldBeUpdatedFromServer(TileEntity te) {
        return false;
    }

    @Override
    public int spamThreshold() {
        return 10;
    }

    @Override
    public void addInformation(World world, BlockPos pos, TileEntity te, List<String> infoList) {
        IHackableBlock hackableBlock = HackableHandler.getHackableForCoord(world, pos, PneumaticRedux.proxy.getPlayer());
        int hackTime = HUDHandler.instance().getSpecificRenderer(BlockTrackUpgradeHandler.class).getTargetForCoord(pos).getHackTime();
        if (hackTime == 0) {
            hackableBlock.addInfo(world, pos, infoList, PneumaticRedux.proxy.getPlayer());
        } else {
            int requiredHackTime = hackableBlock.getHackTime(world, pos, PneumaticRedux.proxy.getPlayer());
            int percentageComplete = hackTime * 100 / requiredHackTime;
            if (percentageComplete < 100) {
                infoList.add(I18n.format("pneumaticHelmet.hacking.hacking") + " (" + percentageComplete + "%%)");
            } else if (hackTime < requiredHackTime + 20) {
                hackableBlock.addPostHackInfo(world, pos, infoList, PneumaticRedux.proxy.getPlayer());
            } else {
                hackableBlock.addInfo(world, pos, infoList, PneumaticRedux.proxy.getPlayer());
            }
        }
    }

    @Override
    public String getEntryName() {
        return "blockTracker.module.hackables";
    }

}
