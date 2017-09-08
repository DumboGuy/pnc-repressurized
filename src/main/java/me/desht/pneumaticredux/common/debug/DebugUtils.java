package me.desht.pneumaticredux.common.debug;

import me.desht.pneumaticredux.lib.Log;
import net.minecraft.nbt.NBTTagCompound;

public class DebugUtils {
    public static void printNBT(NBTTagCompound tag) {
        Log.info(tag.toString());
    }
}
