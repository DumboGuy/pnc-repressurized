package me.desht.pneumaticredux.common.item;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.item.IItemRegistry.EnumUpgrade;
import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.semiblock.SemiBlockActiveProvider;
import me.desht.pneumaticredux.common.thirdparty.ThirdPartyManager;
import me.desht.pneumaticredux.lib.ModIds;
import me.desht.pneumaticredux.lib.Names;
import me.desht.pneumaticredux.lib.PneumaticValues;
import me.desht.pneumaticredux.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GameRegistry.ObjectHolder(Names.MOD_ID)
public class Itemss {
    @GameRegistry.ObjectHolder("gps_tool")
    public static final Item GPS_TOOL = null;
    @GameRegistry.ObjectHolder("ingot_iron_compressed")
    public static final Item INGOT_IRON_COMPRESSED = null;
    @GameRegistry.ObjectHolder("pressure_gauge")
    public static final Item PRESSURE_GAUGE = null;
    @GameRegistry.ObjectHolder("stone_base")
    public static final Item STONE_BASE = null;
    @GameRegistry.ObjectHolder("cannon_barrel")
    public static final Item cannonBarrel = null;
    @GameRegistry.ObjectHolder("turbine_blade")
    public static final Item TURBINE_BLADE = null;
    @GameRegistry.ObjectHolder("plastic")
    public static final Item PLASTIC = null;
    @GameRegistry.ObjectHolder("air_canister")
    public static final Item AIR_CANISTER = null;
    @GameRegistry.ObjectHolder("vortex_cannon")
    public static final Item VORTEX_CANNON = null;
    @GameRegistry.ObjectHolder("pneumatic_cylinder")
    public static final Item PNEUMATIC_CYLINDER = null;
    @GameRegistry.ObjectHolder("pneumatic_helmet")
    public static final Item PNEUMATIC_HELMET = null;
    @GameRegistry.ObjectHolder("manometer")
    public static final Item MANOMETER = null;
    @GameRegistry.ObjectHolder("turbine_rotor")
    public static final Item TURBINE_ROTOR = null;
    @GameRegistry.ObjectHolder("assembly_program")
    public static final Item ASSEMBLY_PROGRAM = null;
    @GameRegistry.ObjectHolder("empty_pcb")
    public static final Item EMPTY_PCB = null;
    @GameRegistry.ObjectHolder("unassembled_pcb")
    public static final Item UNASSEMBLED_PCB = null;
    @GameRegistry.ObjectHolder("pcb_blueprint")
    public static final Item PCB_BLUEPRINT = null;
    @GameRegistry.ObjectHolder("transistor")
    public static final Item TRANSISTOR = null;
    @GameRegistry.ObjectHolder("capacitor")
    public static final Item CAPACITOR = null;
    @GameRegistry.ObjectHolder("printed_circuit_board")
    public static final Item PRINTED_CIRCUIT_BOARD = null;
    @GameRegistry.ObjectHolder("failed_pcb")
    public static final Item FAILED_PCB = null;
    @GameRegistry.ObjectHolder("network_component")
    public static final Item NETWORK_COMPONENT = null;
    @GameRegistry.ObjectHolder("stop_worm")
    public static final Item STOP_WORM = null;
    @GameRegistry.ObjectHolder("nuke_virus")
    public static final Item NUKE_VIRUS = null;
    @GameRegistry.ObjectHolder("compressed_iron_gear")
    public static final Item COMPRESSED_IRON_GEAR = null;
    @GameRegistry.ObjectHolder("pneumatic_wrench")
    public static final Item PNEUMATIC_WRENCH = null;
    @GameRegistry.ObjectHolder("drone")
    public static final Item DRONE = null;
    @GameRegistry.ObjectHolder("programming_puzzle")
    public static final Item PROGRAMMING_PUZZLE = null;
    @GameRegistry.ObjectHolder("advanced_pcb")
    public static final Item ADVANCED_PCB = null;
    @GameRegistry.ObjectHolder("remote")
    public static final Item REMOTE = null;
    @GameRegistry.ObjectHolder("seismic_sensor")
    public static final Item SEISMIC_SENSOR = null;
    @GameRegistry.ObjectHolder("logistics_configurator")
    public static final Item LOGISTICS_CONFIGURATOR = null;
    @GameRegistry.ObjectHolder("logistics_frame_requester")
    public static final Item LOGISTICS_FRAME_REQUESTER = null;
    @GameRegistry.ObjectHolder("logistics_frame_storage")
    public static final Item LOGISTICS_FRAME_STORAGE = null;
    @GameRegistry.ObjectHolder("logistics_frame_default_storage")
    public static final Item LOGISTICS_FRAME_DEFAULT_STORAGE = null;
    @GameRegistry.ObjectHolder("logistics_frame_passive_provider")
    public static final Item LOGISTICS_FRAME_PASSIVE_PROVIDER = null;
    @GameRegistry.ObjectHolder("logistics_frame_active_provider")
    public static final Item LOGISTICS_FRAME_ACTIVE_PROVIDER = null;
    @GameRegistry.ObjectHolder("logistics_drone")
    public static final Item LOGISTICS_DRONE = null;
    @GameRegistry.ObjectHolder("gun_ammo")
    public static final Item GUN_AMMO = null;
    @GameRegistry.ObjectHolder("amadron_tablet")
    public static final Item AMADRON_TABLET = null;
    @GameRegistry.ObjectHolder("minigun")
    public static final Item MINIGUN = null;

