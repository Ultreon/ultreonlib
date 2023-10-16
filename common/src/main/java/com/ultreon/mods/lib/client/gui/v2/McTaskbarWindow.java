package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.input.GameKeyboard;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public final class McTaskbarWindow extends McWindow {
    private final int size;
    private final McStartButton startButton;
    private McStartMenuWindow startMenu;

    public McTaskbarWindow(McApplication application, int size) {
        super(application, 0, 0, 0, size, Component.empty());
        addOnClosingListener(() -> false);

        this.setAbsolute(true);

        this.size = size;

        this.startButton = this.add(new McStartButton(this, 0, 0, this.size, this.size, Component.literal("Start Menu")));
        this.startButton.loadIcon(UltreonLib.res("textures/gui/tests/device/taskbar/icon.png"), 16, 16);
        this.startButton.setCallback(this::openStartMenu);
    }

    @Override
    public void onCreated() {
        super.onCreated();

        this.application.getSystem().addKeyboardHook(new McKeyboardHook() {
            @Override
            public McKeyboardHook keyPressed(int keyCode, int scanCode, int modifiers, McKeyboardHook next) {
                if (keyCode == InputConstants.KEY_A && GameKeyboard.isKeyDown(getApplication().getSystem().getMetaKey())) {
                    openStartMenu();
                    return null;
                }

                return next;
            }
        });
    }

    private boolean openStartMenu(McStartButton button) {
        if (this.startMenu != null && this.startMenu.isValid()) return false;
        return this.openStartMenu();
    }

    public boolean openStartMenu() {
        if (this.startMenu != null && this.startMenu.isValid()) return this.startMenu.close();
        this.startMenu = new McStartMenuWindow(this.getApplication(), this.getX(), this.getY() - McStartMenuWindow.HEIGHT) {
            @Override
            protected @NotNull Vector2i getForcePosition() {
                return new Vector2i(McTaskbarWindow.this.getX(), McTaskbarWindow.this.getY() - McStartMenuWindow.HEIGHT);
            }
        };
        this.startMenu.create();
        this.startMenu.requestFocus();
        return true;
    }

    @Override
    public void onFocusGained() {
        super.onFocusGained();

        if (this.startMenu != null && !this.startMenu.isValid()) {
            this.startMenu = null;
        }
    }

    @Override
    public boolean preMouseClicked(double mouseX, double mouseY, int button) {
        return super.preMouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    public int getSize() {
        return size;
    }

    @Override
    protected void renderInternal(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.setWidth(this.getScreenWidth());
        this.setHeight(this.size);
        this.setX(0);
        this.setY(this.getScreenHeight() - size);
        this.setTopMost(true);

        RenderSystem.enableBlend();
        gfx.fill(getX(), getY(), getX() + width, getY () + height, 0x80101010);
        RenderSystem.disableBlend();
    }
}
