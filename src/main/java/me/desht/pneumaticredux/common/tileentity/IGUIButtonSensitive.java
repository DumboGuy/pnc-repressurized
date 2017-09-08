package me.desht.pneumaticredux.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;

public interface IGUIButtonSensitive {
    public void handleGUIButtonPress(int guiID, EntityPlayer player);
}