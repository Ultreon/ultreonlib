package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Locale;

public abstract class McStartMenuWindow extends McWindow {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 200;

    public McStartMenuWindow(McApplication application, int x, int y) {
        super(application, x, y, WIDTH, HEIGHT, "Start Menu");

        this.setUndecorated(true);
        this.setAbsolute(true);

        McButton powerOff = this.add(new McButton(this.getWidth() - 80, this.getHeight() - 24, 70, 15, "Power Off", Icons.POWER_OFF));
        powerOff.addClickHandler(button -> {
            try {
                this.application.getSystem().shutdown(application);
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected abstract @Nullable Vector2i getForcePosition();

    @Override
    public void onFocusLost() {
        this.close();
    }

    public enum Icons implements Icon {
        POWER_OFF;

        private static final int SIZE = 7;
        private final ResourceLocation resource;

        private Icons() {
            this.resource = UltreonLib.res("textures/gui/device/start_menu/icon_" + name().toLowerCase(Locale.ROOT) + ".png");
        }

        @Override
        public ResourceLocation resource() {
            return resource;
        }

        @Override
        public int width() {
            return SIZE;
        }

        @Override
        public int height() {
            return SIZE;
        }

        @Override
        public int vHeight() {
            return SIZE;
        }

        @Override
        public int uWidth() {
            return SIZE;
        }

        @Override
        public int v() {
            return 0;
        }

        @Override
        public int u() {
            return 0;
        }

        @Override
        public int texWidth() {
            return SIZE;
        }

        @Override
        public int texHeight() {
            return SIZE;
        }
    }
}
