package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.space;

import com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world.DummyBiomeProvider;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.WorldGenDetails;
import com.starl0stgaming.gregicalitystarbound.api.util.StringUtil;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.GCSBDimensionManager;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.ModDimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.Collections;

public class SpaceWorldProvider extends WorldProvider {
    private WorldGenDetails details;
    public static int dimID;
    public static SpaceBiome biome;
    @Override
    protected void init() {
        biomeProvider = new SpaceBiomeProvider();
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimension.spaceType;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
/*        if (super.getSkyRenderer() == null) {
            setSkyRenderer(new SpaceSkyRenderer());
        }*/
        return super.getSkyRenderer();
    }

    public WorldGenDetails getDimensionInformation() {
        if (details == null) {
            int dim = getDimension();
            details = GCSBDimensionManager.getSpecificWorldGenDetails(dim);
        }

        return details;
    }

    public static void createSpace(int dimID) {
        Biome.BiomeProperties prop = new Biome.BiomeProperties("space");
        prop.setRainDisabled();
        prop.setTemperature(0.0F);
        biome = new SpaceBiome(prop);

        SpaceWorldProvider.dimID = dimID;

        if (!DimensionManager.isDimensionRegistered(dimID)) {
            DimensionManager.registerDimension(dimID, ModDimension.spaceType);
            WorldType worldType = new SpaceWorldType();
            ModDimension.WORLD_TYPES.add(worldType);
        }
        if (DimensionManager.getWorld(dimID) == null) {
            File chunkDir = new File(DimensionManager.getCurrentSaveRootDirectory(),
                    DimensionManager.createProviderFor(dimID).getSaveFolder());
            if (ForgeChunkManager.savedWorldHasForcedChunkTickets(chunkDir)) {
                DimensionManager.initDimension(dimID);
            }
        }
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return super.createChunkGenerator();
    }
}
