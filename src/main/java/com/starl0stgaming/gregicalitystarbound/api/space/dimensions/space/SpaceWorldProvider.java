package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.space;

import com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen.WorldGenDetails;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.GCSBDimensionManager;
import com.starl0stgaming.gregicalitystarbound.common.space.dimension.ModDimension;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class SpaceWorldProvider extends WorldProvider {
    private WorldGenDetails details;
    public static int dimID;
    public static SpaceBiome biome;
    @Override
    protected void init() {
        biomeProvider = new SpaceBiomeProvider();
        setCloudRenderer(new SpaceCloudRenderer());
        //setSkyRenderer(new SpaceSkyRenderer());
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
        ModDimension.BIOMES.add(biome.setRegistryName(new ResourceLocation("gcsb", "space")));

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
        return new SpaceChunkGenerator(world, biome);
    }

    @Override
    public Biome getBiomeForCoords(BlockPos pos) {
        return biome;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return new Vec3d(0.0D, 0.0D, 0.0D); // It's all black!
    }

    @Override
    public boolean hasSkyLight() {
        return false;
    }

    @Override
    public float getStarBrightness(float par1) {
        return 1.F;
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0.0D, 0.0D, 0.0D);
    }
}
