package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.entity.living.EntityDrone;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;

public class ProgWidgetStandby extends ProgWidget {
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
        return null;
    }

    @Override
    public String getWidgetString() {
        return "standby";
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.REPULSION_PLANT_DAMAGE;
    }

    @Override
    public WidgetDifficulty getDifficulty() {
        return WidgetDifficulty.EASY;
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_STANDBY;
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneAIStandby((EntityDrone) drone);
    }

    public static class DroneAIStandby extends EntityAIBase {

        private final EntityDrone drone;

        public DroneAIStandby(EntityDrone drone) {
            this.drone = drone;
        }

        @Override
        public boolean shouldExecute() {
            drone.setStandby(true);
            return false;
        }
    }
}
