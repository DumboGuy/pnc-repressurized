package me.desht.pneumaticredux.common.config;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.lib.Names;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.IOException;

@Config(modid = Names.MOD_ID)
public class ConfigHandler {
    private static ISubConfig[] subConfigs = new ISubConfig[]{
            new AmadronOfferSettings(),
            AmadronOfferStaticConfig.INSTANCE,
            new AmadronOfferPeriodicConfig(),
            new ProgWidgetConfig(),
            new HelmetWidgetDefaults()
    };

    @Config.Name("general")
    public static General general = new General();

    @Config.Name("machine_properties")
    public static MachineProperties machineProperties = new MachineProperties();

    @Config.Name("advanced")
    public static Advanced advanced = new Advanced();

    @Config.Name("recipes")
    public static Recipes recipes = new Recipes();

    @Config.Name("helmet")
    public static HelmetOptions helmetOptions = new HelmetOptions();

    @Config.Name("thirdparty")
    public static ThirdParty thirdParty = new ThirdParty();

    public static void sync() {
        ConfigManager.sync(Names.MOD_ID, Config.Type.INSTANCE);
    }

    public static void init(File configFile) {
        PneumaticRedux.proxy.initConfig();

        for(ISubConfig subConfig : subConfigs) {
            File subFolder = new File(configFile.getAbsolutePath().substring(0, configFile.getAbsolutePath().length() - 4) + File.separator);
            if (subFolder.mkdirs()) {
                File subFile = new File(subFolder, subConfig.getFolderName() + ".cfg");
                try {
                    subConfig.init(subFile);
                } catch(IOException e) {
                    PneumaticRedux.logger.error("Config file " + subConfig.getFolderName() + " failed to create! Unexpected things can happen!");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void postInit() {
        for(ISubConfig subConfig : subConfigs) {
            try {
                subConfig.postInit();
            } catch(IOException e) {
                PneumaticRedux.logger.error("Config file " + subConfig.getFolderName() + " failed to create! Unexpected things can happen!");
                e.printStackTrace();
            }
        }
    }

    public static class General {
        @Config.Comment("Villager ID used for the Mechanic Villager. Change when ID collides with an other mod which adds villagers.")
        public static int villagerMechanicID = 125;
        @Config.Comment("Chance per chunk in percentage to generate an Oil Lake. Set to 0 for no spawns")
        @Config.RangeDouble(min = 0d, max = 100d)
        public static double oilGenerationChance = 15d;
        @Config.Comment("Loss percentage (on average) of Compressed Iron ingots/blocks when exposed to an explosion.")
        @Config.RangeInt(min = 0, max = 100)
        public static int configCompressedIngotLossRate = 20;
        @Config.Comment("Enables the dungeon loot generation of the Nuke Virus and Stop! Worm. Useless items when playing SSP")
        public static boolean enableDungeonLoot = true;
        @Config.Comment("When true, the Pneumatic Helmet will be a model. Warning: this model looks far too good to be in MC")
        public static boolean useHelmetModel = false;
        @Config.Comment("Defines the difficulty of the programmer shown to the user. 0 = easy, 1 = medium, 2 = advanced")
        @Config.RangeInt(min = 0, max = 2)
        private static int programmerDifficulty = 0;
        @Config.Comment("Damage of the Miniguns. This applies to the Sentry Gun, Handheld Minigun, and Drone-based minigun")
        public static float configMinigunDamage = 4f;
        @Config.Comment("Enable Update Checker")
        public static boolean enableUpdateChecker = true;
        @Config.Comment("Enable Drone Suffocation Damage")
        public static boolean enableDroneSuffocationDamage = true;
    }

    public static class MachineProperties {
        @Config.Comment("Changing this value will alter the pressurized air usage of the Pneumatic Generator. The output, EU, will stay the same.")
        public static int pneumaticGeneratorEfficiency = 40;
        @Config.Comment("Changing this value will alter the pressurized air production of the Electric Compressor. The input, EU, will stay the same")
        public static int electricCompressorEfficiency = 40;
        @Config.Comment("Changing this value will alter the pressurized air usage of the Pneumatic Engine. The output, MJ, will stay the same")
        public static int pneumaticEngineEfficiency = 40;
        @Config.Comment("Changing this value will alter the pressurized air production of the Kinetic Compressor. The input, MJ, will stay the same")
        public static int kineticCompressorEfficiency = 40;
        @Config.Comment("Changing this value will alter the hydraulic bar production of the Pneumatic Pump. The input, air, will stay the same")
        public static int pneumaticPumpEfficiency = 40;
        @Config.Comment("Changing this value will alter the pressurized air production of the Flux Compressor. The input, RF, will stay the same")
        public static int fluxCompressorEfficiency = 40;
        @Config.Comment("Changing this value will alter the pressurized air usage of the Pneumatic Dynamo. The output, RF, will stay the same")
        public static int pneumaticDynamoEfficiency = 40;
        @Config.Comment("The max height of an elevator per stacked Elevator Base.")
        @Config.RangeInt(min = 1, max = 256)
        public static int elevatorBaseBlocksPerBase = 4;
    }

    public static class Advanced {
        @Config.Comment("When set to true, Drones will not execute any program. This is useful to set to true when due to a bug Drones are lagging your server or crashing it. Please report the bug if you encounter it.")
        public static boolean stopDroneAI = false;
        @Config.Comment("ONLY SET TO TRUE WHEN YOU KNOW WHAT YOU'RE DOING. When set to true, this will convert any Pressure Tube in the world that was a FMP to its block variant. Handy when you're about to remove FMP from the instance. This will remove any other parts from the block like covers. Exception are tube modules.")
        public static boolean convertMultipartsTBlock = false;
        @Config.Comment("When set to true, the Kerosene Lamp's fake air blocks won't be registered and therefore removed from the world. Useful if this causes trouble (it shouldn't though)")
        public static boolean disableKeroseneLampFakeAirBlock = false;
    }

    public static class Recipes {
        @Config.Comment("Electric Compressor")
        public static boolean enableElectricCompressorRecipe = true;
        @Config.Comment("Pneumatic Generator")
        public static boolean enablePneumaticGeneratorRecipe = true;
        @Config.Comment("Pneumatic Pump")
        public static boolean enablePneumaticPumpRecipe = true;
        @Config.Comment("8 Block of Coal --> 1 Diamond (Pressure Chamber)")
        public static boolean enableCoalToDiamondsRecipe = true;
    }

    public static class HelmetOptions {
        @Config.Comment("Pneumatic helmet power readout X")
        public static int powerX = -1;
        @Config.Comment("Pneumatic helmet power readout Y")
        public static int powerY = 2;
        @Config.Comment("Pneumatic helmet power readout on left?")
        public static boolean powerLeft = true;
        @Config.Comment("Pneumatic helmet message readout X")
        public static int messageX = 2;
        @Config.Comment("Pneumatic helmet message readout Y")
        public static int messageY = 2;
        @Config.Comment("Pneumatic helmet message readout on left?")
        public static boolean messageLeft = false;
        @Config.Comment("Pneumatic helmet block tracker X")
        public static int blockTrackerX = -1;
        @Config.Comment("Pneumatic helmet block tracker Y")
        public static int blockTrackerY = 46;
        @Config.Comment("Pneumatic helmet block tracker on left?")
        public static boolean blockTrackerLeft = true;
        @Config.Comment("Pneumatic helmet entity tracker X")
        public static int entityTrackerX = -1;
        @Config.Comment("Pneumatic helmet entity tracker Y")
        public static int entityTrackerY = 90;
        @Config.Comment("Pneumatic helmet entity tracker on left?")
        public static boolean entityTrackerLeft = true;
        @Config.Comment("Pneumatic helmet item search X")
        public static int itemSearchX = -1;
        @Config.Comment("Pneumatic helmet item search Y")
        public static int itemSearchY = 17;
        @Config.Comment("Pneumatic helmet item search on left?")
        public static boolean itemSearchLeft = true;
        @Config.Comment("Pneumatic helmet Path Enabled")
        public static boolean pathEnabled = true;
        @Config.Comment("Pneumatic helmet Wire Path")
        public static boolean wirePath = true;
        @Config.Comment("Pneumatic helmet X-Ray")
        public static boolean xRayEnabled = false;
        @Config.Comment("Pneumatic helmet Path Update Rate")
        public static int pathUpdateSetting = 1;
    }

    public static class ThirdParty {
        @Config.Comment("Enable notenoughkeys mod (note: not ported beyond MC1.8)")
        public static boolean notEnoughKeys = false;
    }

    @Mod.EventBusSubscriber
    public static class ConfigSyncHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(Names.MOD_ID)) {
                ConfigManager.sync(Names.MOD_ID, Config.Type.INSTANCE);
                PneumaticRedux.logger.info("Configuration has been saved.");
            }
        }
    }

    public static void setProgrammerDifficulty(int difficulty) {
        General.programmerDifficulty = difficulty;
        sync();
//        config.get(Configuration.CATEGORY_GENERAL, "Programmer Difficulty", 0).set(difficulty);
//        init(null);
    }

    public static int getProgrammerDifficulty() {
        return General.programmerDifficulty;
    }

}
