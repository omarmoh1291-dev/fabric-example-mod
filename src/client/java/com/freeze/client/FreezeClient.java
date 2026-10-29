package com.freeze.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class FreezeClient implements ClientModInitializer {
    public static final int CYAN = 0xFF00E5FF, LIGHT_BLUE = 0xFF7FD4FF;
    public static KeyBinding menuKey;

    // All features run only in singleplayer.
    public static boolean allowed() {
        MinecraftClient c = MinecraftClient.getInstance();
        return c.world != null && c.isInSingleplayer();
    }

    @Override
    public void onInitializeClient() {
        Config.load();
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.freezeclient.menu", InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) client.setScreen(new FreezeScreen());
        });

        HudElementRegistry.addLast(Identifier.of("freezeclient", "hud"), (ctx, tick) -> {
            if (!Config.hud) return;
            MinecraftClient c = MinecraftClient.getInstance();
            ctx.drawTextWithShadow(c.textRenderer, "Freeze Client", 4, 4, CYAN);
            int y = 16;
            String[][] mods = {
                {"Block ESP", String.valueOf(Config.blockEsp)},
                {"Spawner ESP", String.valueOf(Config.spawnerEsp)},
                {"Sus Chunk", String.valueOf(Config.susChunk)},
                {"Prime Chunk", String.valueOf(Config.primeChunk)},
                {"Auto Tool", String.valueOf(Config.autoTool)}};
            for (String[] m : mods) {
                if (m[1].equals("true")) {
                    ctx.drawTextWithShadow(c.textRenderer, m[0], 4, y, LIGHT_BLUE);
                    y += 10;
                }
            }
        });
    }
}
