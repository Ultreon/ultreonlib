package com.ultreon.mods.lib;

import com.ultreon.libs.commons.v0.Identifier;
import com.ultreon.mods.lib.client.gui.screen.TitleStyle;
import com.ultreon.mods.lib.client.gui.screen.test.TestScreen;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.dev.UltreonLibDev;
import com.ultreon.mods.lib.init.ModTriggerTypes;
import com.ultreon.mods.lib.loot.LootTableInjection;
import com.ultreon.mods.lib.network.api.NetworkManager;
import com.ultreon.mods.lib.registries.ModRegistries;
import com.ultreon.mods.lib.util.ModMessages;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the main class for the UltreonLib mod.
 *
 * @author <a href="https://github.com/XyperCode">XyperCode</a>"
 */
@SuppressWarnings("unused")
public class UltreonLib {
    public static final String MOD_ID = "ultreonlib";

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
    private final UltreonLibConfig config;

    private UltreonLib() {
        UltreonLib.instance = this;

        this.config = new UltreonLibConfig();

        LootEvent.MODIFY_LOOT_TABLE.register(LootTableInjection::runModifications);
        ModMessages.init();

        Identifier.setDefaultNamespace("minecraft");

        ModTriggerTypes.init();

        LifecycleEvent.SETUP.register(UltreonLib::setup);

        if (UltreonLib.isDevEnv()) {
            UltreonLibDev.init();
        }
    }

    private static void setup() {
        ModRegistries.init();
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
            testScreens = Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    @ApiStatus.Internal
    public synchronized static UltreonLib create() {
        if (instance != null) {
            throw new IllegalStateException("The mod is already instantiated.");
        }

        return new UltreonLib();
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
        return Platform.isDevelopmentEnvironment() || UltreonLibConfig.enforceDevMode;
    }

    /**
     * @deprecated use {@link Platform#isMinecraftForge()} instead, this will be removed in the future
     */
    @Deprecated(forRemoval = true)
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
    public void loadComplete() {

    }

    @ApiStatus.Internal
    public void serverSetup() {

    }

    @ApiStatus.Internal
    public void initNetworkInstances() {
        NetworkManager.init();
    }


    @Environment(EnvType.CLIENT)
    public static GlobalTheme getTheme() {
        ResourceLocation res = UltreonLibConfig.theme;
        GlobalTheme theme = GlobalTheme.fromLocationOr(res, null);
        if (theme == null) {
            theme = GlobalTheme.VANILLA;
            UltreonLibConfig.theme = theme.getId();
        }
        return theme;
    }

    @Environment(EnvType.CLIENT)
    public static void setTheme(GlobalTheme globalTheme) {
        UltreonLibConfig.theme = globalTheme.getId();
        instance.config.save();

        instance.reloadTheme();
    }

    @Environment(EnvType.CLIENT)
    public static TitleStyle getTitleStyle() {
        return UltreonLibConfig.titleStyle;
    }

    @Environment(EnvType.CLIENT)
    public static void setTitleStyle(TitleStyle style) {
        UltreonLibConfig.titleStyle = style;
        instance.config.save();

        instance.reloadTheme();
    }

    @Environment(EnvType.CLIENT)
    public void reloadTheme() {
        if (Minecraft.getInstance().screen instanceof Stylized) {
            ((Stylized) Minecraft.getInstance().screen).reloadTheme();
        }
    }
}
