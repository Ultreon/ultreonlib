package com.ultreon.mods.lib.actionmenu;

import org.intellij.lang.annotations.Identifier;

import java.util.ArrayList;
import java.util.List;

public final class MainActionMenu extends ActionMenu {
    static final MainActionMenu INSTANCE = new MainActionMenu();
    private final List<SubmenuItem> menuItems = new ArrayList<>();

    private MainActionMenu() {

    }

    @Override
    public void client() {

    }

    @Override
    public void server() {

    }

    public static void registerHandler(IMenuHandler handler, String modId) {
        INSTANCE.add(new SubmenuItem(INSTANCE, modId, "", handler));
    }
}
