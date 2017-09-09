package me.desht.pneumaticredux.proxy;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IUpgradeRenderHandler;
import me.desht.pneumaticredux.client.AreaShowManager;
import me.desht.pneumaticredux.client.ClientEventHandler;
import me.desht.pneumaticredux.client.ClientTickHandler;
import me.desht.pneumaticredux.client.KeyHandler;
import me.desht.pneumaticredux.client.gui.pneumaticHelmet.GuiHelmetMainScreen;
import me.desht.pneumaticredux.client.model.item.ModelProgrammingPuzzle.LoaderProgrammingPuzzle;
import me.desht.pneumaticredux.client.render.entity.RenderDrone;
import me.desht.pneumaticredux.client.render.entity.RenderEntityRing;
import me.desht.pneumaticredux.client.render.entity.RenderEntityVortex;
import me.desht.pneumaticredux.client.render.pneumaticArmor.CoordTrackUpgradeHandler;
import me.desht.pneumaticredux.client.render.pneumaticArmor.HUDHandler;
import me.desht.pneumaticredux.client.render.pneumaticArmor.UpgradeRenderHandlerList;
import me.desht.pneumaticredux.client.render.pneumaticArmor.entitytracker.EntityTrackHandler;
import me.desht.pneumaticredux.client.render.tileentity.PressureTubeModuleRenderer;
import me.desht.pneumaticredux.client.semiblock.ClientSemiBlockManager;
import me.desht.pneumaticredux.common.CommonHUDHandler;
import me.desht.pneumaticredux.common.HackTickHandler;
import me.desht.pneumaticredux.common.block.BlockColorHandler;
import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.config.ConfigHandler;
import me.desht.pneumaticredux.common.entity.EntityProgrammableController;
import me.desht.pneumaticredux.common.entity.EntityRing;
import me.desht.pneumaticredux.common.entity.living.EntityDrone;
import me.desht.pneumaticredux.common.entity.living.EntityLogisticsDrone;
import me.desht.pneumaticredux.common.entity.projectile.EntityVortex;
import me.desht.pneumaticredux.common.fluid.Fluids;
import me.desht.pneumaticredux.common.item.ItemColorHandler;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.recipes.CraftingRegistrator;
import me.desht.pneumaticredux.common.semiblock.ItemSemiBlockBase;
import me.desht.pneumaticredux.common.thirdparty.ThirdPartyManager;
import me.desht.pneumaticredux.common.tileentity.TileEntityPlasticMixer;
import me.desht.pneumaticredux.common.tileentity.TileEntityPressureTube;
import me.desht.pneumaticredux.lib.Log;
import me.desht.pneumaticredux.lib.ModIds;
import me.desht.pneumaticredux.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static me.desht.pneumaticredux.common.util.PneumaticCraftUtils.RL;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {

    private final HackTickHandler clientHackTickHandler = new HackTickHandler();
    public final Map<String, Integer> keybindToKeyCodes = new HashMap<String, Integer>();

    @Override
    public void preInit() {
        OBJLoader.INSTANCE.addDomain(Names.MOD_ID);
        ModelLoaderRegistry.registerLoader(LoaderProgrammingPuzzle.instance);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPressureTube.class, new PressureTubeModuleRenderer());

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        MinecraftForge.EVENT_BUS.register(HUDHandler.instance());
        MinecraftForge.EVENT_BUS.register(ClientTickHandler.instance());
        MinecraftForge.EVENT_BUS.register(getHackTickHandler());
        MinecraftForge.EVENT_BUS.register(clientHudHandler = new CommonHUDHandler());
        MinecraftForge.EVENT_BUS.register(new ClientSemiBlockManager());

        MinecraftForge.EVENT_BUS.register(HUDHandler.instance().getSpecificRenderer(CoordTrackUpgradeHandler.class));
        MinecraftForge.EVENT_BUS.register(AreaShowManager.getInstance());

        if (!Loader.isModLoaded(ModIds.NOT_ENOUGH_KEYS) || !ConfigHandler.ThirdParty.notEnoughKeys) {
            MinecraftForge.EVENT_BUS.register(KeyHandler.getInstance());
        } else {
            KeyHandler.getInstance();
        }
        ThirdPartyManager.instance().clientSide();

        /*  if(Config.enableUpdateChecker) {
              UpdateChecker.instance().start();
              MinecraftForge.EVENT_BUS.register(UpdateChecker.instance());
          }*/

        EntityTrackHandler.registerDefaultEntries();
        getAllKeybindsFromOptionsFile();

        // disabled for now, it won't work
        //        new IGWSupportNotifier();

        super.preInit();
    }

    @Override
    public void init() {
//        for (Block block : Blockss.blocks) {
//            Item item = Item.getItemFromBlock(block);
//            if (item instanceof ItemBlockPneumaticCraft) ((ItemBlockPneumaticCraft) item).registerItemVariants();
//        }
//        for (Item item : Itemss.items) {
//            if (item instanceof ItemPneumatic) ((ItemPneumatic) item).registerItemVariants();
//            else if (!(item instanceof ItemBucket)) {
//                List<ItemStack> stacks = new ArrayList<ItemStack>();
//                item.getSubItems(item, null, stacks);
//                for (ItemStack stack : stacks) {
//                    ResourceLocation resLoc = new ResourceLocation(Names.MOD_ID, stack.getUnlocalizedName().substring(5));
//                    ModelBakery.registerItemVariants(item, resLoc);
//                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, stack.getItemDamage(), new ModelResourceLocation(resLoc, "inventory"));
//                }
//            }
//        }

        for (Fluid fluid : Fluids.FLUIDS) {
            ModelLoader.setBucketModelDefinition(Fluids.getBucket(fluid));
        }

        // Only register these recipes client side, so NEI compatibility works, but drones don't lose their program when dyed.
        for (int i = 0; i < 16; i++) {
            ItemStack drone = new ItemStack(Itemss.DRONE);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("color", ItemDye.DYE_COLORS[i]);
            drone.setTagCompound(tag);
            CraftingRegistrator.addShapelessRecipe(drone, Itemss.DRONE, TileEntityPlasticMixer.DYES[i]);
        }
        CraftingRegistrator.addShapelessRecipe(new ItemStack(Itemss.DRONE), new ItemStack(Itemss.LOGISTICS_DRONE), Itemss.PRINTED_CIRCUIT_BOARD);

        ThirdPartyManager.instance().clientInit();

        RenderingRegistry.registerEntityRenderingHandler(EntityVortex.class, RenderEntityVortex.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDrone.class, RenderDrone.REGULAR_FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityLogisticsDrone.class, RenderDrone.LOGISTICS_FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityProgrammableController.class, RenderDrone.REGULAR_FACTORY);

        RenderingRegistry.registerEntityRenderingHandler(EntityRing.class, RenderEntityRing.FACTORY);
        EntityRegistry.registerModEntity(RL("ring"), EntityRing.class, Names.MOD_ID + ".ring", 100, PneumaticRedux.instance, 80, 1, true);

        registerModuleRenderers();

        BlockColorHandler.registerColorHandlers();
        ItemColorHandler.registerColorHandlers();

        super.init();
    }

    @Override
    public boolean isSneakingInGui() {

        return GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak);
    }

    private void getAllKeybindsFromOptionsFile() {
        File optionsFile = new File(FMLClientHandler.instance().getClient().mcDataDir, "options.txt");
        if (optionsFile.exists()) {
            try {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));

                try {
                    String s = "";
                    while ((s = bufferedreader.readLine()) != null) {
                        try {
                            String[] astring = s.split(":");
                            if (astring[0].startsWith("key_")) {
                                keybindToKeyCodes.put(astring[0].substring(4), Integer.parseInt(astring[1]));
                            }
                        } catch (Exception exception) {
                            Log.warning("Skipping bad option: " + s);
                        }
                    }
                } finally {
                    bufferedreader.close();
                }
            } catch (Exception exception1) {
                Log.error("Failed to load options");
                exception1.printStackTrace();
            }
        }
    }

    @Override
    public void postInit() {
        EntityTrackHandler.init();
        GuiHelmetMainScreen.init();
    }

    public void registerModuleRenderers() {

    }

    @Override
    public void initConfig() {
        for (IUpgradeRenderHandler renderHandler : UpgradeRenderHandlerList.instance().upgradeRenderers) {
            renderHandler.initConfig();
        }
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().world;
    }

    @Override
    public EntityPlayer getPlayer() {
        return FMLClientHandler.instance().getClient().player;
    }

    @Override
    public int getArmorRenderID(String armorName) {
        return 0;
    }

    @Override
    public void registerVillagerSkins() {
        // FIXME
//        VillagerRegistry.instance().registerVillagerSkin(ConfigHandler.General.villagerMechanicID, new ResourceLocation(Textures.VILLAGER_MECHANIC));
    }

    @Override
    public HackTickHandler getHackTickHandler() {
        return clientHackTickHandler;
    }

    @Override
    public void registerSemiBlockRenderer(ItemSemiBlockBase semiBlock) {
        //TODO 1.8 MinecraftForgeClient.registerItemRenderer(semiBlock, new RenderItemSemiBlock(semiBlock.semiBlockId));
    }

    @Override
    public void addScheduledTask(Runnable runnable, boolean serverSide) {
        if (serverSide) {
            super.addScheduledTask(runnable, serverSide);
        } else {
            Minecraft.getMinecraft().addScheduledTask(runnable);
        }
    }

    @Mod.EventHandler
    public static void registerModels(ModelRegistryEvent event) {
        for (Block block : Blockss.blocks) {
            Item item = Item.getItemFromBlock(block);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }

        for (Item item: Itemss.items) {
            if (item.getHasSubtypes()) {
                NonNullList<ItemStack> stacks = NonNullList.create();
                item.getSubItems(PneumaticRedux.tabPneumaticRedux, stacks);
                for (ItemStack stack : stacks) {
                    // use unlocalized name, stripping off leading "item."
                    ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(), new ModelResourceLocation(stack.getUnlocalizedName().substring(5)));
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}
