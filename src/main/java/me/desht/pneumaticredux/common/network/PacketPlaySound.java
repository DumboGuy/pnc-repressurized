package me.desht.pneumaticredux.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * MineChess
 *
 * @author MineMaarten
 *         www.minemaarten.com
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class PacketPlaySound extends LocationDoublePacket<PacketPlaySound> {
    private SoundEvent sound;
    private float volume, pitch;
    private boolean bool;

    public PacketPlaySound() {
    }

    public PacketPlaySound(SoundEvent sound, double x, double y, double z, float volume, float pitch, boolean bool) {
        super(x, y, z);
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public PacketPlaySound(SoundEvent sound, BlockPos pos, float volume, float pitch, boolean bool) {
        this(sound, pos.getX(), pos.getY(), pos.getZ(), volume, pitch, bool);
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        ByteBufUtils.writeUTF8String(buffer, sound.getSoundName().toString());
        buffer.writeFloat(volume);
        buffer.writeFloat(pitch);
        buffer.writeBoolean(bool);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        sound = new SoundEvent(new ResourceLocation(ByteBufUtils.readUTF8String(buffer)));
        volume = buffer.readFloat();
        pitch = buffer.readFloat();
        bool = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(PacketPlaySound message, EntityPlayer player) {
        // FIXME include sound category in packet?
        player.world.playSound(message.x, message.y, message.z, message.sound, SoundCategory.AMBIENT, message.volume, message.pitch, message.bool);
    }

    @Override
    public void handleServerSide(PacketPlaySound message, EntityPlayer player) {
    }

}
