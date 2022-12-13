package com.ultreon.mods.lib.actionmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.network.UltreonLibNetwork;
import com.ultreon.mods.lib.network.packet.AMenuItemPermissionRequestPacket;
import com.ultreon.mods.lib.mixin.common.ScreenAccess;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ActionMenuScreen extends Screen {
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
    protected void init() {
        if (parent != null && initialized) {
            parent.init(minecraft, width, height);
        }
        this.clearWidgets();
        this.serverButtons.clear();

        try {
            int x = 1;
            if (menuIndex > 0) {
                x = (151 * menuIndex) + 1;
            }

            int y = height - 16;
            List<? extends ActionMenuItem> items = menu.getClient();
            for (int i = items.size() - 1; i >= 0; i--) {
                ActionMenuItem item = items.get(i);
                try {
                    ActionMenuButton actionMenuButton = addRenderableWidget(new ActionMenuButton(this, item, x, y, 150, 15));
                    if (item.isServerVariant()) {
                        actionMenuButton.active = false;
                        serverButtons.add(actionMenuButton);
                    } else {
                        actionMenuButton.active = item.isEnabled();
                    }
                } catch (Throwable t) {
                    CrashReport crashreport = CrashReport.forThrowable(t, "Failed to load action menu item into screen.");

//                    CrashReportUtils.addActionMenuItem(crashreport, item, i, x, y);
                    throw new ReportedException(crashreport);
                }

                y -= 16;
            }

            y -= 16;
            addRenderableWidget(new ActionMenuTitle(this, x, y, 150, 15));
        } catch (Throwable t) {
            CrashReport crashreport = CrashReport.forThrowable(t, "Failed to initialize action menu screen.");

//            CrashReportUtils.addActionMenu(crashreport, menu, menuIndex);
            throw new ReportedException(crashreport);
        }

        initialized = true;

        UltreonLibNetwork.get().sendToServer(new AMenuItemPermissionRequestPacket());
    }

    void setButtonRectangle(Rectangle rect) {
        this.buttonRect = rect;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (parent instanceof ActionMenuScreen) {
            ((ActionMenuScreen) parent).render(matrixStack, mouseX, mouseY, partialTicks, this.menuIndex);
        } else if (parent != null) {
            parent.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        for (GuiEventListener widget : this.children()) {
            if (widget instanceof IActionMenuIndexable indexable) {
                indexable.setMenuIndex(0);
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int startX = 1;
        if (menuIndex > 0) {
            startX = (151 * menuIndex) + 1;
        }

//        int endX = 151;
//        if (menuIndex > 0) {
//            endX = (151 * (menuIndex + 1)) + 1;
//        }
//
//        if (mouseX > startX && mouseX < endX) {
//            wasHovered = true;
//        }

        if (mouseX < startX && Minecraft.getInstance().screen == this) {
            if (buttonRect != null && !buttonRect.contains(mouseX, mouseY)) {
                back();
            }
        }

        for (Screen screen : screens) {
            Minecraft.getInstance().setScreen(screen);
        }
        screens.clear();
    }

    private void back() {
        Minecraft.getInstance().setScreen(parent);
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, int childIndex) {
        if (parent instanceof ActionMenuScreen) {
            ((ActionMenuScreen) parent).render(matrixStack, mouseX, mouseY, partialTicks, childIndex);
        } else if (parent != null) {
            parent.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        for (Widget widget : ((ScreenAccess)(Screen)this).getRenderables()) {
            if (widget instanceof IActionMenuIndexable indexable) {
                indexable.setMenuIndex(childIndex - this.menuIndex);
            }
            widget.render(matrixStack, mouseX, mouseY, partialTicks);
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
}
