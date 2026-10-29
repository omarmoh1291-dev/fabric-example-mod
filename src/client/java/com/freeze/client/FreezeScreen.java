package com.freeze.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.function.*;

public class FreezeScreen extends Screen {
    public FreezeScreen() { super(Text.literal("Freeze Client")); }

    private void toggle(int x, int y, String name, BooleanSupplier get, Consumer<Boolean> set) {
        ButtonWidget[] b = new ButtonWidget[1];
        b[0] = ButtonWidget.builder(label(name, get.getAsBoolean()), btn -> {
            set.accept(!get.getAsBoolean());
            btn.setMessage(label(name, get.getAsBoolean()));
            Config.save();
        }).dimensions(x, y, 140, 20).build();
        addDrawableChild(b[0]);
    }

    private Text label(String n, boolean on) {
        return Text.literal(n + ": " + (on ? "ON" : "OFF"));
    }

    @Override
    protected void init() {
        int x = width / 2 - 145, y = height / 2 - 60;
        toggle(x, y, "HUD", () -> Config.hud, v -> Config.hud = v);
        toggle(x, y + 24, "Block ESP", () -> Config.blockEsp, v -> Config.blockEsp = v);
        toggle(x, y + 48, "Spawner ESP", () -> Config.spawnerEsp, v -> Config.spawnerEsp = v);
        toggle(x, y + 72, "Auto Tool", () -> Config.autoTool, v -> Config.autoTool = v);
        toggle(x + 150, y, "Sus Chunk", () -> Config.susChunk, v -> Config.susChunk = v);
        toggle(x + 150, y + 24, "Prime Chunk", () -> Config.primeChunk, v -> Config.primeChunk = v);
        toggle(x + 150, y + 48, "Tracers", () -> Config.tracers, v -> Config.tracers = v);
        addDrawableChild(ButtonWidget.builder(Text.literal("Range: " + Config.range), btn -> {
            Config.range = Config.range >= 2048 ? 64 : Config.range * 2;
            btn.setMessage(Text.literal("Range: " + Config.range));
            Config.save();
        }).dimensions(x + 150, y + 72, 140, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        ctx.fill(width / 2 - 160, height / 2 - 90, width / 2 + 160, height / 2 + 50, 0xCC0A1A2A);
        ctx.drawCenteredTextWithShadow(textRenderer, "Freeze Client",
            width / 2, height / 2 - 80, FreezeClient.CYAN);
        super.render(ctx, mx, my, delta);
    }
}
