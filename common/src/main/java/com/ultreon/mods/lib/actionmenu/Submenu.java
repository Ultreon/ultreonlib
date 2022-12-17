package com.ultreon.mods.lib.actionmenu;

public abstract class Submenu extends ActionMenu {
    private final ActionMenuItem item;

    public Submenu(ActionMenuItem item) {
        this.item = item;
    }

    public ActionMenuItem getItem() {
        return item;
    }
}
