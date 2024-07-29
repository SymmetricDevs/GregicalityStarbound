package com.starl0stgaming.gregicalitystarbound.common.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.starl0stgaming.gregicalitystarbound.Tags;
import com.starl0stgaming.gregicalitystarbound.client.render.RenderRocket;

public class EntityRegistration {

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntityRocket.class)
                .id(new ResourceLocation(Tags.MODID, "rocket"), 0)
                .name("rocket")
                .tracker(80, 3, true)
                .build());
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, RenderRocket::new);
    }
}
