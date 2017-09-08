package me.desht.pneumaticredux.common.thirdparty.jei;

import me.desht.pneumaticredux.client.gui.GuiPneumaticContainerBase;
import mezz.jei.api.gui.IAdvancedGuiHandler;

import java.awt.*;
import java.util.List;

public class GuiTabHandler implements IAdvancedGuiHandler<GuiPneumaticContainerBase> {

    @Override
    public Class<GuiPneumaticContainerBase> getGuiContainerClass() {
        return GuiPneumaticContainerBase.class;
    }

    @Override
    public List<Rectangle> getGuiExtraAreas(GuiPneumaticContainerBase guiContainer) {
        return guiContainer.getTabRectangles();
    }

}
