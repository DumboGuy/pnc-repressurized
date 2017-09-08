package me.desht.pneumaticredux;

import me.desht.pneumaticredux.api.PneumaticRegistry;
import me.desht.pneumaticredux.api.item.IUpgradeAcceptor;
import me.desht.pneumaticredux.client.CreativeTabPneumaticCraft;
import me.desht.pneumaticredux.client.render.pneumaticArmor.UpgradeRenderHandlerList;
import me.desht.pneumaticredux.client.render.pneumaticArmor.hacking.HackableHandler;
import me.desht.pneumaticredux.common.EventHandlerPneumaticCraft;
import me.desht.pneumaticredux.common.EventHandlerUniversalSensor;
import me.desht.pneumaticredux.common.PneumaticCraftAPIHandler;
import me.desht.pneumaticredux.common.TickHandlerPneumaticCraft;
import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.commands.PCCommandManager;
import me.desht.pneumaticredux.common.config.ConfigHandler;
import me.desht.pneumaticredux.common.entity.EntityRegistrator;
import me.desht.pneumaticredux.common.event.DroneSpecialVariableHandler;
import me.desht.pneumaticredux.common.fluid.FluidFuelManager;
import me.desht.pneumaticredux.common.fluid.Fluids;
import me.desht.pneumaticredux.common.heat.HeatExchangerManager;
import me.desht.pneumaticredux.common.heat.behaviour.HeatBehaviourManager;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.network.NetworkHandler;
import me.desht.pneumaticredux.common.progwidgets.WidgetRegistrator;
import me.desht.pneumaticredux.common.recipes.AmadronOfferManager;
import me.desht.pneumaticredux.common.recipes.CraftingHandler;
import me.desht.pneumaticredux.common.recipes.CraftingRegistrator;
import me.desht.pneumaticredux.common.semiblock.SemiBlockInitializer;
import me.desht.pneumaticredux.common.sensor.SensorHandler;
import me.desht.pneumaticredux.common.thirdparty.ThirdPartyManager;
import me.desht.pneumaticredux.common.tileentity.TileEntityRegistrator;
import me.desht.pneumaticredux.common.util.Reflections;
import me.desht.pneumaticredux.common.worldgen.WorldGeneratorPneumaticCraft;
import me.desht.pneumaticredux.lib.ModIds;
import me.desht.pneumaticredux.lib.Names;
import me.desht.pneumaticredux.lib.Versions;
import me.desht.pneumaticredux.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = Names.MOD_ID, name = "PneumaticRedux", version = PneumaticRedux.MODVERSION,
        dependencies = "required-after:forge@[14.22.0.2474,);" + "after:forestry",
        acceptedMinecraftVersions = "1.12"
)
public class PneumaticRedux {
    public static final String MODVERSION = "@VERSION@";

    @SidedProxy(clientSide = "me.desht.pneumaticredux.proxy.ClientProxy", serverSide = "me.desht.pneumaticredux.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Instance(Names.MOD_ID)
    public static PneumaticRedux instance;

    public static Logger logger;

    public static TickHandlerPneumaticCraft tickHandler;
    public static CreativeTabPneumaticCraft tabPneumaticRedux;

    public static boolean isNEIInstalled;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        event.getModMetadata().version = Versions.fullVersionString();
        isNEIInstalled = Loader.isModLoaded(ModIds.NEI);

        Reflections.init();

        PneumaticRegistry.init(PneumaticCraftAPIHandler.getInstance());
        UpgradeRenderHandlerList.init();
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        ThirdPartyManager.instance().index();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        tabPneumaticRedux = new CreativeTabPneumaticCraft("tabPneumaticCraft");
        Fluids.initFluids();
//        Blockss.init();
//        Itemss.init();
        HackableHandler.addDefaultEntries();
//        ModuleRegistrator.init();
        WidgetRegistrator.init();
        ThirdPartyManager.instance().preInit();
        TileEntityRegistrator.init();
        EntityRegistrator.init();
        SemiBlockInitializer.init();
        CraftingRegistrator.init();
        //TODO 1.8 fix  VillagerHandler.instance().init();
        GameRegistry.registerWorldGenerator(new WorldGeneratorPneumaticCraft(), 0);
        HeatBehaviourManager.getInstance().init();
        SensorHandler.getInstance().init();

        proxy.preInit();
        tickHandler = new TickHandlerPneumaticCraft();
        MinecraftForge.EVENT_BUS.register(tickHandler);
        MinecraftForge.EVENT_BUS.register(new EventHandlerPneumaticCraft());
        MinecraftForge.EVENT_BUS.register(new EventHandlerUniversalSensor());
        MinecraftForge.EVENT_BUS.register(new DroneSpecialVariableHandler());

        MinecraftForge.EVENT_BUS.register(new CraftingHandler());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        NetworkHandler.init();

        // FIXME: port chest generator
        if (ConfigHandler.General.enableDungeonLoot) {
//            ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.stopWorm), 1, 4, 10));
//
//            ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(Itemss.nukeVirus), 1, 4, 10));
        }

        proxy.init();
        ThirdPartyManager.instance().init();
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        ServerCommandManager comManager = (ServerCommandManager) event.getServer().getCommandManager();
        new PCCommandManager().init(comManager);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        //Add these later so we include other mod's storage recipes.
        // CraftingRegistrator.addPressureChamberStorageBlockRecipes();
        CraftingRegistrator.addAssemblyCombinedRecipes();
        HeatExchangerManager.getInstance().init();
        FluidFuelManager.registerFuels();

        ThirdPartyManager.instance().postInit();
        proxy.postInit();
        ConfigHandler.postInit();
        AmadronOfferManager.getInstance().shufflePeriodicOffers();
        AmadronOfferManager.getInstance().recompileOffers();
        CraftingRegistrator.addProgrammingPuzzleRecipes();

        for (Block block : Blockss.blocks) {
            if (block instanceof IUpgradeAcceptor) {
                PneumaticRegistry.getInstance().getItemRegistry().registerUpgradeAcceptor((IUpgradeAcceptor) block);
            }
        }
        for (Item item : Itemss.ALL_ITEMS) {
            if (item instanceof IUpgradeAcceptor) {
                PneumaticRegistry.getInstance().getItemRegistry().registerUpgradeAcceptor((IUpgradeAcceptor) item);
            }
        }
    }


    public void registerFuel(final ItemStack fuelStack, final int fuelValue) {
        GameRegistry.registerFuelHandler(new IFuelHandler() {
            @Override
            public int getBurnTime(ItemStack fuel) {
                return fuel != null && fuel.isItemEqual(fuelStack) ? fuelValue : 0;
            }
        });
    }

    @EventHandler
    public void validateFluids(FMLServerStartedEvent event) {
        Fluid oil = FluidRegistry.getFluid(Fluids.OIL.getName());
        if (oil.getBlock() == null) {
            String modName = FluidRegistry.getDefaultFluidName(oil).split(":")[0];
            throw new IllegalStateException(String.format("Oil fluid does not have a block associated with it. The fluid is owned by %s. This could be fixed by creating the world with having this mod loaded after PneumaticCraft. This can be done by adding a injectedDependencies.json inside the config folder containing: [{\"modId\": \"%s\",\"deps\": [{\"type\":\"after\",\"target\":\"%s\"}]}]", modName, modName, Names.MOD_ID));
        }
    }
}
