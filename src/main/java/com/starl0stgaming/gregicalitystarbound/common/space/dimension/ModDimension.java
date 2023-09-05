package com.starl0stgaming.gregicalitystarbound.common.space.dimension;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.dimensions.world.DummyWorldProvider;
import net.minecraft.world.DimensionType;

public class ModDimension {
    public static DimensionType planetType;

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
