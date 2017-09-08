package me.desht.pneumaticredux.client;

import com.google.common.collect.Maps;
import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.item.IProgrammable;
import me.desht.pneumaticredux.client.gui.IGuiDrone;
import me.desht.pneumaticredux.client.render.RenderProgressingLine;
import me.desht.pneumaticredux.client.util.RenderUtils;
import me.desht.pneumaticredux.common.DateEventHandler;
import me.desht.pneumaticredux.common.block.tubes.ModuleRegistrator;
import me.desht.pneumaticredux.common.block.tubes.ModuleRegulatorTube;
import me.desht.pneumaticredux.common.block.tubes.TubeModule;
import me.desht.pneumaticredux.common.config.ConfigHandler;
import me.desht.pneumaticredux.common.item.ItemMinigun;
import me.desht.pneumaticredux.common.item.ItemProgrammingPuzzle;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.minigun.Minigun;
import me.desht.pneumaticredux.common.progwidgets.IProgWidget;
import me.desht.pneumaticredux.common.tileentity.TileEntityProgrammer;
import me.desht.pneumaticredux.lib.Names;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class ClientEventHandler {
    public static float playerRenderPartialTick;
    private final RenderProgressingLine minigunFire = new RenderProgressingLine().setProgress(1);
    private final Map<IModel, Class<? extends TubeModule>> awaitingBaking = new HashMap<IModel, Class<? extends TubeModule>>();

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof IProgrammable) {
            IProgrammable programmable = (IProgrammable) event.getItemStack().getItem();
            if (programmable.canProgram(event.getItemStack()) && programmable.showProgramTooltip()) {
                boolean hasInvalidPrograms = false;
                List<String> addedEntries = new ArrayList<String>();
                Map<String, Integer> widgetMap = getPuzzleSummary(TileEntityProgrammer.getProgWidgets(event.getItemStack()));
                for (Map.Entry<String, Integer> entry : widgetMap.entrySet()) {
                    IProgWidget widget = ItemProgrammingPuzzle.getWidgetForName(entry.getKey());
                    String prefix = "";
                    GuiScreen curScreen = Minecraft.getMinecraft().currentScreen;
                    if (curScreen instanceof IGuiDrone) {
                        if (!((IGuiDrone) curScreen).getDrone().isProgramApplicable(widget)) {
                            prefix = TextFormatting.RED + "";
                            hasInvalidPrograms = true;
                        }
                    }
                    addedEntries.add(prefix + "-" + entry.getValue() + "x " + I18n.format("programmingPuzzle." + entry.getKey() + ".name"));
                }
                if (hasInvalidPrograms) {
                    event.getToolTip().add(TextFormatting.RED + I18n.format("gui.tooltip.programmable.invalidPieces"));
                }
                Collections.sort(addedEntries);
                event.getToolTip().addAll(addedEntries);
            }
        }
    }

    private static Map<String, Integer> getPuzzleSummary(List<IProgWidget> widgets) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (IProgWidget widget : widgets) {
            if (!map.containsKey(widget.getWidgetString())) {
                map.put(widget.getWidgetString(), 1);
            } else {
                map.put(widget.getWidgetString(), map.get(widget.getWidgetString()) + 1);
            }
        }
        return map;
    }

    @SubscribeEvent
    public void onLivingRender(RenderLivingEvent.Pre event) {
        setRenderHead(event.getEntity(), false);
    }

    @SubscribeEvent
    public void onLivingRender(RenderLivingEvent.Post event) {
        setRenderHead(event.getEntity(), true);
    }

    private void setRenderHead(EntityLivingBase entity, boolean setRender) {
        if (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Itemss.PNEUMATIC_HELMET
                && (ConfigHandler.General.useHelmetModel || DateEventHandler.isIronManEvent())) {
            Render renderer = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
            if (renderer instanceof RenderBiped) {
                ModelBiped modelBiped = (ModelBiped) ((RenderBiped) renderer).getMainModel();
                modelBiped.bipedHead.showModel = setRender;
            }
        }
    }

    /* TODO 1.8 @SubscribeEvent
      public void onPlayerRender(RenderPlayerEvent.Pre event){
          playerRenderPartialTick = event.partialRenderTick;
          if(!Config.useHelmetModel && !DateEventHandler.isIronManEvent() || event.entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null || event.entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() != Itemss.pneumaticHelmet) return;
          event.renderer.modelBipedMain.bipedHead.showModel = false;
      }

      @SubscribeEvent
      public void onPlayerRender(RenderPlayerEvent.Post event){
          event.renderer.modelBipedMain.bipedHead.showModel = true;
      }*/

    @SubscribeEvent
    public void tickEnd(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END && FMLClientHandler.instance().getClient().inGameHasFocus && PneumaticRedux.proxy.getPlayer().world != null && (ModuleRegulatorTube.inverted || !ModuleRegulatorTube.inLine)) {
            Minecraft mc = FMLClientHandler.instance().getClient();
            ScaledResolution sr = new ScaledResolution(mc);
            FontRenderer fontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
            String warning = TextFormatting.RED + I18n.format("gui.regulatorTube.hudMessage." + (ModuleRegulatorTube.inverted ? "inverted" : "notInLine"));
            fontRenderer.drawStringWithShadow(warning, sr.getScaledWidth() / 2 - fontRenderer.getStringWidth(warning) / 2, sr.getScaledHeight() / 2 + 30, 0xFFFFFFFF);
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        double gunRadius = 1.1D;

        EntityPlayer thisPlayer = Minecraft.getMinecraft().player;
        double playerX = thisPlayer.prevPosX + (thisPlayer.posX - thisPlayer.prevPosX) * event.getPartialTicks();
        double playerY = thisPlayer.prevPosY + (thisPlayer.posY - thisPlayer.prevPosY) * event.getPartialTicks();
        double playerZ = thisPlayer.prevPosZ + (thisPlayer.posZ - thisPlayer.prevPosZ) * event.getPartialTicks();
        GL11.glPushMatrix();
        GL11.glTranslated(-playerX, -playerY, -playerZ);

        for (EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {
            if (thisPlayer == player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) continue;
            ItemStack curItem = player.getHeldItemMainhand();
            if (curItem.getItem() == Itemss.MINIGUN) {
                Minigun minigun = ((ItemMinigun) Itemss.MINIGUN).getMinigun(curItem, player);
                if (minigun.isMinigunActivated() && minigun.getMinigunSpeed() == Minigun.MAX_GUN_SPEED) {
                    GL11.glPushMatrix();
                    playerX = player.prevPosX + (player.posX - player.prevPosX) * event.getPartialTicks();
                    playerY = player.prevPosY + (player.posY - player.prevPosY) * event.getPartialTicks();
                    playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.getPartialTicks();
                    GL11.glTranslated(playerX, playerY + 0.5, playerZ);

                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    //GL11.glDisable(GL11.GL_LIGHTING);
                    RenderUtils.glColorHex(0xFF000000 | minigun.getAmmoColor());
                    for (int i = 0; i < 5; i++) {

                        Vec3d directionVec = player.getLookVec().normalize();
                        Vec3d vec = new Vec3d(directionVec.x, 0, directionVec.z).normalize();
                        vec.rotateYaw((float) Math.toRadians(-15 + (player.rotationYawHead - player.renderYawOffset)));
                        minigunFire.startX = vec.x * gunRadius;
                        minigunFire.startY = vec.y * gunRadius - player.getYOffset();
                        minigunFire.startZ = vec.z * gunRadius;
                        minigunFire.endX = directionVec.x * 20 + player.getRNG().nextDouble() - 0.5;
                        minigunFire.endY = directionVec.y * 20 + player.getRNG().nextDouble() - 0.5;
                        minigunFire.endZ = directionVec.z * 20 + player.getRNG().nextDouble() - 0.5;
                        minigunFire.render();
                    }
                    GL11.glColor4d(1, 1, 1, 1);
                    // GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glPopMatrix();
                }
            }
        }
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        System.out.println("Stitching...");
        ModuleRegistrator.models = Maps.newHashMap();
        for (Class<? extends TubeModule> moduleClass : ModuleRegistrator.modules.values()) {
            try {
                TubeModule module = moduleClass.newInstance();

                OBJLoader objLoader = OBJLoader.INSTANCE;
                IModel modelDefinition = objLoader.loadModel(new ResourceLocation(Names.MOD_ID, "models/block/modules/" + module.getModelName() + ".obj"));
                awaitingBaking.put(modelDefinition, moduleClass);
                for (ResourceLocation texture : modelDefinition.getTextures()) {
                    event.getMap().registerSprite(texture);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onModelBaking(ModelBakeEvent event) {
        System.out.println("Baking...");
        for (Map.Entry<IModel, Class<? extends TubeModule>> entry : awaitingBaking.entrySet()) {
            IBakedModel model = entry.getKey().bake(entry.getKey().getDefaultState(), DefaultVertexFormats.BLOCK, RenderUtils.TEXTURE_GETTER);
            ModuleRegistrator.models.put(entry.getValue(), model);
        }
    }
}
