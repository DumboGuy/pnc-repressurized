package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneEntityAIInventoryImport;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

public class ProgWidgetInventoryImport extends ProgWidgetInventoryBase {

    @Override
    public String getWidgetString() {
        return "inventoryImport";
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_INV_IM;
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneEntityAIInventoryImport(drone, (ProgWidgetAreaItemBase) widget);
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.RAIN_PLANT_DAMAGE;
    }
}
