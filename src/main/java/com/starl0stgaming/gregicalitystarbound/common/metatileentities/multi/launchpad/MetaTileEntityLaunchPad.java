package com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.launchpad;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockAsphalt;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MetaTileEntityLaunchPad extends MultiblockWithDisplayBase {

    private int width = 5;
    private int length = 5;
    public MetaTileEntityLaunchPad(ResourceLocation mteId) {
        super(mteId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityLaunchPad(this.metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {

    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start().aisle("XXCXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX"). // Absolutely temporary
                where('C', selfPredicate()).
                where('X', states(MetaBlocks.ASPHALT.getState(BlockAsphalt.BlockType.ASPHALT))).
                where('X', states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT))).
                build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public void invalidateStructure(){
        super.invalidateStructure();
    }

}
