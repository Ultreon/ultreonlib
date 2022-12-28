package com.ultreon.mods.lib.client.gui.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TabNavigator {
    private final List<TabContainer> tabs = new ArrayList<>();
    private int foregroundIndex;
    private TabContainer foreground;
    private Consumer<TabContainer> onCloseTab;

    public boolean close(TabContainer tab) {
        if (!tab.isCloseable()) return false;
        tabs.remove(tab);

        if (foregroundIndex < tabs.size()) {
            foregroundIndex = tabs.size() - 1;
        }

        this.onCloseTab.accept(tab);

        return true;
    }

    public void add(TabContainer tab) {
        tabs.add(tab);
        if (foreground == null) {
            foregroundIndex = Math.min(foregroundIndex, tabs.size() - 1);
            foreground = tabs.get(foregroundIndex);
        }
    }

    public void add(TabContainer tab, int index) {
        tabs.add(index, tab);
        if (foreground == null) {
            foregroundIndex = Math.min(foregroundIndex, tabs.size() - 1);
            foreground = tabs.get(foregroundIndex);
        }
    }

    public boolean setForeground(int index) {
        if (index >= tabs.size()) {
            throw new IndexOutOfBoundsException("Tab index " + index + " is out of bounds (0.." + tabs.size());
        }

        if (foregroundIndex == index) {
            return false;
        }

        foregroundIndex = index;
        foreground = tabs.get(index);

        return true;
    }

    public TabContainer getForeground() {
        return foreground;
    }

    public int getForegroundIndex() {
        return foregroundIndex;
    }

    public int getTabCount() {
        return tabs.size();
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.mouseClicked(pMouseX, pMouseY, pButton);
        }
        return false;
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
        return false;
    }

    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.mouseReleased(pMouseX, pMouseY, pButton);
        }
        return false;
    }

    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.mouseScrolled(pMouseX, pMouseY, pDelta);
        }
        return false;
    }

    public void mouseMoved(double pMouseX, double pMouseY) {
        TabContainer tab = foreground;
        if (tab != null) {
            tab.mouseMoved(pMouseX, pMouseY);
        }
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
        return false;
    }

    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.keyReleased(pKeyCode, pScanCode, pModifiers);
        }
        return false;
    }

    public boolean charTyped(char pCodePoint, int pModifiers) {
        TabContainer tab = foreground;
        if (tab != null) {
            return tab.charTyped(pCodePoint, pModifiers);
        }
        return false;
    }

    public void setOnCloseTab(Consumer<TabContainer> onCloseTab) {
        this.onCloseTab = onCloseTab;
    }

    public int getIndex(TabContainer tabContainer) {
        return tabs.indexOf(tabContainer);
    }
}
