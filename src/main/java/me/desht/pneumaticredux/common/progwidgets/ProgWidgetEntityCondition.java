package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.client.gui.GuiProgrammer;
import me.desht.pneumaticredux.client.gui.programmer.GuiProgWidgetCondition;
import me.desht.pneumaticredux.common.ai.DroneAIBlockCondition;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ProgWidgetEntityCondition extends ProgWidgetCondition {

    @Override
    public String getWidgetString() {
        return "conditionEntity";
    }

    @Override
    public Class<? extends IProgWidget>[] getParameters() {
        return new Class[]{ProgWidgetArea.class, ProgWidgetString.class, ProgWidgetString.class};
    }

    @Override
    protected DroneAIBlockCondition getEvaluator(IDroneBase drone, IProgWidget widget) {
        return null;
    }

    @Override
    public IProgWidget getOutputWidget(IDroneBase drone, List<IProgWidget> allWidgets) {
        List<Entity> entities = getValidEntities(drone.world());
        boolean result = getOperator() == Operator.EQUALS ? entities.size() == getRequiredCount() : entities.size() >= getRequiredCount();
        if (result) {
            drone.addDebugEntry("gui.progWidget.condition.evaluatedTrue");
        } else {
            drone.addDebugEntry("gui.progWidget.condition.evaluatedFalse");
        }
        return ProgWidgetJump.jumpToLabel(drone, allWidgets, this, result);
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_CONDITION_ENTITY;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getOptionWindow(GuiProgrammer guiProgrammer) {
        return new GuiProgWidgetCondition(this, guiProgrammer) {
            @Override
            protected boolean isSidedWidget() {
                return false;
            }

            @Override
            protected boolean isUsingAndOr() {
                return false;
            }
        };
    }
}
