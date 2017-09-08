package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneAILiquidImport;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ProgWidgetLiquidImport extends ProgWidgetInventoryBase implements ILiquidFiltered {

    @Override
    public String getWidgetString() {
        return "liquidImport";
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_LIQUID_IM;
    }

    @Override
    public Class<? extends IProgWidget>[] getParameters() {
        return new Class[]{ProgWidgetArea.class, ProgWidgetLiquidFilter.class};
    }

    @Override
    public boolean isFluidValid(Fluid fluid) {
        return ProgWidgetLiquidFilter.isLiquidValid(fluid, this, 1);
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneAILiquidImport(drone, (ProgWidgetAreaItemBase) widget);
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.RAIN_PLANT_DAMAGE;
    }
}
