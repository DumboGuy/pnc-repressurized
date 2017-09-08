package me.desht.pneumaticredux.common.network;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IHackableBlock;
import me.desht.pneumaticredux.client.render.pneumaticArmor.hacking.HackableHandler;
import me.desht.pneumaticredux.common.CommonHUDHandler;
import me.desht.pneumaticredux.common.util.WorldAndCoord;
import me.desht.pneumaticredux.lib.Sounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PacketHackingBlockFinish extends LocationIntPacket<PacketHackingBlockFinish> {

    public PacketHackingBlockFinish() {
    }

    public PacketHackingBlockFinish(BlockPos pos) {
        super(pos);
    }

    public PacketHackingBlockFinish(WorldAndCoord coord) {
        super(coord.pos);
    }

    @Override
    public void handleClientSide(PacketHackingBlockFinish message, EntityPlayer player) {
        IHackableBlock hackableBlock = HackableHandler.getHackableForCoord(player.world, message.pos, player);
        if (hackableBlock != null) {
            hackableBlock.onHackFinished(player.world, message.pos, player);
            PneumaticRedux.proxy.getHackTickHandler().trackBlock(new WorldAndCoord(player.world, message.pos), hackableBlock);
            CommonHUDHandler.getHandlerForPlayer(player).setHackedBlock(null);
            player.playSound(Sounds.HELMET_HACK_FINISH, 1.0F, 1.0F);
        }
    }

    @Override
    public void handleServerSide(PacketHackingBlockFinish message, EntityPlayer player) {
    }

}
