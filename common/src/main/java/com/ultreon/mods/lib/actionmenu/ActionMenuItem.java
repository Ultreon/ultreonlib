package com.ultreon.mods.lib.actionmenu;

import com.ultreon.mods.lib.network.UltreonLibNetwork;
import com.ultreon.mods.lib.network.packet.ActionMenuTransferPacket;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

public class ActionMenuItem implements IActionMenuItem {
    private final Component text;
    private final Supplier<Boolean> enabled;
    protected final Runnable onActivate;

    private static int currentId = -1;
    private static int currentServerId = -1;
    private final int id;
    private final int serverId;

    private static final HashMap<Integer, ActionMenuItem> commonRegistry = new HashMap<>();
    private static final HashMap<Integer, ActionMenuItem> serverRegistry = new HashMap<>();
    private String modId;
    private String path;
    private IActionMenuItem parent;

    public ActionMenuItem(ActionMenu menu, String modId, String name, Runnable onActivate) {
        this(menu, modId, name, resolveTranslation(menu, name), onActivate);
    }

    public ActionMenuItem(ActionMenu parent, String modId, String name, Component text, Runnable onActivate) {
        this(parent, modId, name, text, () -> true, onActivate);
    }

    public ActionMenuItem(ActionMenu parent, String modId, String name, Component text, Supplier<Boolean> enabled, Runnable onActivate) {
        this.text = text;
        this.enabled = enabled;
        this.onActivate = onActivate;
        this.id = currentId++;
        if (parent instanceof Submenu submenu) {
            if (Objects.equals(submenu.getItem().path, "")) {
                this.path = name;
            } else {
                this.path = submenu.getItem().path + "/" + name;
            }
        } else {
            this.path = name;
        }

        commonRegistry.put(this.id, this);

        if (isServerVariant()) {
            this.serverId = currentServerId++;
            serverRegistry.put(this.serverId, this);
        } else {
            this.serverId = -1;
        }
    }

    public ActionMenuItem(ActionMenu parent, String modId, String name) {
        this(parent, modId, name, Component.translatable("misc.unknown"), () -> true, () -> {
        });
    }

    private static Component resolveTranslation(ActionMenu parent, String name) {
        StringBuilder sb = new StringBuilder();
        if (parent instanceof Submenu submenu) {
            ActionMenuItem item = submenu.getItem();
            sb.append("actionmenu.");
            sb.append(item.modId).append('.');
            sb.append(item.path.replaceAll("/", ".")).append(".").append(name);
            return Component.translatable(sb.toString());
        } else {
            throw new IllegalArgumentException("Expected first child menu to have a mod id.");
        }
    }

    public final void activate() {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
            if (isServerVariant()) {
                UltreonLibNetwork.get().sendToServer(new ActionMenuTransferPacket(serverId()));
            } else {
                onActivate();
            }
        });
        EnvExecutor.runInEnv(Env.SERVER, () -> () -> {
            if (isServerVariant()) {
                onActivate();
            }
        });
    }

    @Override
    public @NotNull Component getText() {
        return text;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    @Override
    public void onActivate() {
        onActivate.run();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public int serverId() {
        return serverId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static int getCurrentServerId() {
        return currentServerId;
    }

    public static ActionMenuItem fromId(int id) {
        return commonRegistry.get(id);
    }

    public static ActionMenuItem fromServerId(int id) {
        return serverRegistry.get(id);
    }

    public final boolean isServerVariant() {
        return this instanceof ServerActionMenuItem;
    }

    public final boolean isClientSideOnly() {
        return !(this instanceof ServerActionMenuItem);
    }

    public ResourceLocation location() {
        return new ResourceLocation(modId, path());
    }

    @Override
    public @NotNull String path() {
        return path;
    }
}
