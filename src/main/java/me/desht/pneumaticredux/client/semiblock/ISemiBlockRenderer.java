package me.desht.pneumaticredux.client.semiblock;

import me.desht.pneumaticredux.common.semiblock.ISemiBlock;

public interface ISemiBlockRenderer<SemiBlock extends ISemiBlock> {
    public void render(SemiBlock semiBlock, float partialTick);
}
