package com.starl0stgaming.gregicalitystarbound.api.space.planets.worldgen;

import com.google.gson.JsonObject;
import com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world.DummyBiome;
import com.starl0stgaming.gregicalitystarbound.api.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PlanetBiome {
    // Both of these strings get turned into blocks
    private IBlockState fillerBlock;
    private IBlockState topBlock;

    private String name;
    private int waterColor;
    private int skyColor;
    private float baseHeight = 0.1F;
    private float heightVariation = 0.2F;
    private float temperature = 0.5F;
    private float rainFall = 0.5F;

    private boolean rainDisabled;

    private Biome biome;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IBlockState getFillerBlock() {
        return fillerBlock;
    }

    public void setFillerBlock(String fillerBlock) {
        this.fillerBlock = StringUtil.getBlockfromString(fillerBlock).getDefaultState();
    }

    public IBlockState getTopBlock() {
        return topBlock;
    }

    public void setTopBlock(String topBlock) {
        this.fillerBlock = StringUtil.getBlockfromString(topBlock).getDefaultState();
    }

    public int getWaterColor() {
        return waterColor;
    }

    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }

    public int getSkyColor() {
        return skyColor;
    }

    public void setSkyColor(int skyColor) {
        this.skyColor = skyColor;
    }

    public float getBaseHeight() {
        return baseHeight;
    }

    public float getHeightVariation() {
        return this.heightVariation;
    }

    public void setHeightVariation(float heightVariation) {
        this.heightVariation = heightVariation;
    }

    public float getRainFall() {
        return rainFall;
    }

    public void setRainFall(float rainFall) {
        this.rainFall = rainFall;
    }

    public boolean getRainDisabled() {
        return rainDisabled;
    }

    public void setRainDisabled(boolean rainDisabled) {
        this.rainDisabled = rainDisabled;
    }

    public Biome getBiome() {
        return biome;
    }
    public void initiateBiome() {
        BiomeProperties properties = new BiomeProperties(this.name);
        if (this.waterColor != -1) {
            properties.setWaterColor(this.waterColor);
        }
        if (this.rainDisabled) {
            properties.setRainDisabled();
        }

        properties.setBaseHeight(this.baseHeight);
        properties.setHeightVariation(this.heightVariation);
        properties.setTemperature(this.temperature);
        properties.setRainfall(this.rainFall);

        this.biome = new DummyBiome(properties, fillerBlock, topBlock, this.skyColor);
    }
}
