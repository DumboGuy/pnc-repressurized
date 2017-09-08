package me.desht.pneumaticredux.common.inventory;

import me.desht.pneumaticredux.common.tileentity.TileEntityPlasticMixer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;

class SlotItemSpecific extends SlotItemHandler {
    private Item itemAllowed;
    private int oreDictEntry;
    private boolean dye;

    SlotItemSpecific(IItemHandler handler, Item itemAllowed, int index, int x, int y) {
        super(handler, index, x, y);
        this.itemAllowed = itemAllowed;
    }

    SlotItemSpecific(IItemHandler handler, String oreDictKeyAllowed, int index, int x, int y) {
        super(handler, index, x, y);
        oreDictEntry = OreDictionary.getOreID(oreDictKeyAllowed);
        dye = oreDictKeyAllowed.equals("dye");
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for
     * the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        if (itemAllowed != null) {
            Item item = par1ItemStack == null ? null : par1ItemStack.getItem();
            return item == itemAllowed;
        } else {
            int[] ids = OreDictionary.getOreIDs(par1ItemStack);
            for (int id : ids) {
                if (id == oreDictEntry) return true;
                if (dye && TileEntityPlasticMixer.getDyeIndex(par1ItemStack) >= 0) return true;
            }
            return false;
        }
    }

}
