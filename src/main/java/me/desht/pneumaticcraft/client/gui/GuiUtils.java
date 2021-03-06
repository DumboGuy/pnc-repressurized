package me.desht.pneumaticcraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiUtils {
    private static HashMap<String, ResourceLocation> resourceMap = new HashMap<String, ResourceLocation>();
    // This method is used to draw a pressure gauge in various GUI's.
    private static final int CIRCLE_POINTS = 500;
    public static final double RADIUS = 20D;
    private static final double START_ANGLE = 240D / 180D * Math.PI; // 150
    // degrees
    private static final double STOP_ANGLE = -60D / 180D * Math.PI;
    private static final int GAUGE_POINTS = (int) ((START_ANGLE - STOP_ANGLE) / (2D * Math.PI) * CIRCLE_POINTS);
    private static RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

    public static void drawPressureGauge(FontRenderer fontRenderer, float minPressure, float maxPressure, float dangerPressure, float minWorkingPressure, float currentPressure, int xPos, int yPos, float zLevel) {
        BufferBuilder wr = Tessellator.getInstance().getBuffer();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(2.0F);
        // Draw the green and red surface in the gauge.
        GL11.glColor4d(0.7D, 0, 0, 1);
        wr.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        wr.pos(xPos, yPos, zLevel).endVertex();
        // System.out.println("gauge points: "+ GAUGE_POINTS);
        int explodeBoundary = GAUGE_POINTS - (int) ((dangerPressure - minPressure) / (maxPressure - minPressure) * GAUGE_POINTS);
        int workingBoundary = GAUGE_POINTS - (int) ((minWorkingPressure - minPressure) / (maxPressure - minPressure) * GAUGE_POINTS);
        boolean changedColorGreen = false;
        boolean changedColorYellow = false;
        for (int i = 0; i < GAUGE_POINTS; i++) {
            if (i == explodeBoundary && !changedColorGreen) {
                Tessellator.getInstance().draw();
                GL11.glColor4d(0, 0.7D, 0, 1);
                wr.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
                wr.pos(xPos, yPos, zLevel).endVertex();
                i--;
                changedColorGreen = true;
            }
            if (i == workingBoundary && !changedColorYellow) {
                Tessellator.getInstance().draw();
                GL11.glColor4d(0.9D, 0.9D, 0, 1);
                wr.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
                wr.pos(xPos, yPos, zLevel).endVertex();
                i--;
                changedColorYellow = true;
            }
            double angle = (double) -i / (double) CIRCLE_POINTS * 2D * Math.PI - STOP_ANGLE;
            wr.pos(Math.cos(angle) * RADIUS + xPos, Math.sin(angle) * RADIUS + yPos, zLevel).endVertex();
        }
        Tessellator.getInstance().draw();

        // Draw the black surrounding circle
        GL11.glColor4d(0.0, 0.0, 0.0, 1.0);

        wr.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        for (int i = 0; i < CIRCLE_POINTS; i++) {
            double angle = (double) i / (double) CIRCLE_POINTS * 2D * Math.PI;
            wr.pos(Math.cos(angle) * RADIUS + xPos, Math.sin(angle) * RADIUS + yPos, zLevel).endVertex();
        }
        Tessellator.getInstance().draw();

        // Draw the scale
        int currentScale = (int) maxPressure;
        List<int[]> textScalers = new ArrayList<int[]>();
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        for (int i = 0; i <= GAUGE_POINTS; i++) {
            double angle = (double) -i / (double) CIRCLE_POINTS * 2D * Math.PI - STOP_ANGLE;
            if (i == GAUGE_POINTS - (int) ((currentScale - minPressure) / (maxPressure - minPressure) * GAUGE_POINTS)) {
                textScalers.add(new int[]{currentScale, (int) (Math.cos(angle) * RADIUS * 1.4D), (int) (Math.sin(angle) * RADIUS * 1.4D)});
                currentScale--;
                // System.out.println("curr: "+ currentScale);
                wr.pos(Math.cos(angle) * RADIUS * 0.9D + xPos, Math.sin(angle) * RADIUS * 0.9D + yPos, zLevel).endVertex();
                wr.pos(Math.cos(angle) * RADIUS * 1.1D + xPos, Math.sin(angle) * RADIUS * 1.1D + yPos, zLevel).endVertex();

            }
        }
        Tessellator.getInstance().draw();

        // Draw the needle.
        GL11.glColor4d(0.0, 0.0, 0.0, 1.0);
        double angleIndicator = GAUGE_POINTS - (int) ((currentPressure - minPressure) / (maxPressure - minPressure) * GAUGE_POINTS);
        angleIndicator = -angleIndicator / CIRCLE_POINTS * 2D * Math.PI - STOP_ANGLE;
        wr.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        wr.pos(Math.cos(angleIndicator + 0.89D * Math.PI) * RADIUS * 0.3D + xPos, Math.sin(angleIndicator + 0.89D * Math.PI) * RADIUS * 0.3D + yPos, zLevel).endVertex();
        wr.pos(Math.cos(angleIndicator + 1.11D * Math.PI) * RADIUS * 0.3D + xPos, Math.sin(angleIndicator + 1.11D * Math.PI) * RADIUS * 0.3D + yPos, zLevel).endVertex();
        wr.pos(Math.cos(angleIndicator) * RADIUS * 0.8D + xPos, Math.sin(angleIndicator) * RADIUS * 0.8D + yPos, zLevel).endVertex();
        Tessellator.getInstance().draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // draw the numbers next to the scaler.
        while (textScalers.size() > 10) {
            int divider = textScalers.size() / 5;
            for (int i = textScalers.size() - 1; i >= 0; i--) {
                if (i % divider != 0) textScalers.remove(i);
            }
        }
        for (int[] scaler : textScalers) {
            fontRenderer.drawString("" + scaler[0], xPos + scaler[1] - 3, yPos + scaler[2] - 3, 0);
        }
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }

    public static ResourceLocation getResourceLocation(String texture) {
        if (resourceMap.containsKey(texture)) {
            return resourceMap.get(texture);
        } else {
            ResourceLocation resource = new ResourceLocation(texture);
            resourceMap.put(texture, resource);
            return resource;
        }
    }

    public static void drawItemStack(ItemStack stack, int x, int y) {
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

}
