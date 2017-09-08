package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.client.gui.GuiProgrammer;
import me.desht.pneumaticredux.client.gui.programmer.GuiProgWidgetAreaShow;
import me.desht.pneumaticredux.common.ai.DroneAIEntityImport;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Set;

public class ProgWidgetEntityImport extends ProgWidget implements IProgWidget, IAreaProvider, IEntityProvider {

    @Override
    public void addErrors(List<String> curInfo, List<IProgWidget> widgets) {
        super.addErrors(curInfo, widgets);
        if (getConnectedParameters()[0] == null) {
            curInfo.add("gui.progWidget.area.error.noArea");
        }
    }

    @Override
    public boolean hasStepInput() {
        return true;
    }

    @Override
    public Class<? extends IProgWidget> returnType() {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getOptionWindow(GuiProgrammer guiProgrammer) {
        return new GuiProgWidgetAreaShow(this, guiProgrammer);
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneAIEntityImport(drone, widget);
    }

    @Override
    public Class<? extends IProgWidget>[] getParameters() {
        return new Class[]{ProgWidgetArea.class, ProgWidgetString.class};
    }

    @Override
    public String getWidgetString() {
        return "entityImport";
    }

    @Override
    public WidgetDifficulty getDifficulty() {
        return WidgetDifficulty.EASY;
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_ENTITY_IM;
    }

    @Override
    public void getArea(Set<BlockPos> area) {
        ProgWidgetAreaItemBase.getArea(area, (ProgWidgetArea) getConnectedParameters()[0], (ProgWidgetArea) getConnectedParameters()[2]);
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.RAIN_PLANT_DAMAGE;
    }

    @Override
    public List<Entity> getValidEntities(World world) {
        return ProgWidgetAreaItemBase.getValidEntities(world, this);
    }

    @Override
    public boolean isEntityValid(Entity entity) {
        return ProgWidgetAreaItemBase.isEntityValid(entity, this);
    }
}
