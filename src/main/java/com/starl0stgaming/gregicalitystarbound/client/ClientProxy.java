package com.starl0stgaming.gregicalitystarbound.client;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.space.timeline.TimelineTask;
import com.starl0stgaming.gregicalitystarbound.client.sound.GCSBSounds;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import com.starl0stgaming.gregicalitystarbound.test.TimelineTest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = GregicalityStarbound.MODID)
public class ClientProxy extends CommonProxy {

    public static KeyBinding testBinding = new KeyBinding("key.test.desc", Keyboard.KEY_P, "key.gregicalitystarbound.category");

    public void preLoad() {
        super.preLoad();
        GCSBSounds.registerSounds();
        ClientRegistry.registerKeyBinding(testBinding);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onEvent(InputEvent.KeyInputEvent event) {
        if(testBinding.isPressed()) {
            System.out.println("key press");
            TimelineTest test = new TimelineTest();
            test.run();
        }
    }
}
