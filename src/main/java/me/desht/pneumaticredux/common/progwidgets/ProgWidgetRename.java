package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneAIManager;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.common.remote.TextVariableParser;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class ProgWidgetRename extends ProgWidget implements IRenamingWidget, IVariableWidget {
    private DroneAIManager aiManager;

    @Override
    public boolean hasStepInput() {
        return true;
    }

    @Override
    public Class<? extends IProgWidget> returnType() {
        return null;
    }

    @Override
    public Class<? extends IProgWidget>[] getParameters() {
        return new Class[]{ProgWidgetString.class};
    }

    @Override
    public String getWidgetString() {
        return "rename";
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.FLYING_FLOWER_DAMAGE;
    }

    @Override
    public WidgetDifficulty getDifficulty() {
        return WidgetDifficulty.EASY;
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_RENAME;
    }

    @Override
    public EntityAIBase getWidgetAI(final IDroneBase drone, final IProgWidget widget) {
        return new DroneAIRename(drone, (IRenamingWidget) widget);
    }

    private class DroneAIRename extends EntityAIBase {
        private final IDroneBase drone;
        private final IRenamingWidget widget;

        public DroneAIRename(IDroneBase drone, IRenamingWidget widget) {
            this.drone = drone;
            this.widget = widget;
        }

        @Override
        public boolean shouldExecute() {
            drone.setName(widget.getNewName() != null ? widget.getNewName() : I18n.format("entity.PneumaticCraft.Drone.name"));
            return false;
        }

    }

    @Override
    public String getNewName() {
        return getConnectedParameters()[0] != null ? new TextVariableParser(((ProgWidgetString) getConnectedParameters()[0]).string, aiManager).parse() : null;
    }

    @Override
    public void setAIManager(DroneAIManager aiManager) {
        this.aiManager = aiManager;
    }

    @Override
    public void addVariables(Set<String> variables) {
    }

}
