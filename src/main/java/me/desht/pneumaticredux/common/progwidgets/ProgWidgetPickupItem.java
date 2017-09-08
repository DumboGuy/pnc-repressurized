package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneEntityAIPickupItems;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

public class ProgWidgetPickupItem extends ProgWidgetAreaItemBase {

    @Override
    public String getWidgetString() {
        return "pickupItem";
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_PICK_ITEM;
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneEntityAIPickupItems(drone, (ProgWidgetAreaItemBase) widget);
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.POTION_PLANT_DAMAGE;
    }
}
