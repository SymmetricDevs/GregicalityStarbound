package com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;


public class DummyChunkGenerator implements IChunkGenerator {
    private final Random rand;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final WorldType terrainType;
    private final double[] heightMap;
    private final float[] biomeWeights;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;
    public NoiseGeneratorOctaves forestNoise;
    double[] mainNoiseRegion;
    double[] minLimitRegion;
    double[] maxLimitRegion;
    double[] depthRegion;
    private NoiseGeneratorOctaves minLimitPerlinNoise;
    private NoiseGeneratorOctaves maxLimitPerlinNoise;
    private NoiseGeneratorOctaves mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;
    private double[] depthBuffer = new double[256];
    private MapGenBase caveGenerator = new MapGenCaves();
    private Biome[] biomesForGeneration;
    private double depthNoiseScaleX = 200.0D;
    private double depthNoiseScaleZ = 200.0D;
    private double depthNoiseScaleExponent = 0.5D;
    private int coordScale = 684;
    private int mainNoiseScaleX = 80;
    private int mainNoiseScaleY = 160;
    private int mainNoiseScaleZ = 80;
    private int heightScale = 684;
    private int biomeDepthOffSet = 0;
    private int biomeScaleOffset = 0;
    private double heightStretch = 12;
    private double baseSize = 8.5D;
    private double lowerLimitScale = 512D;
    private double upperLimitScale = 512D;
    private float biomeDepthWeight = 1.0F;
    private float biomeScaleWeight = 1.0F;
    private int seaLevel = 25;

    private IBlockState stone;
    private IBlockState bedrock;


