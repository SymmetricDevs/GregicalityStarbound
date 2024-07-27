package com.starl0stgaming.gregicalitystarbound;

import com.starl0stgaming.gregicalitystarbound.api.configuration.GCSBConfigHandler;
import com.starl0stgaming.gregicalitystarbound.api.configuration.space.SpaceConfigHandler;
import com.starl0stgaming.gregicalitystarbound.api.sound.GCSBSounds;


import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket.entity.RocketEntity;
import com.starl0stgaming.gregicalitystarbound.api.vehicle.VehicleManager;
import com.starl0stgaming.gregicalitystarbound.api.vehicle.components.ComponentManager;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import com.starl0stgaming.gregicalitystarbound.common.metatileentities.GCSBMetaTileEntities;
import com.starl0stgaming.gregicalitystarbound.common.space.SpaceController;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(name = GregicalityStarbound.NAME, modid = GregicalityStarbound.MODID)
public class GregicalityStarbound {


    @Mod.Instance(GregicalityStarbound.MODID)
    public static GregicalityStarbound instance;

    public static final String NAME = "Gregicality Starbound";
    public static final String MODID = "gregicalitystarbound";

    public static final SpaceController SPACE_CONTROLLER = new SpaceController();
    public static final GCSBConfigHandler CONFIG_HANDLER = new GCSBConfigHandler();
    public static final SpaceConfigHandler SPACE_CONFIG_HANDLER = new SpaceConfigHandler();

    public static final ComponentManager COMPONENT_MANAGER = new ComponentManager();
    public static final VehicleManager VEHICLE_MANAGER = new VehicleManager();
    //public static final TelemetryNetwork TELEMETRY_NETWORK = new TelemetryNetwork();

    public static KeyBinding keyBinding;

    @SidedProxy(modId = GregicalityStarbound.MODID, clientSide = "com.starl0stgaming.gregicalitystarbound.client.ClientProxy", serverSide = "gregicalitystarbound.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        GregicalityStarbound.CONFIG_HANDLER.init();

        proxy.preLoad();


        GCSBSounds.registerSounds();

        EntityRegistry.registerModEntity(new ResourceLocation(GregicalityStarbound.MODID, "rocket"), RocketEntity.class, "Rocket", 1, GregicalityStarbound.instance, 64, 3, true);


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GCSBMetaTileEntities.init();
    }
}
