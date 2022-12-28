/*
 * Copyright (c) 2022. - Qboi SMP Development Team
 * Do NOT redistribute, or copy in any way, and do NOT modify in any way.
 * It is not allowed to hack into the code, use cheats against the code and/or compiled form.
 * And it is not allowed to decompile, modify or/and patch parts of code or classes or in full form.
 * Sharing this file isn't allowed either, and is hereby strictly forbidden.
 * Sharing decompiled code on social media or an online platform will cause in a report on that account.
 *
 * ONLY the owner can bypass these rules.
 */

package com.ultreon.mods.lib.client.gui.widget;

import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseContainerWidget extends BaseWidget implements ContainerEventHandler {
    protected final List<BaseWidget> children = new ArrayList<>();
    private GuiEventListener focused;
    private boolean isDragging;

    public BaseContainerWidget(int p_93629_, int p_93630_, int p_93631_, int p_93632_, Component p_93633_) {
        super(p_93629_, p_93630_, p_93631_, p_93632_, p_93633_);
    }

    public final boolean isDragging() {
        return this.isDragging;
    }

    public final void setDragging(boolean p_94681_) {
        this.isDragging = p_94681_;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener focused) {
        this.focused = focused;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return ContainerEventHandler.super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        return ContainerEventHandler.super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        return ContainerEventHandler.super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return ContainerEventHandler.super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        ContainerEventHandler.super.mouseMoved(pMouseX, pMouseY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return ContainerEventHandler.super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        return ContainerEventHandler.super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        return ContainerEventHandler.super.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public boolean changeFocus(boolean pFocus) {
        return ContainerEventHandler.super.changeFocus(pFocus);
    }

    @NotNull
    @Override
    public Optional<GuiEventListener> getChildAt(double pMouseX, double pMouseY) {
        return ContainerEventHandler.super.getChildAt(pMouseX, pMouseY);
    }

    @NotNull
    @Override
    public List<? extends GuiEventListener> children() {
        return List.copyOf(children);
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {

    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return focused;
    }
}
