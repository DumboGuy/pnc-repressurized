package me.desht.pneumaticredux.client.gui;

import me.desht.pneumaticredux.api.tileentity.IHeatExchanger;
import me.desht.pneumaticredux.client.gui.widget.WidgetTemperature;
import me.desht.pneumaticredux.common.inventory.ContainerAdvancedLiquidCompressor;
import me.desht.pneumaticredux.common.tileentity.TileEntityAdvancedLiquidCompressor;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.List;

public class GuiAdvancedLiquidCompressor extends GuiLiquidCompressor {

    public GuiAdvancedLiquidCompressor(InventoryPlayer player, TileEntityAdvancedLiquidCompressor te) {
        super(new ContainerAdvancedLiquidCompressor(player, te), te, Textures.GUI_ADVANCED_LIQUID_COMPRESSOR);
    }

    @Override
    public void initGui() {
        super.initGui();
        addWidget(new WidgetTemperature(0, guiLeft + 92, guiTop + 20, 273, 675, ((IHeatExchanger) te).getHeatExchangerLogic(null), 325, 625));
    }

    @Override
    protected int getFluidOffset() {
        return 72;
    }

    @Override
    public void addProblems(List<String> curInfo) {
        super.addProblems(curInfo);
        if (te.getEfficiency() < 100) {
            curInfo.add(I18n.format("gui.tab.problems.advancedAirCompressor.efficiency", te.getEfficiency() + "%%"));
        }
    }
}
