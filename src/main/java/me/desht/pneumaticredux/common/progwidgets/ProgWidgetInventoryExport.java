package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneEntityAIInventoryExport;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

public class ProgWidgetInventoryExport extends ProgWidgetInventoryBase {
    @Override
    public String getWidgetString() {
        return "inventoryExport";
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_INV_EX;
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneEntityAIInventoryExport(drone, (ProgWidgetAreaItemBase) widget);
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.PROPULSION_PLANT_DAMAGE;
    }
}
