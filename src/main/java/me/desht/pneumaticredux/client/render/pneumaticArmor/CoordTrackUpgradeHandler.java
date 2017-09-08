package me.desht.pneumaticredux.client.render.pneumaticArmor;

import me.desht.pneumaticredux.api.client.pneumaticHelmet.IOptionPage;
import me.desht.pneumaticredux.api.client.pneumaticHelmet.IUpgradeRenderHandler;
import me.desht.pneumaticredux.api.item.IItemRegistry.EnumUpgrade;
import me.desht.pneumaticredux.client.gui.pneumaticHelmet.GuiCoordinateTrackerOptions;
import me.desht.pneumaticredux.client.gui.widget.GuiAnimatedStat;
import me.desht.pneumaticredux.common.NBTUtil;
import me.desht.pneumaticredux.common.ai.EntityPathNavigateDrone;
import me.desht.pneumaticredux.common.config.ConfigHandler;
import me.desht.pneumaticredux.common.entity.living.EntityDrone;
import me.desht.pneumaticredux.common.item.ItemPneumaticArmor;
import me.desht.pneumaticredux.common.item.Itemss;
import me.desht.pneumaticredux.common.network.NetworkHandler;
import me.desht.pneumaticredux.common.network.PacketCoordTrackUpdate;
import me.desht.pneumaticredux.lib.PneumaticValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoordTrackUpgradeHandler implements IUpgradeRenderHandler {
    private RenderCoordWireframe coordTracker;
    private RenderNavigator navigator;

    public boolean isListeningToCoordTrackerSetting = false;

    public boolean pathEnabled;
    public boolean wirePath;
    public boolean xRayEnabled;
    private int noPathCooldown; //timer used to delay the client recalculating a path when it didn't last time. This prevents
    //gigantic lag, as it uses much performance to find a path when it doesn't have anything cached.
    private int pathCalculateCooldown;
    public int pathUpdateSetting;
    public static final int SEARCH_RANGE = 150;

    public enum EnumNavigationResult {
        NO_PATH, EASY_PATH, DRONE_PATH;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUpgradeName() {
        return "coordinateTracker";
    }

    @Override
    public void initConfig() {
        pathEnabled = ConfigHandler.HelmetOptions.pathEnabled;
        wirePath = ConfigHandler.HelmetOptions.wirePath;
        xRayEnabled = ConfigHandler.HelmetOptions.xRayEnabled;
        pathUpdateSetting = ConfigHandler.HelmetOptions.pathUpdateSetting;

//        pathEnabled = config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "Path Enabled", true).getBoolean(true);
//        wirePath = config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "Wire Path", true).getBoolean(true);
//        xRayEnabled = config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "X-Ray", false).getBoolean(true);
//        pathUpdateSetting = config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "Path Update Rate", 1).getInt();
    }

    @Override
    public void saveToConfig() {
//        Configuration config = ConfigHandler.config;
//        config.load();
        ConfigHandler.HelmetOptions.pathEnabled = pathEnabled;
        ConfigHandler.HelmetOptions.wirePath = wirePath;
        ConfigHandler.HelmetOptions.xRayEnabled = xRayEnabled;
        ConfigHandler.HelmetOptions.pathUpdateSetting = pathUpdateSetting;
//        config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "Path Enabled", true).set(pathEnabled);
//        config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "Wire Path", true).set(wirePath);
//        config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "X-Ray", true).set(xRayEnabled);
//        config.get("Helmet_Options" + Configuration.CATEGORY_SPLITTER + "Coordinate_Tracker", "Path Update Rate", true).set(pathUpdateSetting);
//        config.save();
        ConfigHandler.sync();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void update(EntityPlayer player, int rangeUpgrades) {
        if (coordTracker != null) coordTracker.ticksExisted++;
        else {
            coordTracker = ItemPneumaticArmor.getCoordTrackLocation(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
            if (coordTracker != null) navigator = new RenderNavigator(coordTracker.world, coordTracker.pos);
        }
        if (noPathCooldown > 0) noPathCooldown--;
        if (navigator != null && pathEnabled && noPathCooldown == 0 && --pathCalculateCooldown <= 0) {
            navigator.updatePath();
            if (!navigator.tracedToDestination()) noPathCooldown = 100;//wait 5 seconds before recalculating a path.
            pathCalculateCooldown = pathUpdateSetting == 2 ? 1 : pathUpdateSetting == 1 ? 20 : 100;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render3D(float partialTicks) {
        if (coordTracker != null) {
            if (FMLClientHandler.instance().getClient().player.world.provider.getDimension() != coordTracker.world.provider.getDimension())
                return;
            coordTracker.render(partialTicks);
            if (pathEnabled && navigator != null) {
                navigator.render(wirePath, xRayEnabled, partialTicks);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render2D(float partialTicks, boolean upgradeEnabled) {
    }

    @Override
    public Item[] getRequiredUpgrades() {
        return new Item[]{Itemss.upgrades.get(EnumUpgrade.COORDINATE_TRACKER)};
    }

    @Override
    public float getEnergyUsage(int rangeUpgrades, EntityPlayer player) {
        return PneumaticValues.USAGE_COORD_TRACKER;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void reset() {
        coordTracker = null;
        navigator = null;
    }

    @SubscribeEvent
    public boolean onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (isListeningToCoordTrackerSetting) {
            isListeningToCoordTrackerSetting = false;
            EnumFacing dir = event.getFace();
            reset();
            ItemStack stack = event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if (!stack.isEmpty()) {
                NBTTagCompound tag = NBTUtil.getCompoundTag(stack, "CoordTracker");
                tag.setInteger("dimID", event.getEntity().world.provider.getDimension());
                NBTUtil.setPos(tag, event.getPos().offset(dir));
            }
            assert dir != null;
            NetworkHandler.sendToServer(new PacketCoordTrackUpdate(event.getEntity().world, event.getPos().offset(dir)));
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public EnumNavigationResult navigateToSurface(EntityPlayer player) {
        World world = player.world;
        BlockPos navigatingPos = world.getHeight(new BlockPos(player));
        // FIXME player is not a type of EntityLiving
//        Path path = PneumaticCraftUtils.getPathFinder().findPath(world, player, navigatingPos, (float)SEARCH_RANGE);
        Path path = null;
        if (path != null) {
            for (int i = 0; i < path.getCurrentPathLength(); i++) {
                PathPoint pathPoint = path.getPathPointFromIndex(i);
                BlockPos pathPos = new BlockPos(pathPoint.x, pathPoint.y, pathPoint.z);
                if (world.canSeeSky(pathPos)) {
                    coordTracker = new RenderCoordWireframe(world, pathPos);
                    navigator = new RenderNavigator(world, pathPos);
                    return EnumNavigationResult.EASY_PATH;
                }
            }
        }
        path = getDronePath(player, navigatingPos);
        if (path != null) {
            for (int i = 0; i < path.getCurrentPathLength(); i++) {
                PathPoint pathPoint = path.getPathPointFromIndex(i);
                BlockPos pathPos = new BlockPos(pathPoint.x, pathPoint.y, pathPoint.z);
                if (world.canSeeSky(pathPos)) {
                    coordTracker = new RenderCoordWireframe(world, pathPos);
                    navigator = new RenderNavigator(world, pathPos);
                    return EnumNavigationResult.DRONE_PATH;
                }
            }
        }
        return EnumNavigationResult.NO_PATH;
    }

    public static Path getDronePath(EntityPlayer player, BlockPos pos) {
        World world = player.world;
        EntityDrone drone = new EntityDrone(world);
        drone.setPosition(player.posX, player.posY - 2, player.posZ);
        return new EntityPathNavigateDrone(drone, world).getPathToPos(pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IOptionPage getGuiOptionsPage() {
        return new GuiCoordinateTrackerOptions();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiAnimatedStat getAnimatedStat() {
        return null;
    }

}
