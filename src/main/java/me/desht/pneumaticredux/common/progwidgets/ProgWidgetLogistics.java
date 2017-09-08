package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneAILogistics;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

public class ProgWidgetLogistics extends ProgWidgetAreaItemBase {

    @Override
    public String getWidgetString() {
        return "logistics";
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.ENDER_PLANT_DAMAGE;
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_LOGISTICS;
    }

    @Override
    public Class<? extends IProgWidget>[] getParameters() {
        return new Class[]{ProgWidgetArea.class};
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneAILogistics(drone, (ProgWidgetAreaItemBase) widget);
    }

}
