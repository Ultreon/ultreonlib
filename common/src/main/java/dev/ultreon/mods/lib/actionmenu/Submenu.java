package dev.ultreon.mods.lib.actionmenu;

@Deprecated(forRemoval = true)
public abstract class Submenu extends ActionMenu {
    private final ActionMenuItem item;

    public Submenu(ActionMenuItem item) {
        this.item = item;
    }

    public ActionMenuItem getItem() {
        return item;
    }
}