    public static final List<Item> ALL_ITEMS = new ArrayList<>();
    public static final List<ItemBlock> ALL_ITEMBLOCKS = new ArrayList<>();
    public static final Map<EnumUpgrade, Item> upgrades = new HashMap<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registerItem(registry, new ItemGPSTool());
        registerItem(registry, new ItemPneumatic("ingot_iron_compressed"));
        registerItem(registry, new ItemPneumatic("pressure_gauge"));
        registerItem(registry, new ItemPneumatic("stone_base"));
        registerItem(registry, new ItemPneumatic("cannon_barrel"));
        registerItem(registry, new ItemPneumatic("turbine_blade"));
        registerItem(registry, new ItemPlastic());
        registerItem(registry, new ItemPressurizable("air_canister", PneumaticValues.AIR_CANISTER_MAX_AIR, PneumaticValues.AIR_CANISTER_VOLUME));
        registerItem(registry, new ItemVortexCannon());
        registerItem(registry, new ItemPneumatic("pneumatic_cylinder"));
        registerItem(registry, new ItemPneumaticArmor(ItemArmor.ArmorMaterial.IRON, PneumaticRedux.proxy.getArmorRenderID(Textures.ARMOR_PNEUMATIC), EntityEquipmentSlot.HEAD, PneumaticValues.PNEUMATIC_HELMET_MAX_AIR));
        registerItem(registry, new ItemManometer());
        registerItem(registry, new ItemPneumatic("turbine_rotor"));
        registerItem(registry, new ItemAssemblyProgram());
        registerItem(registry, new ItemEmptyPCB());
        registerItem(registry, new ItemNonDespawning("unassembled_pcb"));
        registerItem(registry, new ItemPneumatic("pcb_blueprint"));
        registerItem(registry, new ItemPneumatic("transistor"));
        registerItem(registry, new ItemPneumatic("capacitor"));
        registerItem(registry, new ItemPneumatic("printed_circuit_board"));
        registerItem(registry, new ItemNonDespawning("failed_pcb"));
        registerItem(registry, new ItemNetworkComponents());
        registerItem(registry, new ItemPneumatic("stop_worm"));
        registerItem(registry, new ItemPneumatic("nuke_virus"));
        registerItem(registry, new ItemPneumatic("compressed_iron_gear"));
        registerItem(registry, new ItemPneumaticWrench());
        registerItem(registry, new ItemDrone());
        registerItem(registry, new ItemProgrammingPuzzle());
        registerItem(registry, new ItemPneumatic("advanced_pcb"));
        registerItem(registry, new ItemRemote());
        registerItem(registry, new ItemSeismicSensor());
        registerItem(registry, new ItemLogisticsConfigurator());
        registerItem(registry, new ItemLogisticsFrameRequester());
        registerItem(registry, new ItemLogisticsFrameStorage());
        registerItem(registry, new ItemLogisticsFrameDefaultStorage());
        registerItem(registry, new ItemLogisticsFramePassiveProvider());
        registerItem(registry, new ItemLogisticsFrame(SemiBlockActiveProvider.ID));
        registerItem(registry, new ItemLogisticsDrone());
        registerItem(registry, new ItemGunAmmo());
        registerItem(registry, new ItemAmadronTablet());
        registerItem(registry, new ItemMinigun());

        registerUpgrades(registry);

        OreDictionary.registerOre(Names.INGOT_IRON_COMPRESSED, INGOT_IRON_COMPRESSED);

        for (Block b : Blockss.blocks) {
            registerItem(registry, new ItemBlock(b).setRegistryName(b.getRegistryName()));
        }
    }

    private static void registerUpgrades(IForgeRegistry<Item> registry) {
        for (EnumUpgrade upgrade : EnumUpgrade.values()) {
            if (upgrade != EnumUpgrade.THAUMCRAFT || Loader.isModLoaded(ModIds.THAUMCRAFT)) {
                String upgradeName = upgrade.toString().toLowerCase() + "_upgrade";
                Item upgradeItem = new ItemMachineUpgrade(upgradeName);
                registerItem(registry, upgradeItem);
                upgrades.put(upgrade, upgradeItem);
            }
        }
    }

    public static void registerItem(IForgeRegistry<Item> registry, Item item) {
        registry.register(item);
        ThirdPartyManager.instance().onItemRegistry(item);
        if (item instanceof ItemBlock) {
            ALL_ITEMBLOCKS.add((ItemBlock) item);
        } else {
            ALL_ITEMS.add(item);
        }
    }

//    public static void registerItem(Item item) {
//        registerItem(item, item.getUnlocalizedName().substring("item.".length()));
//    }
//
//    public static void registerItem(Item item, String registerName) {
//        GameRegistry.registerItem(item, registerName, Names.MOD_ID);
//        ThirdPartyManager.instance().onItemRegistry(item);
//        //GameData.newItemAdded(item);
//        items.add(item);
//    }
}
