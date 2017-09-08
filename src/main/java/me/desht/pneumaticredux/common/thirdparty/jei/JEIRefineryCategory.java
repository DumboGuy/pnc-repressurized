package me.desht.pneumaticredux.common.thirdparty.jei;

import me.desht.pneumaticredux.common.block.Blockss;
import me.desht.pneumaticredux.common.fluid.Fluids;
import me.desht.pneumaticredux.common.thirdparty.jei.JEIRefineryCategory.RefineryNEIRecipeWrapper;
import me.desht.pneumaticredux.common.tileentity.TileEntityRefinery;
import me.desht.pneumaticredux.lib.Textures;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class JEIRefineryCategory extends PneumaticCraftCategory<RefineryNEIRecipeWrapper> {

    public JEIRefineryCategory(IJeiHelpers jeiHelpers) {
        super(jeiHelpers);
    }

    @Override
    public String getUid() {
        return ModCategoryUid.REFINERY;
    }

    @Override
    public String getTitle() {
        return I18n.format(Blockss.refinery.getUnlocalizedName() + ".name");
    }

    @Override
    public ResourceDrawable getGuiTexture() {
        return new ResourceDrawable(Textures.GUI_REFINERY, 0, 0, 6, 3, 166, 79);
    }

    /*   
       private boolean tankClick(GuiRecipe gui, int recipe, boolean usage){
           Point pos = getMousePosition();
           Point offset = gui.getRecipePosition(recipe);
           Point relMouse = new Point(pos.x - gui.guiLeft - offsetx, pos.y - gui.guiTop - offsety);
       }*/

    public static class RefineryNEIRecipeWrapper extends PneumaticCraftCategory.MultipleInputOutputRecipeWrapper {
        public final int refineries;

        private RefineryNEIRecipeWrapper(int refineries, int[] outputs) {
            this.refineries = refineries;
            addInputLiquid(new FluidStack(Fluids.OIL, 10), 2, 10);
            int x = 69;
            int y = 18;
            for (int i = 0; i < outputs.length; i++) {
                if (outputs[i] == 0) continue;
                x += 20;
                y -= 4;
                addOutputLiquid(new FluidStack(TileEntityRefinery.getRefiningFluids()[i], outputs[i]), x, y);
            }
            setUsedTemperature(26, 18, 373);
        }

    }

    public List<MultipleInputOutputRecipeWrapper> getAllRecipes() {
        List<MultipleInputOutputRecipeWrapper> recipes = new ArrayList<MultipleInputOutputRecipeWrapper>();
        for (int i = 0; i < TileEntityRefinery.REFINING_TABLE.length; i++) {
            recipes.add(new RefineryNEIRecipeWrapper(2 + i, TileEntityRefinery.REFINING_TABLE[i]));
        }
        return recipes;
    }

//    @Override
//    public Class<RefineryNEIRecipeWrapper> getRecipeClass() {
//        return RefineryNEIRecipeWrapper.class;
//    }
//
//    @Override
//    public IRecipeWrapper getRecipeWrapper(RefineryNEIRecipeWrapper recipe) {
//        return recipe;
//    }
//
//    @Override
//    public boolean isRecipeValid(RefineryNEIRecipeWrapper recipe) {
//        return true;
//    }
}
