package com.ultreon.mods.lib.actionmenu;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.PanoramaScreen;
import com.ultreon.mods.lib.client.gui.widget.UIWidget;
import com.ultreon.mods.lib.mixin.common.ScreenAccess;
import com.ultreon.mods.lib.network.UltreonLibNetwork;
import com.ultreon.mods.lib.network.api.Network;
import com.ultreon.mods.lib.network.packet.AMenuItemPermissionRequestPacket;
import com.ultreon.mods.lib.util.CrashReportUtils;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ActionMenuScreen extends PanoramaScreen {
    private final List<Screen> screens = new ArrayList<>();
    private final @Nullable Screen parent;
    private boolean initialized = false;
    private final int menuIndex;
    private final ActionMenu menu;
    @Nullable
    private Rectangle buttonRect;
    @Nullable
    private ActionMenuButton activeItem;
    private final List<ActionMenuButton> serverButtons = new ArrayList<>();

    protected ActionMenuScreen(@Nullable Screen parent, ActionMenu menu, int menuIndex) {
        super(Component.literal("Main"));
        this.parent = parent;
        this.menu = menu;
        this.menuIndex = menuIndex;
    }

    protected ActionMenuScreen(@Nullable Screen parent, ActionMenu menu, int menuIndex, Component title) {
        super(title);
        this.parent = parent;
        this.menu = menu;
        this.menuIndex = menuIndex;
    }

    @Override
    public void initWidgets() {
        if (parent != null && initialized) {
            assert minecraft != null;
            parent.init(minecraft, width, height);
        }
        this.clearWidgets();
        this.serverButtons.clear();

        try {
            this.add(new ActionMenuTitle(this))
                    .position(this::revalidateTitlePos)
                    .size(() -> new Vector2i(150, 15));
        } catch (Throwable t) {
            CrashReport report = CrashReport.forThrowable(t, "Failed to initialize action menu screen.");

            CrashReportUtils.addActionMenu(report, menu, menuIndex);
            throw new ReportedException(report);
        }

        initialized = true;

        Network network = UltreonLibNetwork.get();
        if (network != null) {
            network.sendToServer(new AMenuItemPermissionRequestPacket());
        }
    }

    void setButtonRectangle(Rectangle rect) {
        this.buttonRect = rect;
    }

    @Override
    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        assert this.minecraft != null;
        if (this.minecraft.level == null) {
            renderPanorama(renderer, partialTicks);
        }

        if (parent instanceof ActionMenuScreen actionMenuScreen) {
            actionMenuScreen.render(renderer, mouseX, mouseY, partialTicks, this.menuIndex);
        }

        for (GuiEventListener widget : this.children()) {
            if (widget instanceof ActionMenuIndexable indexable) {
                indexable.setMenuIndex(0);
            }
        }

        super.render(renderer, mouseX, mouseY, partialTicks);

        int startX = 1;
        if (menuIndex > 0) {
            startX = (151 * menuIndex) + 1;
        }

        if (mouseX < startX && Minecraft.getInstance().screen == this) {
            if (buttonRect != null && !buttonRect.contains(mouseX, mouseY)) {
                close();
            }
        }

        for (Screen screen : screens) {
            Minecraft.getInstance().setScreen(screen);
        }
        screens.clear();
    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return null;
    }

    @Override
    public void close() {
        Minecraft.getInstance().setScreen(parent);
    }

    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks, int childIndex) {
        if (parent instanceof ActionMenuScreen) {
            ((ActionMenuScreen) parent).render(renderer, mouseX, mouseY, partialTicks, childIndex);
        } else if (parent != null) {
            parent.render(renderer.gfx(), mouseX, mouseY, partialTicks);
        }

        for (Renderable renderable : ((ScreenAccess) this).getRenderables()) {
            if (renderable instanceof ActionMenuIndexable indexable) {
                indexable.setMenuIndex(childIndex - this.menuIndex);
            }
            if (renderable instanceof UIWidget<?> widget) {
                widget.render(renderer, mouseX, mouseY, partialTicks);
            } else {
                renderable.render(renderer.gfx(), mouseX, mouseY, partialTicks);
            }
        }

        for (Screen screen : screens) {
            Minecraft.getInstance().setScreen(screen);
        }
        screens.clear();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    void scheduleDisplay(ActionMenuScreen screen) {
        this.screens.add(screen);
    }

    public void handlePermission(AMenuItemPermissionRequestPacket.Reply reply) {
        if (reply.isAllowed()) {
            serverButtons.forEach(button -> button.active = true);
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public ActionMenu getMenu() {
        return menu;
    }

    public @Nullable Rectangle getButtonRect() {
        return buttonRect;
    }

    public @Nullable ActionMenuButton getActiveItem() {
        return activeItem;
    }

    void setActiveItem(@Nullable ActionMenuButton activeItem) {
        this.activeItem = activeItem;
    }

    private Vector2i revalidateTitlePos() {
        int x = 1;
        if (menuIndex > 0) {
            x = (151 * menuIndex) + 1;
        }

        int y = height - 16;
        List<? extends ActionMenuItem> items = menu.getClient();
        for (int i = items.size() - 1; i >= 0; i--) {
            ActionMenuItem item = items.get(i);
            try {
                ActionMenuButton actionMenuButton = add(new ActionMenuButton(this, item, x, y, 150, 15));
                if (item.isServerVariant()) {
                    actionMenuButton.active = false;
                    serverButtons.add(actionMenuButton);
                } else {
                    actionMenuButton.active = item.isEnabled();
                }
            } catch (Throwable t) {
                CrashReport report = CrashReport.forThrowable(t, "Failed to load action menu item into screen.");

                CrashReportUtils.addActionMenuItem(report, item, i, x, y);
                throw new ReportedException(report);
            }

            y -= 16;
        }

        y -= 16;
        return new Vector2i(x, y);
    }
}
