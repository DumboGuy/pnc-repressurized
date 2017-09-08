package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneAIDig;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

public class ProgWidgetDig extends ProgWidgetDigAndPlace {

    public ProgWidgetDig() {
        super(ProgWidgetDigAndPlace.EnumOrder.CLOSEST);
    }

    @Override
    public String getWidgetString() {
        return "dig";
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_DIG;
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return setupMaxActions(new DroneAIDig(drone, (ProgWidgetAreaItemBase) widget), (IMaxActions) widget);
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.SLIME_PLANT_DAMAGE;
    }

}
