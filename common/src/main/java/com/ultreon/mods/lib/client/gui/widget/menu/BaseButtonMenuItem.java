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

package com.ultreon.mods.lib.client.gui.widget.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public abstract class BaseButtonMenuItem extends MenuItem {
    public BaseButtonMenuItem(ContextMenu menu, Component message) {
        super(20, menu, message);

        setMinWidth(Minecraft.getInstance().font.width(message) + 8 + 4);
    }

    protected abstract void click();

    @Override
    public int getTextColor() {
        if (isUsingCustomTextColor()) {
            return super.getTextColor();
        }
        return getTheme().getButtonTextColor();
    }

    @Override
    public void onLeftClick(int clicks) {
        click();
    }
}
