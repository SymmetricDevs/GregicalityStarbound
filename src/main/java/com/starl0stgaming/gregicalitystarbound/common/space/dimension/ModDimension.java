package com.starl0stgaming.gregicalitystarbound.common.space.dimension;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world.DummyWorldProvider;

public class ModDimension {

    public static DimensionType planetType;

    public static List<Biome> BIOMES = new ArrayList<>();
    public static List<WorldType> WORLD_TYPES = new ArrayList<>();

    public static void init() {
        int id = -1;

        for (DimensionType type : DimensionType.values()) {
            if (type.getId() > id) {
                id = type.getId();
            }
        }
        id++;

        GCSBLog.LOGGER.info("Registering planet dimension type at id " + id);
        planetType = DimensionType.register("gcsb_planet", "_gscb", id, DummyWorldProvider.class, false);
    }
}
