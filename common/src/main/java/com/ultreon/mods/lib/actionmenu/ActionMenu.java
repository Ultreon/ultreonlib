package com.ultreon.mods.lib.actionmenu;

import com.ultreon.mods.lib.commons.IllegalEnvironmentException;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public abstract class ActionMenu {
    protected List<ActionMenuItem> client = new ArrayList<>();
    protected List<ActionMenuItem> server = new ArrayList<>();
    private boolean clientLock = false;
    private boolean serverLock = false;

    public ActionMenu() {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
            clientLock = true;
            client();
            clientLock = false;
        });
        serverLock = true;
        server();
        serverLock = false;
    }

    @Environment(EnvType.CLIENT)
    public abstract void client();

    public abstract void server();

    public List<? extends ActionMenuItem> getClient() {
        return Collections.unmodifiableList(this.client);
    }

    @Environment(EnvType.CLIENT)
    public final <T extends ActionMenuItem> T addClient(T item) {
        if (!clientLock) {
            throw new IllegalStateException("Expected to call from #client()");
        }
        if (item.isServerVariant()) {
            throw new IllegalEnvironmentException("Expected client side only action menu item, got a server variant.");
        }

        return add(item);
    }

    public final <T extends ActionMenuItem> T addServer(T item) {
        if (!serverLock) {
            throw new IllegalStateException("Expected to call from #server()");
        }
        if (item.isClientSideOnly()) {
            throw new IllegalEnvironmentException("Expected server action menu item, got a client side only variant.");
        }

        return add(item);
    }

    public final <T extends ActionMenuItem> T add(T item) {
        if (item.isServerVariant()) {
            if (!serverLock && !(this instanceof MainActionMenu)) {
                throw new IllegalStateException("Expected to call from #server()");
            }
            this.client.add(item);
            this.server.add(item);
        } else if (item.isClientSideOnly()) {
            if (!clientLock && !(this instanceof MainActionMenu)) {
                throw new IllegalStateException("Expected to call from #client()");
            }
            this.client.add(item);
        } else {
            throw new IllegalStateException("Couldn't determine if menu item is client side only or not.");
        }
        return item;
    }
}
