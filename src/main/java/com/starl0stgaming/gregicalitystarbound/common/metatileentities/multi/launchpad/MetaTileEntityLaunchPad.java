package com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.launchpad;

import static gregtech.api.util.RelativeDirection.*;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.starl0stgaming.gregicalitystarbound.api.recipes.GCSBRecipeMaps;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.IEndpointSerializable;

import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;

public class MetaTileEntityLaunchPad extends RecipeMapMultiblockController implements IEndpointSerializable {

    public static final int MIN_LENGTH = 5; // accounts for the edge blocks
    private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = {
            MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.MAINTENANCE_HATCH
    };
    // Probably needs a fluid inventory for a water deluge system
    protected FluidTankList inputFluidInventory;
    private int cbAxLen = MIN_LENGTH; // controller-back axis length
    private int sAxLen = 5; // side-side axis length

    public MetaTileEntityLaunchPad(ResourceLocation mteId) {
        super(mteId, GCSBRecipeMaps.LAUNCH_PAD_LOADING_RECIPES);
        resetTileAbilities();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityLaunchPad(this.metaTileEntityId);
    }

    public void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
    }

    public void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
    }

    @Override
    protected void updateFormedValid() {
        // not necessary for now
    }

    public TraceabilityPredicate autoAbilities() {
        return super.autoAbilities(true, false)
                .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        if (getWorld() != null) updateStructureDimensions();
        cbAxLen = Math.max(cbAxLen, MIN_LENGTH);
        sAxLen = Math.max(sAxLen, MIN_LENGTH);

        // T stands for template
        StringBuilder frontTopT = new StringBuilder(new String(new char[sAxLen]).replace("\0", " "));
        frontTopT.setCharAt(sAxLen / 2, 'S');
        StringBuilder frontT = new StringBuilder(new String(new char[sAxLen]).replace("\0", "E"));
        frontT.setCharAt(sAxLen / 2, 'C');
        StringBuilder centerT = new StringBuilder(new String(new char[sAxLen]).replace("\0", "X"));
        centerT.setCharAt(0, 'E');
        centerT.setCharAt(sAxLen - 1, 'E');
        StringBuilder backT = new StringBuilder(new String(new char[sAxLen]).replace("\0", "E"));
        StringBuilder baseFBTS = new StringBuilder();
        for (int i = 0; i < sAxLen / 2; i++) {
            baseFBTS.append(i % 2 == 0 ? "S" : " ");
        }
        String baseFBT = baseFBTS + (sAxLen % 2 == 1 ? "S" : "") + baseFBTS.reverse();
        StringBuilder baseCT = new StringBuilder(new String(new char[sAxLen]).replace("\0", " "));
        baseCT.setCharAt(0, 'S');
        baseCT.setCharAt(sAxLen - 1, 'S');
        String baseEmptyT = new String(new char[sAxLen]).replace("\0", " ");

        String bottomAisle[] = new String[cbAxLen];
        for (int i = 0; i < cbAxLen; i++) {
            if (i == 0 || i == cbAxLen - 1) {
                bottomAisle[i] = baseFBT;
            } else if (i % 2 == 0 ^ (i >= cbAxLen / 2 && cbAxLen % 2 == 0)) {
                bottomAisle[i] = baseCT.toString();
            } else {
                bottomAisle[i] = baseEmptyT;
            }
        }
        String secondAisle[] = new String[cbAxLen];
        for (int i = 0; i < cbAxLen; i++) {
            if (i == 0) {
                secondAisle[i] = frontT.toString();
            } else if (i == cbAxLen - 1) {
                secondAisle[i] = backT.toString();
            } else {
                secondAisle[i] = centerT.toString();
            }
        }
        String otherAisle[] = new String[cbAxLen];
        for (int i = 0; i < cbAxLen; i++) {
            if (i == 0) {
                otherAisle[i] = frontTopT.toString();
            } else {
                otherAisle[i] = baseEmptyT;
            }
        }

        return FactoryBlockPattern.start(RIGHT, FRONT, UP).aisle(bottomAisle)
                .aisle(secondAisle)
                .aisle(otherAisle)
                .aisle(otherAisle)
                .aisle(otherAisle)
                .where('C', selfPredicate())
                .where('X',
                        states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH)
                                .getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT)))
                .where('S', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('E', states(getCasingState()).or(autoAbilities()))
                .where(' ', air())
                .build();
    }

    protected boolean updateStructureDimensions() { // Most code initially created by bruberu
        World world = getWorld();
        EnumFacing front = getFrontFacing();
        EnumFacing back = front.getOpposite();
        EnumFacing left = front.rotateYCCW();
        EnumFacing right = left.getOpposite();

        BlockPos.MutableBlockPos lPos = new BlockPos.MutableBlockPos(getPos().offset(back));
        BlockPos.MutableBlockPos rPos = new BlockPos.MutableBlockPos(lPos);
        BlockPos.MutableBlockPos bPos = new BlockPos.MutableBlockPos(lPos);

        int cbAxLen = 2;

        for (int i = cbAxLen + 1; i < 15; i++) {
            if (isBlockEdge(world, bPos, back)) {
                cbAxLen = i;
                break;
            }
        }

        int rAxLen = 1;
        int lAxLen = 1;
        int checkPos = 0;
        for (int i = 1; i < 8; i++) {
            if (!isBlockEdge(world, lPos, left) && (checkPos & 1) == 0) {
                lAxLen++;
            } else {
                checkPos |= 1;
            }
            if (!isBlockEdge(world, rPos, right) && (checkPos & 2) == 0) {
                rAxLen++;
            } else {
                checkPos |= 2;
            }
        }
        sAxLen = lAxLen + rAxLen + 1;
        int diff = lAxLen - rAxLen; // diff needs to be either 0 (XXCXX) or 1 (XXXCXX)
        if (cbAxLen < MIN_LENGTH || sAxLen < MIN_LENGTH || (diff != 0 && diff != 1)) {
            invalidateStructure();
            return false;
        }
        this.cbAxLen = cbAxLen;
        this.sAxLen = sAxLen;

        if (!this.getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.UPDATE_STRUCTURE_SIZE, buf -> {
                buf.writeInt(this.cbAxLen);
                buf.writeInt(this.sAxLen);
            });
        }
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.cbAxLen = data.getInteger("sDist"); // for consistency with GTFO's Kitchen
        this.sAxLen = data.getInteger("bDist");
        reinitializeStructurePattern();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("sDist", this.cbAxLen);
        data.setInteger("bDist", this.sAxLen);
        return super.writeToNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(cbAxLen);
        buf.writeInt(sAxLen);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.cbAxLen = buf.readInt();
        this.sAxLen = buf.readInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.UPDATE_STRUCTURE_SIZE) {
            this.cbAxLen = buf.readInt();
            this.sAxLen = buf.readInt();
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer player) {
        return super.createUITemplate(player);
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

    @Nonnull
    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
    }

    public boolean isBlockEdge(@Nonnull World world, @Nonnull BlockPos.MutableBlockPos pos,
                               @Nonnull EnumFacing direction) {
        return world.getBlockState(pos.move(direction)) == getCasingState() ||
                world.getTileEntity(pos) instanceof MetaTileEntityHolder;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    public void handleMessage(NBTTagCompound ntc) {}
}
