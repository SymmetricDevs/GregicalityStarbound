package com.starl0stgaming.gregicalitystarbound;

import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = GregicalityStarbound.NAME, modid = GregicalityStarbound.MODID, dependencies = "required-after:gcym")
public class GregicalityStarbound {

    public static final String NAME = "Gregicality Starbound";
    public static final String MODID = "gregicalitystarbound";

    @SidedProxy(modId = GregicalityStarbound.MODID, clientSide = "com.starl0stgaming.gregicalitystarbound.client.ClientProxy", serverSide = "gregicalitystarbound.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preLoad();
    }
}
