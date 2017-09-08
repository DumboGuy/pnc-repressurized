package me.desht.pneumaticredux.common.item;

import me.desht.pneumaticredux.proxy.CommonProxy.EnumGuiId;

public interface IChargingStationGUIHolderItem {

    /**
     * Should return the GuiHandler's ID for this item.
     *
     * @return
     */
    public EnumGuiId getGuiID();

}
