package com.starl0stgaming.gregicalitystarbound.api.space.planets;

import com.google.gson.JsonObject;
import com.starl0stgaming.gregicalitystarbound.api.world.dimension.DummyBiome;
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

    /**
     * Creates a new biome for a planet.
     * Make sure to call initiateBiome() to register the biome itself.
     *
     */
    public PlanetBiome(JsonObject jsonObject) {
        if (jsonObject.has("name")) {
            this.name = jsonObject.get("name").getAsString();
        }
        this.fillerBlock = getBlockfromString(jsonObject.get("filler").getAsString()).getDefaultState();
        this.topBlock = getBlockfromString(jsonObject.get("top").getAsString()).getDefaultState();
        this.waterColor = (jsonObject.has("WaterColor")) ? jsonObject.get("WaterColor").getAsInt() : -1;
        this.skyColor = jsonObject.get("SkyColor").getAsInt();
        if (jsonObject.has("BaseHeight")) {
            this.baseHeight = jsonObject.get("BaseHeight").getAsFloat();
        }
        if (jsonObject.has("HeightVariation")) {
            this.heightVariation = jsonObject.get("HeightVariation").getAsFloat();
        }
        if (jsonObject.has("Temperature")) {
            this.temperature = jsonObject.get("Temperature").getAsFloat();
        }
        if (jsonObject.has("Humidity")) {
            this.rainFall = jsonObject.get("Humidity").getAsFloat();
        }
        if (jsonObject.has("DisableRain")) {
            this.rainDisabled = jsonObject.get("DisableRain").getAsBoolean();
        }


    }


    public Block getBlockfromString(String s) {
        String modId = "minecraft";

        String name;

        if (s.contains(":")) {
            modId = s.substring(0, s.indexOf(":"));
        }

        name = s.substring(s.indexOf(":") + 1);

        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, name));
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
