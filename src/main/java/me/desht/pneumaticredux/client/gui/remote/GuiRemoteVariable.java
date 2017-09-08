package me.desht.pneumaticredux.client.gui.remote;

import me.desht.pneumaticredux.client.gui.GuiRemoteEditor;
import me.desht.pneumaticredux.client.gui.widget.WidgetComboBox;
import me.desht.pneumaticredux.common.inventory.ContainerRemote;
import me.desht.pneumaticredux.common.remote.ActionWidgetVariable;
import net.minecraft.client.resources.I18n;

public class GuiRemoteVariable<Widget extends ActionWidgetVariable> extends GuiRemoteOptionBase<Widget> {

    private WidgetComboBox variableField;

    public GuiRemoteVariable(Widget widget, GuiRemoteEditor guiRemote) {
        super(widget, guiRemote);
    }

    @Override
    public void initGui() {
        super.initGui();
        addLabel(I18n.format("gui.progWidget.coordinate.variableName"), guiLeft + 10, guiTop + 70);
        addLabel("#", guiLeft + 10, guiTop + 81);

        variableField = new WidgetComboBox(fontRenderer, guiLeft + 18, guiTop + 80, 152, 10);
        variableField.setElements(((ContainerRemote) guiRemote.inventorySlots).variables);
        variableField.setText(widget.getVariableName());
        variableField.setTooltip(I18n.format("gui.remote.variable.tooltip"));
        addWidget(variableField);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        widget.setVariableName(variableField.getText());
    }
}
