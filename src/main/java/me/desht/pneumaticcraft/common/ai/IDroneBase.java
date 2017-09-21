package me.desht.pneumaticcraft.common.ai;

import me.desht.pneumaticcraft.api.drone.IDrone;
import me.desht.pneumaticcraft.common.progwidgets.IProgWidget;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IDroneBase extends IDrone {

    public List<IProgWidget> getProgWidgets();

    public void setActiveProgram(IProgWidget widget);

    public boolean isProgramApplicable(IProgWidget widget);

    public void overload();

    public DroneAIManager getAIManager();

    /**
     * Sets the label that was jumped to last, with a hierarchy in case of External Programs.
     */
    public void updateLabel();

    public void addDebugEntry(String message);

    public void addDebugEntry(String message, BlockPos pos);
}
