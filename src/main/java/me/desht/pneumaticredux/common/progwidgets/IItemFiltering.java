package me.desht.pneumaticredux.common.progwidgets;

import net.minecraft.item.ItemStack;

public interface IItemFiltering {
    boolean isItemValidForFilters(ItemStack item);
}
