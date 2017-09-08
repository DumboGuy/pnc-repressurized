package me.desht.pneumaticredux.client.gui.programmer;

import me.desht.pneumaticredux.client.gui.GuiProgrammer;
import me.desht.pneumaticredux.client.gui.widget.WidgetTextField;
import me.desht.pneumaticredux.common.progwidgets.IProgWidget;
import me.desht.pneumaticredux.common.progwidgets.ProgWidgetString;

public class GuiProgWidgetString extends GuiProgWidgetOptionBase {
    private WidgetTextField textfield;

    public GuiProgWidgetString(IProgWidget widget, GuiProgrammer guiProgrammer) {
        super(widget, guiProgrammer);
    }

    @Override
    public void initGui() {
        super.initGui();
        textfield = new WidgetTextField(fontRenderer, guiLeft + 10, guiTop + 20, 160, 10);
        textfield.setMaxStringLength(1000);
        textfield.setText(((ProgWidgetString) widget).string);
        addWidget(textfield);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        ((ProgWidgetString) widget).string = textfield.getText();
    }
}
