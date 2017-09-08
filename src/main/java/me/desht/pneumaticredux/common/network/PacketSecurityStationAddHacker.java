package me.desht.pneumaticredux.common.network;

import com.mojang.authlib.GameProfile;
import me.desht.pneumaticredux.common.tileentity.TileEntitySecurityStation;
import net.minecraft.tileentity.TileEntity;

public class PacketSecurityStationAddHacker extends PacketSecurityStation<PacketSecurityStationAddHacker> {

    public PacketSecurityStationAddHacker() {
    }

    public PacketSecurityStationAddHacker(TileEntity te, String username) {
        super(te, username);
    }

    @Override
    protected void handleServerSide(TileEntity te, String profile) {
        if (te instanceof TileEntitySecurityStation) {
            ((TileEntitySecurityStation) te).addHacker(new GameProfile(null, profile));
        }
    }
}
