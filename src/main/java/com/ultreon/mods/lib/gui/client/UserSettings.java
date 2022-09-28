package com.ultreon.mods.lib.gui.client;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ultreon.mods.lib.gui.client.gui.Theme;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.bytes.ByteConsumer;
import it.unimi.dsi.fastutil.doubles.DoubleConsumer;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import it.unimi.dsi.fastutil.longs.LongConsumer;
import it.unimi.dsi.fastutil.shorts.ShortConsumer;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;

@Deprecated
public class UserSettings {
    @Deprecated
    private static final UserSettings INSTANCE = new UserSettings();
    @Deprecated
    private static final Gson GSON = new Gson();
    @Deprecated
    private Theme theme;

    @Deprecated
    public static UserSettings get() {
        return INSTANCE;
    }

    private static JsonWriter createWriter(Writer writer) {
        JsonWriter out = new JsonWriter(writer);
        out.setIndent("  ");
        return out;
    }

    @Deprecated
    public UserSettings() {
        load();
    }

    @Deprecated
    private void load() {
        try {
            JsonObject root = GSON.fromJson(new JsonReader(new FileReader(new File(FMLPaths.CONFIGDIR.get().toFile(), "ultreon_user_settings.json"))), JsonObject.class);
            load(root);
        } catch (FileNotFoundException e) {
            save();
        } catch (JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private void load(JsonObject root) {
        getString(root, "theme", (name) -> theme = Theme.fromIdOrDefault(name));
    }

    @Deprecated
    public void save() {
        JsonObject root = new JsonObject();
        save(root);
    }

    @Deprecated
    private void save(JsonObject root) {
        root.addProperty("theme", theme.id());
    }

    @Deprecated
    public void getString(JsonObject json, String key, Consumer<String> consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isString()) {
            consumer.accept(json.get(key).getAsString());
        }
    }

    @Deprecated
    public void getByte(JsonObject json, String key, ByteConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsByte());
        }
    }

    @Deprecated
    public void getShort(JsonObject json, String key, ShortConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsShort());
        }
    }

    @Deprecated
    public void getInteger(JsonObject json, String key, IntConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsInt());
        }
    }

    @Deprecated
    public void getLong(JsonObject json, String key, LongConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsLong());
        }
    }

    @Deprecated
    public void getBigInteger(JsonObject json, String key, Consumer<BigInteger> consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsBigInteger());
        }
    }

    @Deprecated
    public void getFloat(JsonObject json, String key, FloatConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsFloat());
        }
    }

    @Deprecated
    public void getDouble(JsonObject json, String key, DoubleConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsDouble());
        }
    }

    @Deprecated
    public void getBigDecimal(JsonObject json, String key, Consumer<BigDecimal> consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isNumber()) {
            consumer.accept(json.get(key).getAsBigDecimal());
        }
    }

    @Deprecated
    public void getBoolean(JsonObject json, String key, BooleanConsumer consumer) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.get(key).getAsJsonPrimitive().isBoolean()) {
            consumer.accept(json.get(key).getAsBoolean());
        }
    }

    @Deprecated
    public void getArray(JsonObject json, String key, Consumer<JsonArray> consumer) {
        if (json.has(key) && json.get(key).isJsonArray()) {
            consumer.accept(json.get(key).getAsJsonArray());
        }
    }

    @Deprecated
    public void getObject(JsonObject json, String key, Consumer<JsonObject> consumer) {
        if (json.has(key) && json.get(key).isJsonObject()) {
            consumer.accept(json.get(key).getAsJsonObject());
        }
    }

    @Deprecated
    public boolean hasDarkMode() {
        return theme == Theme.DARK;
    }

    @Deprecated
    public void setDarkMode(boolean darkMode) {
        theme = darkMode ? Theme.DARK : Theme.NORMAL;
    }

    @Deprecated
    public Theme getTheme() {
        return theme;
    }

    @Deprecated
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
