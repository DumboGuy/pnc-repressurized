package me.desht.pneumaticredux.common.fluid;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.api.PneumaticRegistry;
import me.desht.pneumaticredux.common.block.BlockFluidEtchingAcid;
import me.desht.pneumaticredux.common.block.BlockFluidPneumaticCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static me.desht.pneumaticredux.common.util.PneumaticCraftUtils.RL;

public class Fluids {
    public static final Fluid ETCHING_ACID = createFluid("etchacid", FluidEtchingAcid.class,
            fluid -> {
            }, fluid -> new BlockFluidEtchingAcid());
    public static final Fluid PLASTIC = createFluid("plastic", FluidPlastic.class,
            fluid -> {
            }, fluid -> new BlockFluidPneumaticCraft(fluid, new MaterialLiquid(MapColor.GRAY)));
    public static final Fluid OIL = createFluid("oil", FluidPneumaticCraft.class,
            fluid -> fluid.setDensity(800).setViscosity(10000), BlockFluidPneumaticCraft::new);
    public static final Fluid LPG = createFluid("lpg", FluidPneumaticCraft.class,
            fluid -> {
            }, BlockFluidPneumaticCraft::new);
    public static final Fluid GASOLINE = createFluid("gasoline", FluidPneumaticCraft.class,
            fluid -> {
            }, BlockFluidPneumaticCraft::new);
    public static final Fluid KEROSENE = createFluid("kerosene", FluidPneumaticCraft.class,
            fluid -> {
            }, BlockFluidPneumaticCraft::new);
    public static final Fluid DIESEL = createFluid("diesel", FluidPneumaticCraft.class,
            fluid -> {
            }, BlockFluidPneumaticCraft::new);
    public static final Fluid LUBRICANT = createFluid("lubricant", FluidPneumaticCraft.class,
            fluid -> {
            }, BlockFluidPneumaticCraft::new);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static final Set<Fluid> FLUIDS = new HashSet<>();
    static final Set<IFluidBlock> MOD_FLUID_BLOCKS = new HashSet<>();

    public static Map<Block, Item> fluidBlockToBucketMap = new HashMap<Block, Item>();
    private static Map<String, Block> fluidToBlockMap = new HashMap<String, Block>();//you could theoretically use fluid.getBlock(), but other mods like GregTech break it for some reason.

    public static void initFluids() {

        PLASTIC.getBlock().setUnlocalizedName(PLASTIC.getName() + "Block");

        registerFluidContainers();

        PneumaticRegistry.getInstance().registerFuel(OIL, 150000);
        PneumaticRegistry.getInstance().registerFuel(DIESEL, 700000);
        PneumaticRegistry.getInstance().registerFuel(KEROSENE, 1100000);
        PneumaticRegistry.getInstance().registerFuel(GASOLINE, 1500000);
        PneumaticRegistry.getInstance().registerFuel(LPG, 1800000);

        PneumaticRedux.instance.registerFuel(new ItemStack(getBucket(OIL)), 150000 / 2);
        PneumaticRedux.instance.registerFuel(new ItemStack(getBucket(DIESEL)), 700000 / 2);
        PneumaticRedux.instance.registerFuel(new ItemStack(getBucket(KEROSENE)), 1100000 / 2);
        PneumaticRedux.instance.registerFuel(new ItemStack(getBucket(GASOLINE)), 1500000 / 2);
        PneumaticRedux.instance.registerFuel(new ItemStack(getBucket(LPG)), 1800000 / 2);

    }

    private static <T extends Block & IFluidBlock> Fluid createFluid(String name, Class<? extends FluidPneumaticCraft> cls, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {
        try {
            Constructor<?> ctor = cls.getDeclaredConstructor(String.class);
            Fluid fluid = (Fluid) ctor.newInstance(name);
            final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
            if (useOwnFluid) {
                fluidPropertyApplier.accept(fluid);
                MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
            } else {
                fluid = FluidRegistry.getFluid(name);
            }
            FLUIDS.add(fluid);
            return fluid;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
                final Block block = (Block) fluidBlock;
                block.setRegistryName(RL("fluid." + fluidBlock.getFluid().getName()));
                block.setUnlocalizedName(fluidBlock.getFluid().getName());
                block.setCreativeTab(PneumaticRedux.tabPneumaticRedux);
                registry.register(block);
                fluidToBlockMap.put(fluidBlock.getFluid().getName(), block);
            }
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();
            for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
                final Block block = (Block) fluidBlock;
                final ItemBlock itemBlock = new ItemBlock(block);
                itemBlock.setRegistryName(block.getRegistryName());
                registry.register(itemBlock);
            }
        }
    }


    public static boolean areFluidsEqual(Fluid fluid1, Fluid fluid2) {
        return fluid1 == null && fluid2 == null || fluid1 == null == (fluid2 == null) && fluid1.getName().equals(fluid2.getName());
    }

    public static Item getBucket(Fluid fluid) {
        return FluidUtil.getFilledBucket(new FluidStack(fluid, 1000)).getItem();
    }

    public static Block getBlock(Fluid fluid) {
        return fluidToBlockMap.get(fluid.getName());
    }

    private static void registerFluidContainers() {
        for (final Fluid fluid : FLUIDS) {
            FluidRegistry.addBucketForFluid(fluid);
        }
    }
}
