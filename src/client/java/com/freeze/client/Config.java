package com.freeze.client;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.*;

public class Config {
    public static boolean hud = true, blockEsp = true, spawnerEsp = true,
        susChunk = true, primeChunk = true, tracers = true, autoTool = true;
    public static int range = 128; // max 2048

    private static final Path FILE =
        FabricLoader.getInstance().getConfigDir().resolve("freezeclient.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save() {
        try {
            JsonObject o = new JsonObject();
            o.addProperty("hud", hud);
            o.addProperty("blockEsp", blockEsp);
            o.addProperty("spawnerEsp", spawnerEsp);
            o.addProperty("susChunk", susChunk);
            o.addProperty("primeChunk", primeChunk);
            o.addProperty("tracers", tracers);
            o.addProperty("autoTool", autoTool);
            o.addProperty("range", range);
            Files.writeString(FILE, GSON.toJson(o));
        } catch (Exception ignored) {}
    }

    public static void load() {
        try {
            if (!Files.exists(FILE)) { save(); return; }
            JsonObject o = JsonParser.parseString(Files.readString(FILE)).getAsJsonObject();
            hud = o.get("hud").getAsBoolean();
            blockEsp = o.get("blockEsp").getAsBoolean();
            spawnerEsp = o.get("spawnerEsp").getAsBoolean();
            susChunk = o.get("susChunk").getAsBoolean();
            primeChunk = o.get("primeChunk").getAsBoolean();
            tracers = o.get("tracers").getAsBoolean();
            autoTool = o.get("autoTool").getAsBoolean();
            range = Math.min(2048, Math.max(16, o.get("range").getAsInt()));
        } catch (Exception e) { save(); }
    }
}
