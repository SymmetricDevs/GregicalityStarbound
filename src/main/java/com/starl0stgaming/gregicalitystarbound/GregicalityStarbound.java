package com.starl0stgaming.gregicalitystarbound;

import com.starl0stgaming.gregicalitystarbound.api.configuration.GCSBConfigHandler;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import com.starl0stgaming.gregicalitystarbound.common.space.SpaceController;
import com.starl0stgaming.gregicalitystarbound.common.space.planets.PlanetSystemManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

import java.io.File;

@Mod(name = GregicalityStarbound.NAME, modid = GregicalityStarbound.MODID, dependencies = "required-after:gcym")
public class GregicalityStarbound {



    public static final String NAME = "Gregicality Starbound";
    public static final String MODID = "gregicalitystarbound";

    public static final SpaceController SPACE_CONTROLLER = new SpaceController();
    public static final GCSBConfigHandler CONFIG_HANDLER = new GCSBConfigHandler();

    public static KeyBinding keyBinding;

    @SidedProxy(modId = GregicalityStarbound.MODID, clientSide = "com.starl0stgaming.gregicalitystarbound.client.ClientProxy", serverSide = "gregicalitystarbound.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preLoad();

        GregicalityStarbound.CONFIG_HANDLER.init();


        keyBinding = new KeyBinding("key.gregicalitystarbound.test", Keyboard.KEY_P,"key.gregicalitystarbound.category");

        ClientRegistry.registerKeyBinding(keyBinding);



    }
}
