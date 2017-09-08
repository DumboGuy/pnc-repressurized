package me.desht.pneumaticredux.api.hacking;

import me.desht.pneumaticredux.api.client.pneumaticHelmet.IHackableEntity;
import net.minecraft.entity.Entity;

import java.util.List;

public interface IHackingCapability {
    void update(Entity entity);
    void addHackable(IHackableEntity hackable);
    List<IHackableEntity> getCurrentHacks();
}
