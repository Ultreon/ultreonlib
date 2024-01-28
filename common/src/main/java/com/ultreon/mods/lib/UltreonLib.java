package com.ultreon.mods.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.ultreon.mods.lib.client.theme.Theme;
import com.ultreon.mods.lib.commons.Identifier;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.loot.LootTableInjection;
import com.ultreon.mods.lib.network.api.NetworkManager;
import com.ultreon.mods.lib.util.ModMessages;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.registries.RegistrarManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;

/**
 * This is the main class for the UltreonLib mod.
 *
 * @author <a href="https://github.com/XyperCode">XyperCode</a>"
 */
@SuppressWarnings("unused")
public class UltreonLib {
    public static final String MOD_ID = "ultreonlib";

    public static final RegistrarManager REGISTRAR_MANAGER = RegistrarManager.get(MOD_ID);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static UltreonLib instance;
    public static final String VERSION;
    public static final String DESCRIPTION;
    @Nullable
    public static final String ISSUE_TRACKER;
    @Nullable
    public static final String HOMEPAGE;
    @Nullable
    public static final String SOURCE_CODE;

    public static final Logger LOGGER = LoggerFactory.getLogger("UltreonLib");
    private static List<ServiceLoader.Provider<TestScreen>> testScreens;

    static {
        Mod mod = Platform.getMod(MOD_ID);
        VERSION = mod.getVersion();
        DESCRIPTION = mod.getDescription();
        ISSUE_TRACKER = mod.getIssueTracker().orElse(null);
        HOMEPAGE = mod.getHomepage().orElse(null);
        SOURCE_CODE = mod.getSources().orElse(null);
    }

    private static CompletableFuture<ServiceLoader<TestScreen>> testsInit;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private UltreonLib() {
        LootEvent.MODIFY_LOOT_TABLE.register(LootTableInjection::runModifications);

        PlayerEvent.PLAYER_JOIN.register(ModMessages::sendOnLogin);
        Identifier.setDefaultNamespace("minecraft");

        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, Theme::reload, UltreonLib.res("themes"));
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, GlobalTheme::reload, UltreonLib.res("global_themes"), List.of(UltreonLib.res("themes")));
    }

    public static List<ServiceLoader.Provider<TestScreen>> getScreens() {
        if (testScreens != null) {
            return testScreens;
        }
        UltreonLib.LOGGER.info("Screens initializing!");
        var load = ServiceLoader.load(TestScreen.class);
        try {
            UltreonLib.LOGGER.info("Test screen services loaded!");
            return testScreens = load.stream().toList();
        } catch (Exception e) {
            UltreonLib.LOGGER.warn("Failed to load services:", e);
        }
        return Collections.emptyList();
    }

    private static void registerThemes(List<GlobalTheme> arg2, ProfilerFiller reloadProfiler) {
        for (GlobalTheme theme : arg2) {
            theme.init();
        }
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    @ApiStatus.Internal
    public synchronized static UltreonLib create() {
        if (instance != null) {
            throw new IllegalStateException("The mod is already instantiated.");
        }
        instance = new UltreonLib();
        return instance;
    }

    public static UltreonLib get() {
        return instance;
    }

    public static boolean isClientSide() {
        return Platform.getEnv() == EnvType.CLIENT;
    }

    public static boolean isServerSide() {
        return Platform.getEnv() == EnvType.SERVER;
    }

    public static boolean hasClientSide() {
        return Platform.getEnv() == EnvType.CLIENT;
    }

    @SuppressWarnings("UnnecessaryDefault")
    public static boolean hasServerSide() {
        return switch (Platform.getEnv()) {
            case CLIENT, SERVER -> true;
            default -> false;
        };
    }

    public static boolean isDevEnv() {
        return Platform.isDevelopmentEnvironment();
    }

    /**
     * @deprecated Use {@link #isMinecraftForge()} or {@link #isNeoForge()} instead.
     */
    @Deprecated
    public static boolean isForge() {
        return Platform.isMinecraftForge();
    }

    public static boolean isMinecraftForge() {
        return Platform.isMinecraftForge();
    }

    public static boolean isNeoForge() {
        return Platform.isNeoForge();
    }


    public static boolean isFabric() {
        return Platform.isFabric();
    }

    @Deprecated(forRemoval = true)
    public static boolean isDevTest() {
        return Platform.getMod(MOD_ID).getVersion().endsWith("+local");
    }

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @ApiStatus.Internal
    public void initNetworkInstances() {
        NetworkManager.init();
    }


    @Environment(EnvType.CLIENT)
    public static GlobalTheme getTheme() {
        ResourceLocation res = ResourceLocation.tryParse(UltreonLibConfig.THEME.get());
        GlobalTheme theme = GlobalTheme.fromLocationOr(res, GlobalTheme.VANILLA);
        if (theme == null) {
            theme = GlobalTheme.VANILLA;
            UltreonLibConfig.THEME.set(theme.getId().toString());
        }
        return theme;
    }

    @Environment(EnvType.CLIENT)
    public static void setTheme(GlobalTheme globalTheme) {
        UltreonLibConfig.THEME.set(globalTheme.getId().toString());
        UltreonLibConfig.THEME.save();

        instance.reloadTheme();
    }

    @Environment(EnvType.CLIENT)
    public static TitleStyle getTitleStyle() {
        return TitleStyle.fromId(UltreonLibConfig.TITLE_STYLE.get());
    }

    @Environment(EnvType.CLIENT)
    public static void setTitleStyle(TitleStyle style) {
        UltreonLibConfig.TITLE_STYLE.set(style.id());
        UltreonLibConfig.TITLE_STYLE.save();

        instance.reloadTheme();
    }

    @Environment(EnvType.CLIENT)
    public void reloadTheme() {
        if (Minecraft.getInstance().screen instanceof Stylized) {
            ((Stylized) Minecraft.getInstance().screen).reloadTheme();
        }
    }

    public void serverSetup() {
        // Nothing to do yet
    }

    public void setup() {
        // Nothing to do yet
    }
}
