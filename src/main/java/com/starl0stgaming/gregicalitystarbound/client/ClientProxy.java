package com.starl0stgaming.gregicalitystarbound.client;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.client.sound.GCSBSounds;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRegistration;

@Mod.EventBusSubscriber(modid = GregicalityStarbound.MODID)
public class ClientProxy extends CommonProxy {
    public static KeyBinding testBinding = new KeyBinding("key.test.desc", Keyboard.KEY_P, "key.gregicalitystarbound.category");

    public void preLoad() {
        super.preLoad();
        EntityRegistration.registerRenders();
        GCSBSounds.registerSounds();
        ClientRegistry.registerKeyBinding(testBinding);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event) {
        if(testBinding.isPressed()) {
            System.out.println("key press");
        }

        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final EntityPlayerSP player = minecraft.player;

        // Prevent control when a GUI is open
        if (Minecraft.getMinecraft().currentScreen != null)// && Minecraft.getMinecraft().currentScreen instanceof
            // GuiChat)
            return;

        if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityRocket) {
            EntityRocket rocket = (EntityRocket) player.getRidingEntity();
            if (Minecraft.getMinecraft().inGameHasFocus && player.equals(Minecraft.getMinecraft().player)) {
                if (!rocket.isLaunched() && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    rocket.onLaunch();
                }
            }
        }
    }
}