    public DummyChunkGenerator(World worldIn, long seed, IBlockState stone, IBlockState bedrock) {
        world = worldIn;
        mapFeaturesEnabled = true;
        terrainType = worldIn.getWorldInfo().getTerrainType();
        rand = new Random(seed);
        minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
        surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
        scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        heightMap = new double[825];
        biomeWeights = new float[25];

        this.stone = stone;
        this.bedrock = bedrock;
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
                this.biomeWeights[i + 2 + (j + 2) * 5] = f;
            }
        }

        worldIn.setSeaLevel(this.seaLevel);


        net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld ctx =
                new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld(minLimitPerlinNoise, maxLimitPerlinNoise, mainPerlinNoise, surfaceNoise, scaleNoise, depthNoise, forestNoise);
        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, ctx);
        this.minLimitPerlinNoise = ctx.getLPerlin1();
        this.maxLimitPerlinNoise = ctx.getLPerlin2();
        this.mainPerlinNoise = ctx.getPerlin();
        this.surfaceNoise = ctx.getHeight();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();
        this.forestNoise = ctx.getForest();
    }

    /**
     * Creates base terrain (air and stone) using heightmaps.
     *
     * @param chunkX
     * @param chunkZ
     * @param primer
     */
    public void generateTerrain(int chunkX, int chunkZ, ChunkPrimer primer) {

        // Get biomes being used in this chunk
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);

        // Get heightmap for this chunk
        this.generateHeightmap(chunkX * 4, 0, chunkZ * 4);

        // Divide chunk along x axis
        for (int iX = 0; iX < 4; ++iX) {
            int j = iX * 5;
            int k = (iX + 1) * 5;

            // Divide chunk along z axis
            for (int iZ = 0; iZ < 4; ++iZ) {
                int i1 = (j + iZ) * 33;
                int j1 = (j + iZ + 1) * 33;
                int k1 = (k + iZ) * 33;
                int l1 = (k + iZ + 1) * 33;

                // Divide chunk along y axis.
                // Now have a 4 x 8 x 4 subchunk to work with.
                for (int iY = 0; iY < 32; ++iY) {
                    // Get noise values from heightmap.
                    double d1 = this.heightMap[i1 + iY];
                    double d2 = this.heightMap[j1 + iY];
                    double d3 = this.heightMap[k1 + iY];
                    double d4 = this.heightMap[l1 + iY];

                    // Lerp values.
                    double d5 = (this.heightMap[i1 + iY + 1] - d1) * 0.125D;
                    double d6 = (this.heightMap[j1 + iY + 1] - d2) * 0.125D;
                    double d7 = (this.heightMap[k1 + iY + 1] - d3) * 0.125D;
                    double d8 = (this.heightMap[l1 + iY + 1] - d4) * 0.125D;

                    // Loop through each Y level in the subchunk
                    for (int jY = 0; jY < 8; ++jY) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        int height = (iY * 8) + jY;
                        // Loop through x axis
                        for (int jX = 0; jX < 4; ++jX) {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * 0.25D;
                            double finalNoise = d10 - d16;

                            // Loop through z axis
                            for (int jZ = 0; jZ < 4; ++jZ) {
                                // If the noiseLevel is above 0, set block to stone.
                                if (height < 2) {
                                    primer.setBlockState(iX * 4 + jX, iY * 8 + jY, iZ * 4 + jZ, bedrock);
                                } else if ((finalNoise += d16) > 0.0D) {
                                    primer.setBlockState(iX * 4 + jX, iY * 8 + jY, iZ * 4 + jZ, stone);
                                }

                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;
        double d0 = 0.03125D;
        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double) (x * 16), (double) (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        // Loop through each x row.
        for (int iX = 0; iX < 16; ++iX) {
            // Loop through each y row.
            for (int iZ = 0; iZ < 16; ++iZ) {
                // Get the biome at this x,z coord?
                Biome biome = biomesIn[iZ + iX * 16];

                // Use the biome to replace blocks at this x, z coord (full y column).
                biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + iX, z * 16 + iZ, this.depthBuffer[iZ + iX * 16]);
            }
        }
    }

    /**
     * Generates the chunk at the specified position, from scratch
     */
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.generateTerrain(x, z, chunkprimer);
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void generateHeightmap(int p_185978_1_, int p_185978_2_, int p_185978_3_) {
        this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, p_185978_1_, p_185978_3_, 5, 5, (double) this.depthNoiseScaleX, (double) this.depthNoiseScaleZ, (double) this.depthNoiseScaleExponent);
        float f = this.coordScale;
        float f1 = this.heightScale;
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) (f / this.mainNoiseScaleX), (double) (f1 / this.mainNoiseScaleY), (double) (f / this.mainNoiseScaleZ));
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) f, (double) f1, (double) f);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) f, (double) f1, (double) f);
        int i = 0;
        int j = 0;

        for (int k = 0; k < 5; ++k) {
            for (int l = 0; l < 5; ++l) {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                int i1 = 2;
                Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];

                for (int j1 = -2; j1 <= 2; ++j1) {
                    for (int k1 = -2; k1 <= 2; ++k1) {
                        Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
                        float f5 = this.biomeDepthOffSet + biome1.getBaseHeight() * this.biomeDepthWeight;
                        float f6 = this.biomeScaleOffset + biome1.getHeightVariation() * this.biomeScaleWeight;

                        if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
                            f5 = 1.0F + f5 * 2.0F;
                            f6 = 1.0F + f6 * 4.0F;
                        }

                        float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);

                        if (biome1.getBaseHeight() > biome.getBaseHeight()) {
                            f7 /= 2.0F;
                        }

                        f2 += f6 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 = f2 / f4;
                f3 = f3 / f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double d7 = this.depthRegion[j] / 8000.0D;

                if (d7 < 0.0D) {
                    d7 = -d7 * 0.3D;
                }

                d7 = d7 * 3.0D - 2.0D;

                if (d7 < 0.0D) {
                    d7 = d7 / 2.0D;

                    if (d7 < -1.0D) {
                        d7 = -1.0D;
                    }

                    d7 = d7 / 1.4D;
                    d7 = d7 / 2.0D;
                } else {
                    if (d7 > 1.0D) {
                        d7 = 1.0D;
                    }

                    d7 = d7 / 8.0D;
                }

                ++j;
                double d8 = (double) f3;
                double d9 = (double) f2;
                d8 = d8 + d7 * 0.2D;
                d8 = d8 * (double) this.baseSize / 8.0D;
                double d0 = (double) this.baseSize + d8 * 4.0D;

                for (int l1 = 0; l1 < 33; ++l1) {
                    double d1 = ((double) l1 - d0) * (double) this.heightStretch * 128.0D / 256.0D / d9;

                    if (d1 < 0.0D) {
                        d1 *= 4.0D;
                    }

                    double d2 = this.minLimitRegion[i] / (double) this.lowerLimitScale;
                    double d3 = this.maxLimitRegion[i] / (double) this.upperLimitScale;
                    double d4 = (this.mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;

                    if (l1 > 29) {
                        double d6 = (double) ((float) (l1 - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) + -10.0D * d6;
                    }

                    this.heightMap[i] = d5;
                    ++i;
                }
            }
        }
    }

    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));

        biome.decorate(this.world, this.rand, blockpos);

    }

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     */
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    // TODO: 16/04/2018 Mobs
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return null;
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     */
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

}
