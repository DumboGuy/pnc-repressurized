package me.desht.pneumaticredux.common.network;

import io.netty.buffer.ByteBuf;
import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IHackableEntity;
import me.desht.pneumaticredux.client.render.pneumaticArmor.hacking.HackableHandler;
import me.desht.pneumaticredux.common.CommonHUDHandler;
import me.desht.pneumaticredux.lib.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketHackingEntityFinish extends AbstractPacket<PacketHackingEntityFinish> {
    private int entityId;

    public PacketHackingEntityFinish() {
    }

    public PacketHackingEntityFinish(Entity entity) {
        entityId = entity.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public void handleClientSide(PacketHackingEntityFinish message, EntityPlayer player) {
        Entity entity = player.world.getEntityByID(message.entityId);
        if (entity != null) {
            IHackableEntity hackableEntity = HackableHandler.getHackableForEntity(entity, player);
            if (hackableEntity != null) {
                hackableEntity.onHackFinished(entity, player);
                PneumaticRedux.proxy.getHackTickHandler().trackEntity(entity, hackableEntity);
                CommonHUDHandler.getHandlerForPlayer(player).setHackedEntity(null);
                player.playSound(Sounds.HELMET_HACK_FINISH, 1.0F, 1.0F);
            }
        }

    }

    @Override
    public void handleServerSide(PacketHackingEntityFinish message, EntityPlayer player) {
    }

}
