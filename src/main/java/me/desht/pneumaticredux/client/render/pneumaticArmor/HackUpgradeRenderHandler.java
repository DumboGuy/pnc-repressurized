package me.desht.pneumaticredux.client.render.pneumaticArmor;

import me.desht.pneumaticredux.api.client.IGuiAnimatedStat;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IOptionPage;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IUpgradeRenderHandler;
import me.desht.pneumaticredux.api.item.IItemRegistry.EnumUpgrade;
import me.desht.pneumaticredux.common.item.ItemPneumaticArmor;
import me.desht.pneumaticredux.common.item.Itemss;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HackUpgradeRenderHandler implements IUpgradeRenderHandler {

    @Override
    public String getUpgradeName() {
        return "hackingUpgrade";
    }

    @Override
    public void initConfig() {

    }

    @Override
    public void saveToConfig() {

    }

    @Override
    public void update(EntityPlayer player, int rangeUpgrades) {

    }

    @Override
    public void render3D(float partialTicks) {

    }

    @Override
    public void render2D(float partialTicks, boolean helmetEnabled) {

    }

    @Override
    public IGuiAnimatedStat getAnimatedStat() {
        return null;
    }

    @Override
    public Item[] getRequiredUpgrades() {
        return new Item[]{Itemss.upgrades.get(EnumUpgrade.SECURITY)};
    }

    private static boolean enabledForStacks(ItemStack[] upgradeStacks) {
        for (ItemStack stack : upgradeStacks) {
            if (stack != null && stack.getItem() == Itemss.upgrades.get(EnumUpgrade.SECURITY)) return true;
        }
        return false;
    }

    public static boolean enabledForPlayer(EntityPlayer player) {
        ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (helmet != null) {
            return enabledForStacks(ItemPneumaticArmor.getUpgradeStacks(helmet));
        } else {
            return false;
        }
    }

    @Override
    public float getEnergyUsage(int rangeUpgrades, EntityPlayer player) {
        return 0;
    }

    @Override
    public void reset() {

    }

    @Override
    public IOptionPage getGuiOptionsPage() {
        return null;
    }

}
