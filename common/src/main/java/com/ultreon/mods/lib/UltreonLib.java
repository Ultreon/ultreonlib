package com.ultreon.mods.lib;

import com.ultreon.libs.commons.v0.Identifier;
import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.Themed;
import com.ultreon.mods.lib.client.gui.screen.TitleStyle;
import com.ultreon.mods.lib.loot.LootTableInjection;
import com.ultreon.mods.lib.network.api.NetworkManager;
import com.ultreon.mods.lib.util.ModMessages;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.PlayerEvent;
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

@SuppressWarnings("unused")
public class UltreonLib {
    private static UltreonLib instance;
    public static final String MOD_ID = "ultreonlib";
    public static final String VERSION;
    public static final String DESCRIPTION;
    @Nullable
    public static final String ISSUE_TRACKER;
    @Nullable
    public static final String HOMEPAGE;
    @Nullable
    public static final String SOURCE_CODE;

    public static final Logger LOGGER = LoggerFactory.getLogger("UltreonLib");

    static {
        Mod mod = Platform.getMod(MOD_ID);
        VERSION = mod.getVersion();
        DESCRIPTION = mod.getDescription();
        ISSUE_TRACKER = mod.getIssueTracker().orElse(null);
        HOMEPAGE = mod.getHomepage().orElse(null);
        SOURCE_CODE = mod.getSources().orElse(null);
    }

    private UltreonLib() {
        LootEvent.MODIFY_LOOT_TABLE.register(LootTableInjection::runModifications);

        PlayerEvent.PLAYER_JOIN.register(ModMessages::sendOnLogin);
        Identifier.setDefaultNamespace("minecraft");
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

    public static boolean isForge() {
        return Platform.isForge();
    }

    public static boolean isFabric() {
        return Platform.isFabric();
    }

    public static boolean isDevTest() {
        return Platform.getMod(MOD_ID).getVersion().endsWith("+local");
    }

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public void loadComplete() {

    }

    public void serverSetup() {

    }

    public void initNetworkInstances() {
        NetworkManager.init();
    }


    @Environment(EnvType.CLIENT)
    public static Theme getTheme() {
        return UltreonLibConfig.THEME.get();
    }

    @Environment(EnvType.CLIENT)
    public static void setTheme(Theme theme) {
        UltreonLibConfig.THEME.set(theme);
        UltreonLibConfig.THEME.save();

        instance.reloadTheme();
    }

    @Environment(EnvType.CLIENT)
    public static TitleStyle getTitleStyle() {
        return UltreonLibConfig.TITLE_STYLE.get();
    }

    @Environment(EnvType.CLIENT)
    public static void setTitleStyle(TitleStyle style) {
        UltreonLibConfig.TITLE_STYLE.set(style);
        UltreonLibConfig.TITLE_STYLE.save();

        instance.reloadTheme();
    }

    @Environment(EnvType.CLIENT)
    public void reloadTheme() {
        if (Minecraft.getInstance().screen instanceof Themed) {
            ((Themed) Minecraft.getInstance().screen).reloadTheme();
        }
    }
}
