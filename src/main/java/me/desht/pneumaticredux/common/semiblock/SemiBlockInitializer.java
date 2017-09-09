package me.desht.pneumaticredux.common.semiblock;

import me.desht.pneumaticredux.PneumaticRedux;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.lib.ModIds;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SemiBlockInitializer {
    public static void init() {
//        MinecraftForge.EVENT_BUS.register(SemiBlockManager.getServerInstance());

        Class requesterClass = Loader.isModLoaded(ModIds.AE2) ? SemiBlockRequesterAE.class : SemiBlockRequester.class;

        registerSemiBlock(SemiBlockActiveProvider.ID, SemiBlockActiveProvider.class, false);
        registerSemiBlock(SemiBlockPassiveProvider.ID, SemiBlockPassiveProvider.class, false);
        registerSemiBlock(SemiBlockStorage.ID, SemiBlockStorage.class, false);
        registerSemiBlock(SemiBlockDefaultStorage.ID, SemiBlockDefaultStorage.class, false);
        registerSemiBlock(SemiBlockRequester.ID, requesterClass, false);
        registerSemiBlock("heat_frame", SemiBlockHeatFrame.class, true);

        PneumaticRedux.proxy.registerSemiBlockRenderer((ItemSemiBlockBase) Itemss.LOGISTICS_FRAME_REQUESTER);
        SemiBlockManager.registerSemiBlockToItemMapping(requesterClass, Itemss.LOGISTICS_FRAME_REQUESTER);

        PneumaticRedux.proxy.registerSemiBlockRenderer((ItemSemiBlockBase) Itemss.LOGISTICS_FRAME_DEFAULT_STORAGE);
        SemiBlockManager.registerSemiBlockToItemMapping(SemiBlockDefaultStorage.class, Itemss.LOGISTICS_FRAME_DEFAULT_STORAGE);

        PneumaticRedux.proxy.registerSemiBlockRenderer((ItemSemiBlockBase) Itemss.LOGISTICS_FRAME_STORAGE);
        SemiBlockManager.registerSemiBlockToItemMapping(SemiBlockStorage.class, Itemss.LOGISTICS_FRAME_STORAGE);

        PneumaticRedux.proxy.registerSemiBlockRenderer((ItemSemiBlockBase) Itemss.LOGISTICS_FRAME_PASSIVE_PROVIDER);
        SemiBlockManager.registerSemiBlockToItemMapping(SemiBlockPassiveProvider.class, Itemss.LOGISTICS_FRAME_PASSIVE_PROVIDER);

        PneumaticRedux.proxy.registerSemiBlockRenderer((ItemSemiBlockBase) Itemss.LOGISTICS_FRAME_ACTIVE_PROVIDER);
        SemiBlockManager.registerSemiBlockToItemMapping(SemiBlockActiveProvider.class, Itemss.LOGISTICS_FRAME_ACTIVE_PROVIDER);
    }

    private static Item registerSemiBlock(String key, Class<? extends ISemiBlock> semiBlock, boolean addItem) {
        Item item = SemiBlockManager.registerSemiBlock(key, semiBlock, addItem);
        if (item != null) {
            item.setCreativeTab(PneumaticRedux.tabPneumaticRedux);
        }
        return item;
    }

//    @SubscribeEvent
//    public static void registerItems(RegistryEvent.Register<Item> event) {
//        System.out.println("*** SEMIBLOCK ITEM INIT");
//        SemiBlockManager.registerItems(event.getRegistry());
//    }
}
