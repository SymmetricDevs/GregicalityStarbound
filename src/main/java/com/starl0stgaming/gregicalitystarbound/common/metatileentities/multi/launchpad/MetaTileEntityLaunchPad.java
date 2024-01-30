package com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.launchpad;
import com.starl0stgaming.gregicalitystarbound.api.recipes.GCSBRecipeMaps;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockAsphalt;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class MetaTileEntityLaunchPad extends RecipeMapMultiblockController {

    // Probably needs a fluid inventory for a water deluge system
    private FluidTankList inputFluidInventory;

    public MetaTileEntityLaunchPad(ResourceLocation mteId) {
        super(mteId, GCSBRecipeMaps.LAUNCH_PAD_LOADING_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityLaunchPad(this.metaTileEntityId);
    }

    public void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
    }

    @Override
    protected void updateFormedValid() {
        // not necessary for now
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start().aisle("S S S", "S   S", "S   S", "S   S", "S S S")
                .aisle("XXCXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX")
                .aisle("  S  ", "     ", "     ", "     ", "     ")
                .aisle("  S  ", "     ", "     ", "     ", "     ")
                .aisle("  S  ", "     ", "     ", "     ", "     ")
                    .where('C', selfPredicate())
                    .where('X', states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT)))
                    .where('S', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)).or(autoAbilities()))
                    .where(' ', air())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MACHINE_CONTROLLER_OVERLAY;
    }

    @Override
    public void invalidateStructure(){
        super.invalidateStructure();
    }

}
