package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.client.gui.GuiProgrammer;
import me.desht.pneumaticredux.client.gui.programmer.GuiProgWidgetExternalProgram;
import me.desht.pneumaticredux.common.ai.DroneAIExternalProgram;
import me.desht.pneumaticredux.common.ai.IDroneBase;
import me.desht.pneumaticredux.common.item.ItemPlastic;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProgWidgetExternalProgram extends ProgWidgetAreaItemBase {
    public boolean shareVariables;

    @Override
    public String getWidgetString() {
        return "externalProgram";
    }

    @Override
    public int getCraftingColorIndex() {
        return ItemPlastic.ENDER_PLANT_DAMAGE;
    }

    @Override
    public ResourceLocation getTexture() {
        return Textures.PROG_WIDGET_EXTERNAL_PROGRAM;
    }

    @Override
    public EntityAIBase getWidgetAI(IDroneBase drone, IProgWidget widget) {
        return new DroneAIExternalProgram(drone, aiManager, (ProgWidgetExternalProgram) widget);
    }

    @Override
    public Class<? extends IProgWidget>[] getParameters() {
        return new Class[]{ProgWidgetArea.class};
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("shareVariables", shareVariables);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        shareVariables = tag.getBoolean("shareVariables");
    }

    @Override
    public boolean canBeRunByComputers(IDroneBase drone, IProgWidget widget) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getOptionWindow(GuiProgrammer guiProgrammer) {
        return new GuiProgWidgetExternalProgram(this, guiProgrammer);
    }
}
