package me.desht.pneumaticredux.client.gui;

import me.desht.pneumaticredux.api.client.pneumaticHelmet.IUpgradeRenderHandler;
import me.desht.pneumaticredux.api.item.IItemRegistry.EnumUpgrade;
import me.desht.pneumaticredux.api.item.IPressurizable;
import me.desht.pneumaticredux.client.gui.widget.GuiAnimatedStat;
import me.desht.pneumaticredux.client.render.pneumaticArmor.UpgradeRenderHandlerList;
import me.desht.pneumaticredux.common.CommonHUDHandler;
import me.desht.pneumaticredux.common.inventory.ContainerChargingStationItemInventory;
import me.desht.pneumaticredux.common.item.ItemPneumaticArmor;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.recipes.CraftingRegistrator;
import me.desht.pneumaticredux.common.tileentity.TileEntityChargingStation;
import me.desht.pneumaticredux.common.util.PneumaticCraftUtils;
import me.desht.pneumaticredux.lib.PneumaticValues;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiPneumaticHelmet extends GuiPneumaticInventoryItem {

    private GuiAnimatedStat statusStat;

    public GuiPneumaticHelmet(ContainerChargingStationItemInventory container, TileEntityChargingStation te) {
        super(container, te);
    }

    @Override
    public void initGui() {
        super.initGui();
        addAnimatedStat("gui.tab.info", Textures.GUI_INFO_LOCATION, 0xFF8888FF, true).setText("gui.tab.info.item.pneumaticHelmet");
        statusStat = addAnimatedStat("Helmet Status", new ItemStack(Itemss.PNEUMATIC_HELMET), 0xFFFFAA00, false);

        ItemStack searchUpgradeStat = CraftingRegistrator.getUpgrade(EnumUpgrade.SEARCH);
        addAnimatedStat(searchUpgradeStat.getDisplayName(), searchUpgradeStat, 0xFF0000FF, false).setText("gui.tab.info.item.pneumaticHelmet.searchUpgrade");
        ItemStack coordinateTracker = CraftingRegistrator.getUpgrade(EnumUpgrade.COORDINATE_TRACKER);
        addAnimatedStat(coordinateTracker.getDisplayName(), coordinateTracker, 0xFF0000FF, false).setText("gui.tab.info.item.pneumaticHelmet.coordinateTracker");
        ItemStack entityTracker = CraftingRegistrator.getUpgrade(EnumUpgrade.ENTITY_TRACKER);
        addAnimatedStat(entityTracker.getDisplayName(), entityTracker, 0xFF0000FF, true).setText("gui.tab.info.item.pneumaticHelmet.entityTracker");
        ItemStack blockTracker = CraftingRegistrator.getUpgrade(EnumUpgrade.BLOCK_TRACKER);
        addAnimatedStat(blockTracker.getDisplayName(), blockTracker, 0xFF0000FF, true).setText("gui.tab.info.item.pneumaticHelmet.blockTracker");
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        CommonHUDHandler.getHandlerForPlayer().checkHelmetInventory(itemStack);
        statusStat.setText(getStatusText());
    }

    private List<String> getStatusText() {
        List<String> text = new ArrayList<>();

        text.add("\u00a77Air Usage:");
        float totalUsage = UpgradeRenderHandlerList.instance().getAirUsage(FMLClientHandler.instance().getClient().player, true);
        if (totalUsage > 0F) {
            EntityPlayer player = FMLClientHandler.instance().getClient().player;
            for (int i = 0; i < UpgradeRenderHandlerList.instance().upgradeRenderers.size(); i++) {
                if (CommonHUDHandler.getHandlerForPlayer(player).upgradeRenderersInserted[i]) {
                    IUpgradeRenderHandler handler = UpgradeRenderHandlerList.instance().upgradeRenderers.get(i);
                    float upgradeUsage = handler.getEnergyUsage(CommonHUDHandler.getHandlerForPlayer(player).rangeUpgradesInstalled, player);
                    if (upgradeUsage > 0F) {
                        text.add(TextFormatting.BLACK.toString() + PneumaticCraftUtils.roundNumberTo(upgradeUsage, 1) + " mL/tick (" + handler.getUpgradeName() + ")");
                    }
                }
            }
            text.add("\u00a70--------+");
            text.add("\u00a70" + PneumaticCraftUtils.roundNumberTo(totalUsage, 1) + " mL/tick");
        } else {
            text.add(TextFormatting.BLACK + "0.0 mL/tick");
        }
        text.add("\u00a77Estimated time remaining:");
        int volume = ItemPneumaticArmor.getUpgrades(EnumUpgrade.VOLUME, te.getPrimaryInventory().getStackInSlot(TileEntityChargingStation.CHARGE_INVENTORY_INDEX)) * PneumaticValues.VOLUME_VOLUME_UPGRADE + getDefaultVolume();
        int airLeft = (int) (((IPressurizable) itemStack.getItem()).getPressure(itemStack) * volume);
        if (totalUsage == 0) {
            if (airLeft > 0) text.add("\u00a70infinite");
            else text.add("\u00a700s");
        } else {
            text.add("\u00a70" + PneumaticCraftUtils.convertTicksToMinutesAndSeconds((int) (airLeft / totalUsage), false));
        }
        return text;
    }

    @Override
    protected int getDefaultVolume() {
        return PneumaticValues.PNEUMATIC_HELMET_VOLUME;
    }
}
