package me.desht.pneumaticredux.client.render.pneumaticArmor.hacking;

import me.desht.pneumaticredux.api.client.pneumaticHelmet.IHackableEntity;
import me.desht.pneumaticredux.api.hacking.IHackingCapability;
import me.desht.pneumaticredux.client.render.pneumaticArmor.PneumaticHelmetRegistry;
import me.desht.pneumaticredux.lib.Log;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Hacking implements IHackingCapability {
    private final List<IHackableEntity> hackables = new ArrayList<>();

    @Override
    public void update(Entity entity) {
        hackables.removeIf(hackable -> !hackable.afterHackTick(entity));
    }

    @Override
    public void addHackable(IHackableEntity hackable) {
        hackables.add(hackable);
    }

    @Override
    public List<IHackableEntity> getCurrentHacks() {
        return hackables;
    }

    public static class Storage implements Capability.IStorage<IHackingCapability> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IHackingCapability> capability, IHackingCapability instance, EnumFacing side) {
            NBTTagCompound compound = new NBTTagCompound();
            if (!instance.getCurrentHacks().isEmpty()) {
                NBTTagList tagList = new NBTTagList();
                for (IHackableEntity hackableEntity : instance.getCurrentHacks()) {
                    if (hackableEntity.getId() != null) {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setString("id", hackableEntity.getId());
                        tagList.appendTag(tag);
                    }
                }
                compound.setTag("hackables", tagList);
            }
            return compound;
        }

        @Override
        public void readNBT(Capability<IHackingCapability> capability, IHackingCapability instance, EnumFacing side, NBTBase nbt) {
            instance.getCurrentHacks().clear();
            NBTTagList tagList = ((NBTTagCompound) nbt).getTagList("hackables", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                String hackableId = tagList.getCompoundTagAt(i).getString("id");
                Class<? extends IHackableEntity> hackableClass = PneumaticHelmetRegistry.getInstance().stringToEntityHackables.get(hackableId);
                if (hackableClass != null) {
                    try {
                        instance.getCurrentHacks().add(hackableClass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.warning("hackable \"" + hackableId + "\" not found when constructing from NBT. Was it deleted?");
                }
            }
        }
    }

    public static class Factory implements Callable<IHackingCapability> {
        @Override
        public IHackingCapability call() throws Exception {
            return new Hacking();
        }
    }
}
