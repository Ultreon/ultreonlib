package com.ultreon.mods.lib.core.silentlib.client.gui.nbt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

/**
 * @deprecated Removed
 */
@Deprecated
public class DisplayNBTList extends ObjectSelectionList<DisplayNBTListEntry> {
    private final DisplayNBTScreen screen;

    public DisplayNBTList(DisplayNBTScreen screen, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.screen = screen;
        this.screen.lines.forEach(line -> addEntry(new DisplayNBTListEntry(line)));
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 200;
    }

}
