package me.desht.pneumaticredux.common.item;

import me.desht.pneumaticredux.common.entity.projectile.EntityVortex;
import me.desht.pneumaticredux.lib.PneumaticValues;
import me.desht.pneumaticredux.lib.Sounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemVortexCannon extends ItemPressurizable {

    public ItemVortexCannon() {
        super("vortex_cannon", PneumaticValues.VORTEX_CANNON_MAX_AIR, PneumaticValues.VORTEX_CANNON_VOLUME);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack iStack = playerIn.getHeldItem(handIn);
        if (iStack.getItemDamage() < iStack.getMaxDamage()) {
            double factor = 0.2D * getPressure(iStack);
            world.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, Sounds.CANNON_SOUND, SoundCategory.PLAYERS, 1.0F, 0.7F + (float) factor * 0.2F, false);
            EntityVortex vortex = new EntityVortex(world, playerIn);
            vortex.motionX *= factor;
            vortex.motionY *= factor;
            vortex.motionZ *= factor;
            if (!world.isRemote) world.spawnEntity(vortex);

            iStack.setItemDamage(iStack.getItemDamage() + PneumaticValues.USAGE_VORTEX_CANNON);
            if (iStack.getItemDamage() > iStack.getMaxDamage()) {
                iStack.setItemDamage(iStack.getMaxDamage());
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, iStack);
    }
}
